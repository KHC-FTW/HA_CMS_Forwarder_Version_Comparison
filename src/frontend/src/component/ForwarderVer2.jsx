import '../style/ForwarderVer2.css';
import React, { useState } from 'react';

const ForwarderVer2 = ({data}) => {

    const [contextRootDisplay, setContextRootDisplay] = useState(false);
    const [lastUpdatedDisplay, setLastUpdatedDisplay] = useState(false);

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
                                <tr key={index}>
                                    <td>{result.function}</td>
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