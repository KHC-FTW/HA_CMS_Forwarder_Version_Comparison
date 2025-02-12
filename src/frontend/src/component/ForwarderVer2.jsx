import '../style/ForwarderVer2.css';
import React, { useState } from 'react';

const ForwarderVer2 = ({data, setSortedData, selectedFuncName, setSelectedFuncName}) => {

    const [contextRootDisplay, setContextRootDisplay] = useState(false);
    const [lastUpdatedDisplay, setLastUpdatedDisplay] = useState(false);
    // const [selectedFuncName, setSelectedFuncName] = useState('');

    const handleContextRootDisplay = () =>{
        const value = !contextRootDisplay;
        setContextRootDisplay(value);
    }

    const handleLastUpdatedDisplay = () =>{
        setLastUpdatedDisplay(!lastUpdatedDisplay);
    }

    const formatContextRoot = (contextRoot) => {
        return { __html: contextRoot.replace(/\n/g, '<br />') };
    }

    const sortByFuncName = (e) => {
        const funcName = e.target.getAttribute('value');
        if (funcName) setSelectedFuncName(funcName);
        const sortedHospCodes = getSortedHospCodes(data, funcName);
        const sortedData = applySortingToHospForwarders(data, sortedHospCodes);
        setSortedData(sortedData);
    }

    const getDate = (dateStr) => {
        // Empty string treated as very old date.
        return dateStr ? new Date(dateStr) : new Date(0);
      };

    const getSortedHospCodes = (respData, funcName) => {
        const hospCodeLatestMap = {};
        // Loop through each function and update the most recent last_updated for each hospCode.
        respData.results.filter(funcItem => funcItem.function === funcName).forEach(funcItem => {
          funcItem.hospForwarder.forEach(item => {
            const { hospCode, last_updated } = item;
            const itemDate = getDate(last_updated);
            hospCodeLatestMap[hospCode] = itemDate;
          });
        });
        // Sort hospCodes descending by the latest date (most recent first).
        return Object.keys(hospCodeLatestMap).sort((a, b) => hospCodeLatestMap[b] - hospCodeLatestMap[a]);
    };

    const applySortingToHospForwarders = (respData, sortedHospCodes) => {
        // For each function, sort its hospForwarders based on the global sorted hospCode order.
        const result = respData.results.map(item => {
          const sortedHospForwarders = [...item.hospForwarder].sort((a, b) =>
            sortedHospCodes.indexOf(a.hospCode) - sortedHospCodes.indexOf(b.hospCode)
          );
          return { ...item, hospForwarder: sortedHospForwarders };
        });
        return { ...respData, results: result };
    };
    

    return (
        <div className="data-container">
            <div className="inner-container">
                <div className="total">Total: {data.total}</div>
                {data.lastUpdated && (<div className="last-updated">Last Updated: {data.lastUpdated}</div>)}
                {data.total > 0 && (
                    <>
                    <label className='context-root-checkbox'>
                        <input
                            type="checkbox"
                            checked = {contextRootDisplay}
                            onChange={handleContextRootDisplay}
                        />
                        Show context_root
                    </label>
                    <label className='last-updated-checkbox'>
                        <input
                            type="checkbox"
                            checked = {lastUpdatedDisplay}
                            onChange={handleLastUpdatedDisplay}
                        />
                        Show last_updated
                    </label>
                    <div className="results">
                        <table>
                            <thead>
                            <tr>
                                <th></th>
                                {data.results[0].hospForwarder.map((result, index) => (
                                    <th colSpan={contextRootDisplay && lastUpdatedDisplay ? 3 : (contextRootDisplay || lastUpdatedDisplay ? 2 : 1)} key={index}>{result.hospCode}</th>
                                ))}
                            </tr>
                            </thead>
                            <tbody>
                            {data.results.map((result, index) => (
                                <tr key={index} className={result.function === selectedFuncName ? 'row-selected' : ''}>
                                    <td value={result.function} onClick={sortByFuncName} className='func-name'>
                                        {result.function === selectedFuncName ? (<span style={{color: 'red'}}>{result.function}</span>) : result.function}
                                        {/* {`${result.function}`}
                                        {result.function === selectedFuncName && (<span style={{color: 'red'}}>{` (sorted)`}</span>)} */}
                                    </td>
                                    { result.hospForwarder.map((result, idx) => (
                                        <React.Fragment key={idx}>
                                        <td style={{textAlign: 'center'}}>{result.version || '/'}</td>
                                        {contextRootDisplay && (
                                            <td dangerouslySetInnerHTML={formatContextRoot(result.context_root || '/')} />
                                        )}
                                        {lastUpdatedDisplay && (
                                            <td className='last-Updated-cell' dangerouslySetInnerHTML={formatContextRoot(result.last_updated || '/')} />
                                        )}
                                        </React.Fragment>
                                    ))}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    </>
                )}
                
            </div>
        </div>
    );
};

export default ForwarderVer2;