import { useState } from 'react'
import './style/App.css'
import ClusterCheckboxes from './component/ClusterCheckbox'
import ForwarderVer from './component/ForwarderVer';

function App() {
  return (
    <div className="App">
      <div style={{width: '30%'}}>
        <ClusterCheckboxes />
      </div>
      <div style={{width: '70%'}}>
        <ForwarderVer />
      </div>
        
        
    </div>
);
}

export default App
