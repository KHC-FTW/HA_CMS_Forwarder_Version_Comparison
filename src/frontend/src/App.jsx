import { useEffect, useState } from 'react'
import './style/App.css'
import ClusterCheckboxes from './component/ClusterCheckbox'
import ForwarderVer from './component/ForwarderVer';
import data from './param_config/sampleResponse.json';
import setupConfig from './param_config/setupConfig.json';
import { doGetRequest } from './jsUtils.js';

function App() {

  const [respData, setRespData] = useState(data);

  useEffect(() => {
    const targetAPI = setupConfig.backend_port + setupConfig.api_list.find_forwarder_diff_all_hosp;
    doGetRequest(targetAPI).then(data => setRespData(data));
  }, [])

  return (
    <div className="App">
      <div style={{width: '30%'}}>
        <ClusterCheckboxes setRespData={setRespData}/>
      </div>
      <div style={{width: '70%'}}>
        <ForwarderVer data={respData}/>
      </div>
        
        
    </div>
);
}

export default App
