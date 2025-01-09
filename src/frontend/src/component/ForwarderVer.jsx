import '../style/ForwarderVer.css';

const ForwarderVer = ({data}) => {
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