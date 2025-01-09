import React, { useState } from 'react';
import '../style/ClusterCheckboxes.css';
import allClusters from '../param_config/allClusters.json';

const ClusterCheckboxes = () => {
    const [checkedClusters, setCheckedClusters] = useState({});
    const [checkedHospitals, setCheckedHospitals] = useState({});

    const handleClusterChange = (cluster) => {
        const isChecked = !checkedClusters[cluster];
        setCheckedClusters({ ...checkedClusters, [cluster]: isChecked });

        const updatedHospitals = { ...checkedHospitals };
        allClusters.allClusters.find(c => c.cluster === cluster).hospList.forEach(hosp => {
            updatedHospitals[hosp] = isChecked;
        });
        setCheckedHospitals(updatedHospitals);
    };

    const handleHospitalChange = (cluster, hospital) => {
        const isChecked = !checkedHospitals[hospital];
        setCheckedHospitals({ ...checkedHospitals, [hospital]: isChecked });

        const clusterHospitals = allClusters.allClusters.find(c => c.cluster === cluster).hospList;
        const allChecked = clusterHospitals.every(hosp => checkedHospitals[hosp] || hosp === hospital && isChecked);
        setCheckedClusters({ ...checkedClusters, [cluster]: allChecked });

    };

    const compileSelected = () => {
        const selectedClusters = allClusters.allClusters.filter(cluster => 
            checkedClusters[cluster.cluster] || cluster.hospList.some(hosp => checkedHospitals[hosp])
        ).map(cluster => ({
            cluster: cluster.cluster,
            hospList: cluster.hospList.filter(hosp => checkedHospitals[hosp])
        }));

        // console.log(JSON.stringify({ allClusters: selectedClusters }, null, 2));
        console.log({ allClusters: selectedClusters });
    };

    const resetSelection = () => {
        setCheckedClusters({});
        setCheckedHospitals({});
    };

    return (
        <>
        <div className="cluster-checkboxes">
            {allClusters.allClusters.map(cluster => (
                <div key={cluster.cluster} className="cluster-row">
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
        </div>
        <div className="buttons">
            <button onClick={compileSelected}>Compile Selected</button>
            <button onClick={resetSelection}>Reset</button>
        </div>
        </>
    );
};

export default ClusterCheckboxes;