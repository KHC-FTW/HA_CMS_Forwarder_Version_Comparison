package ha.crc2Jasper.forwarderVerComparison;

import ha.crc2Jasper.forwarderVerComparison.component.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/api/v1")
public class ClientRequestController {
    private static final SetupConfig SETUP_CONFIG = SetupConfig.getInstance();
    private static final Database DB = Database.getInstance();

    @ResponseBody
    @GetMapping("/testGET")
    public Map<String, Object> retrieveTest() {
        return Collections.singletonMap("Test_Result", DB.getAllFuncHospMap());
    }

    @ResponseBody
    @GetMapping("/find-forwarder-diff/all-hosp")
    public Response getFuncWithDiffVer_allHosp() {
//        List<String> hospList = DataUtils.getHospListFromPayload(SETUP_CONFIG.getAllClusters());
        return null;
    }

    @ResponseBody
    @PostMapping("/find-forwarder-diff/selected-hosp")
    public Response getFuncWithDiffVer_selectedHosp(@RequestBody PayloadHospSelection payload) {
        List<String> hospList = DataUtils.getHospListFromPayload(payload);
        Map<String, Map<String, CMSFunction>> results = DataUtils.compareForwarderVersion(hospList);
        return null;
    }

    @ResponseBody
    @GetMapping("/find-forwarder-diff-by-cluster/{cluster}")
    public Response getFuncWithDiffVer_byCluster(@PathVariable String cluster) {
        return null;
    }

}
