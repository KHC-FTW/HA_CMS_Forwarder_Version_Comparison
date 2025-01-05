package ha.crc2Jasper.forwarderVerComparison;

import ha.crc2Jasper.forwarderVerComparison.component.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/api/v1")
public class ClientRequestController {
    private ClientRequestController() {}
    private static final SetupConfig SETUP_CONFIG = SetupConfig.getInstance();
    private static final Database DB = Database.getInstance();
    private static final String VALID_CLUSTERS = String.join(", ", SETUP_CONFIG.getAllClusters()
            .getPayload().stream().map(Cluster::getCluster).toList());

    @ResponseBody
    @GetMapping("/testGET")
    public Response retrieveTest() {
        return new Response("GET request successful!", 0, null);
    }

    @ResponseBody
    @GetMapping("/find-forwarder-diff/all-hosp")
    public Response getFuncWithDiffVer_allHosp() {
        Response response = DB.getPreComputedResponseAllHosp();
        return response != null ? response : new Response();
    }

    @ResponseBody
    @PostMapping("/find-forwarder-diff/selected-hosp")
    public Response getFuncWithDiffVer_selectedHosp(@RequestBody PayloadHospSelection payload) {
        Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp = DB.getFuncWithDiffVerMap_allHosp();
        if (funcWithDiffVerMap_allHosp != null) {
            List<String> hospList = DataUtils.getHospListFromPayload(payload);
            Map<String, Map<String, CMSFunction>> results = DataUtils.compareForwarderVersion(hospList, funcWithDiffVerMap_allHosp);
            return DataUtils.createResponse(results);
        }
        return new Response();
    }

    @ResponseBody
    @GetMapping("/find-forwarder-diff-by-cluster/{cluster}")
    public Response getFuncWithDiffVer_byCluster(@PathVariable String cluster) {
        Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp = DB.getFuncWithDiffVerMap_allHosp();
        if (funcWithDiffVerMap_allHosp != null) {
            List<String> hospList = SETUP_CONFIG.getHospListByCluster(cluster);
            if(hospList != null) {
                Map<String, Map<String, CMSFunction>> results = DataUtils.compareForwarderVersion(hospList, funcWithDiffVerMap_allHosp);
                return DataUtils.createResponse(results);
            }
            String errorMsg = "Error: Cluster \"" + cluster + "\" not found! Only valid clusters are accepted: " + VALID_CLUSTERS;
            return new Response(errorMsg, 0, null);
        }
        return new Response();
    }

}
