import React, { useEffect, useState } from 'react';
import '../style/ForwarderVer.css';

import data from '../param_config/sampleResponse.json';

const ForwarderVer = () => {
    // const [data, setData] = useState(null);

    // useEffect(() => {
    //     // Replace with your actual API endpoint
    //     fetch('https://api.example.com/data')
    //         .then(response => response.json())
    //         .then(data => setData(data))
    //         .catch(error => console.error('Error fetching data:', error));
    // }, []);

    // if (!data) {
    //     return <div>Loading...</div>;
    // }

    return (
        <div className="api-response-handler">
            <div className="total">Total: {data.total}</div>
            <div className="results">
                {data.results.map((result, index) => (
                    <div key={index} className="result">
                        <div className="function-header">{`${index + 1}. ${result.function}`}</div>
                        {result.hospForwarder.map((hosp, idx) => (
                            <div key={idx} className="hosp-row">
                                <div className="hosp-code">{hosp.hospCode}</div>
                                <div className="version">{hosp.version || '/'}</div>
                            </div>
                        ))}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ForwarderVer;