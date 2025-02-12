import { useEffect, useState } from 'react';
import './style/App.css';
import ClusterCheckboxes from './component/ClusterCheckbox';
import ForwarderVer2 from './component/ForwarderVer2.jsx';
import data from './param_config/sampleResponse.json';
import setupConfig from './param_config/setupConfig.json';
import { doGetRequest } from './jsUtils.js';

function App() {
  const [respData, setRespData] = useState(data);
  const [sortedData, setSortedData] = useState(data);
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [selectedFuncName, setSelectedFuncName] = useState('');

  useEffect(() => {
    const targetAPI = setupConfig.backend_port + setupConfig.api_list.find_forwarder_diff_all_hosp;
    doGetRequest(targetAPI).then(data => setRespData(data));
  }, []);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  const getDate = (dateStr) => {
    // Empty string treated as very old date.
    return dateStr ? new Date(dateStr) : new Date(0);
  };

  const getSortedHospCodes = (respData) => {
    const hospCodeLatestMap = {};
    // Loop through each function and update the most recent last_updated for each hospCode.
    respData.results.forEach(funcItem => {
      funcItem.hospForwarder.forEach(item => {
        const { hospCode, last_updated } = item;
        const itemDate = getDate(last_updated);
        if (!hospCodeLatestMap[hospCode] || hospCodeLatestMap[hospCode] < itemDate) {
          hospCodeLatestMap[hospCode] = itemDate;
        }
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
    respData.results = result;
    return respData;
  };

  useEffect(() => {
    if (respData.results.length === 0) return;
    const sortedHospCodes = getSortedHospCodes(respData);
    const sortedData = applySortingToHospForwarders(respData, sortedHospCodes);
    setSortedData(sortedData);
  }, [respData]);

  return (
    <div className="App">
      <div className='sidebar' onClick={toggleSidebar}>
        按此
        {isSidebarOpen ? <span className='close-wording'>關閉</span>: <span className='open-wording'>展開</span>}
        醫院列表
      </div>
      <div className={`left-side ${isSidebarOpen ? 'open' : ''}`}>
          <ClusterCheckboxes setRespData={setRespData} setIsSidebarOpen={setIsSidebarOpen} setSelectedFuncName={setSelectedFuncName}/>
      </div>
      <div className='right-side'>
        <ForwarderVer2 data={sortedData} setSortedData={setSortedData} selectedFuncName={selectedFuncName} setSelectedFuncName={setSelectedFuncName}/>
      </div>
    </div>
  );
}

export default App;
