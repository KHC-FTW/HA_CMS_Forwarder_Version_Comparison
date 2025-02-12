import React, { useEffect, useState } from 'react';
import '../style/ClusterCheckboxes.css';
import setupConfig from '../param_config/setupConfig.json';
import axios from 'axios';
import { doGetRequest } from '../jsUtils.js';

const ClusterCheckboxes = ({setRespData, setIsSidebarOpen, setSelectedFuncName}) => {
    const [checkedClusters, setCheckedClusters] = useState({});
    const [checkedHospitals, setCheckedHospitals] = useState({});

    const handleClusterChange = (cluster) => {
        const isChecked = !checkedClusters[cluster];
        setCheckedClusters({ ...checkedClusters, [cluster]: isChecked });

        const updatedHospitals = { ...checkedHospitals };
        setupConfig.allClusters.find(c => c.cluster === cluster).hospList.forEach(hosp => {
            updatedHospitals[hosp] = isChecked;
        });
        setCheckedHospitals(updatedHospitals);
    };

    // const handleHospitalChange = (cluster, hospital) => {
    //     const isChecked = !checkedHospitals[hospital];
    //     setCheckedHospitals({ ...checkedHospitals, [hospital]: isChecked });
    //     const clusterHospitals = setupConfig.allClusters.find(c => c.cluster === cluster).hospList;
    //     let allChecked = false;
    //     for (const hosp of clusterHospitals){
    //         if (hosp === hospital) allChecked = isChecked
    //         else allChecked = checkedHospitals[hosp]
    //         if(!allChecked) break;
    //     }
    //     setCheckedClusters({ ...checkedClusters, [cluster]: allChecked });
    // };

    const handleHospitalChange = (cluster, hospital) => {
        const isChecked = !checkedHospitals[hospital];

        setCheckedHospitals({ ...checkedHospitals, [hospital]: isChecked });
        const clusterHospitals = setupConfig.allClusters.find(c => c.cluster === cluster).hospList;
        let allChecked = false;
        for (const hosp of clusterHospitals){
            if (hosp === hospital) allChecked = isChecked
            else allChecked = checkedHospitals[hosp]
            if(!allChecked) break;
        }
        setCheckedClusters({ ...checkedClusters, [cluster]: allChecked });

        // setCheckedHospitals(prevState => {
        //     const updatedHospitals = { ...prevState, [hospital]: isChecked };
            
        //     const clusterHospitals = setupConfig.allClusters.find(c => c.cluster === cluster).hospList;
        //     const allChecked = clusterHospitals.every(hosp => updatedHospitals[hosp]);
            
        //     setCheckedClusters(prevClusters => ({ ...prevClusters, [cluster]: allChecked }));
            
        //     return updatedHospitals;
        // });
    };

    const lessThanTwoChecked = () => {
        const checkedCount = Object.values(checkedHospitals).filter(Boolean).length;
        return checkedCount < 2;
    };

    const selectAll = () => {
        const updatedClusters = { ...checkedClusters };
        const updatedHospitals = { ...checkedHospitals };
        setupConfig.allClusters.forEach(c => {
            updatedClusters[c.cluster] = true;
            c.hospList.forEach(hosp => updatedHospitals[hosp] = true)
        });
        setCheckedClusters(updatedClusters);
        setCheckedHospitals(updatedHospitals);
    }

    const checkIsAllSelected = () => {
        return setupConfig.allClusters.every(c => checkedClusters[c.cluster]);
    }

    const compileSelected = () => {
        const selectedClusters = setupConfig.allClusters.filter(cluster => 
            checkedClusters[cluster.cluster] || cluster.hospList.some(hosp => checkedHospitals[hosp])
        ).map(cluster => ({
            cluster: cluster.cluster,
            hospList: cluster.hospList.filter(hosp => checkedHospitals[hosp])
        }));
        return selectedClusters;
    };

    const handleCheck = () => {
        if (lessThanTwoChecked()){
            alert('At least 2 hospitals must be selected!');
            return;
        }
        const isAllSelected = checkIsAllSelected();
        const targetAPI = setupConfig.backend_port + (isAllSelected ? setupConfig.api_list.find_forwarder_diff_all_hosp : setupConfig.api_list.find_forwarder_diff_selected_hosp);

        if (isAllSelected){
            doGetRequest(targetAPI).then(data => {
                setRespData(data);
                // set to automatically close the side bar
                // for better user experience
                setIsSidebarOpen(false);
                setSelectedFuncName('');
            });
        }else{
            const payload = { payload: compileSelected() };    
            axios.post(targetAPI, payload, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(resp => { 
                setRespData(resp.data);
                // set to automatically close the side bar
                // for better user experience
                setIsSidebarOpen(false);
                setSelectedFuncName('');
            })
            .catch(err => { console.log(err); });
        }
    };

    const resetSelection = () => {
        setCheckedClusters({});
        setCheckedHospitals({});
    };


    return (
        <>
        <div className="cluster-checkboxes">
            <div className='header'>CMS Forwarder Version Comparison</div>
            {setupConfig.allClusters.map(cluster => (
                <div key={cluster.cluster} className="cluster-row" id={`cluster-row-${cluster.cluster}`}>
                    <div className="cluster-column">
                        <label>
                            <input
                                type="checkbox"
                                checked={checkedClusters[cluster.cluster] || false}
                                onChange={() => handleClusterChange(cluster.cluster)}
                            />
                            {cluster.cluster}
                        </label>
                    </div>
                    <div className="hospital-column">
                        {cluster.hospList.map(hospital => (
                            <label key={hospital}>
                                <input
                                    type="checkbox"
                                    checked={checkedHospitals[hospital] || false}
                                    onChange={() => handleHospitalChange(cluster.cluster, hospital)}
                                />
                                {hospital}
                            </label>
                        ))}
                    </div>
                </div>
            ))}
            <div className="buttons">
                <button onClick={selectAll} id='select-all'>Select All</button>
                <button onClick={resetSelection} id='reset'>Reset</button>
                <button onClick={handleCheck} id='check'>Check</button>
            </div>
        </div>
        </>
    );
};

export default ClusterCheckboxes;