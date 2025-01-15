import { useEffect, useState } from 'react';
import './style/App.css';
import ClusterCheckboxes from './component/ClusterCheckbox';
import ForwarderVer2 from './component/ForwarderVer2.jsx';
import data from './param_config/sampleResponse.json';
import setupConfig from './param_config/setupConfig.json';
import { doGetRequest } from './jsUtils.js';

function App() {
  const [respData, setRespData] = useState(data);
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  useEffect(() => {
    const targetAPI = setupConfig.backend_port + setupConfig.api_list.find_forwarder_diff_all_hosp;
    doGetRequest(targetAPI).then(data => setRespData(data));
  }, []);

  const toggleSidebar = () => {
    const status = !isSidebarOpen
    setIsSidebarOpen(status);
  };

  return (
    <div className="App">
      <div className='sidebar' onClick={toggleSidebar}>
        按此
        {isSidebarOpen ? <span className='close-wording'>關閉</span>: <span className='open-wording'>展開</span>}
        醫院列表
      </div>
      <div className={`left-side ${isSidebarOpen ? 'open' : ''}`}>
          <ClusterCheckboxes setRespData={setRespData} />
      </div>
      <div className='right-side'>
        <ForwarderVer2 data={respData} />
      </div>
    </div>
  );
}

export default App;
