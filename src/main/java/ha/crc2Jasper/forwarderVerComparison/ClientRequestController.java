package ha.crc2Jasper.forwarderVerComparison;

import ha.crc2Jasper.forwarderVerComparison.component.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/api")
public class ClientRequestController {
    private ClientRequestController() {}
    private static final Database DB = Database.getInstance();

    @ResponseBody
    @GetMapping("/v1/testGET")
    public Response retrieveTest() {
        return new Response("GET request successful!", 0, null);
    }

    @ResponseBody
    @GetMapping("/v1/find-forwarder-diff/all-hosp")
    public Response v1_find_forwarder_diff_all_hosp() {
        Response response = DB.getPreComputedResponseAllHosp();
        return response != null ? response : new Response();
    }

    @ResponseBody
    @GetMapping("/v1/get-all-forwarders/all-hosp")
    public Response v1_get_all_forwarders_all_hosp() {
        Map<String, Map<String, CMSFunction>> allFuncHospMap = DB.getAllFuncHospMap();
        Response response = DataUtils.createResponse(allFuncHospMap);
        return response != null ? response : new Response();
    }

    @ResponseBody
    @PostMapping("/v1/find-forwarder-diff/selected-hosp")
    public Response v1_find_forwarder_diff_selected_hosp(@RequestBody PayloadHospSelection payload) {
        Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp = DB.getFuncWithDiffVerMap_allHosp();
        if (funcWithDiffVerMap_allHosp != null) {
            List<String> hospList = DataUtils.getHospListFromPayload(payload);
            Map<String, Map<String, CMSFunction>> results = DataUtils.compareForwarderVersion_V2(hospList);
            return DataUtils.createResponse(results);
        }
        return new Response();
    }

    @ResponseBody
    @GetMapping("/v1/find-forwarder-diff-by-cluster/{cluster}")
    public Response v1_find_forwarder_diff_by_cluster(@PathVariable("cluster") String cluster) {
        Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp = DB.getFuncWithDiffVerMap_allHosp();
        final SetupConfig SETUP_CONFIG = SetupConfig.getInstance();
        if (funcWithDiffVerMap_allHosp != null) {
            List<String> hospList = SETUP_CONFIG.getHospListByCluster(cluster);
            if(hospList != null) {
                Map<String, Map<String, CMSFunction>> results = DataUtils.compareForwarderVersion(hospList, funcWithDiffVerMap_allHosp);
                return DataUtils.createResponse(results);
            }
            String errorMsg = "Error: Cluster \"" + cluster + "\" not found! Only valid clusters are accepted: " + SETUP_CONFIG.getValidClusters();
            return new Response(errorMsg, 0, null);
        }
        return new Response();
    }

    @ResponseBody
    @GetMapping("/v1/find-forwarder-diff-2-hosp/hosp1={hosp1}&hosp2={hosp2}")
    public Response v1_find_forwarder_diff_2_hosp(@PathVariable("hosp1") String hosp1, @PathVariable("hosp2") String hosp2) {
        Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp = DB.getFuncWithDiffVerMap_allHosp();
        Map<String, CMSFunction> hospMapPlaceholder = DB.getHospMapPlaceholder();
        if(hospMapPlaceholder.containsKey(hosp1) && hospMapPlaceholder.containsKey(hosp2)) {
            List<String> hospList = new ArrayList<>(Arrays.asList(hosp1, hosp2));
            Map<String, Map<String, CMSFunction>> results = DataUtils.compareForwarderVersion(hospList, funcWithDiffVerMap_allHosp);
            return DataUtils.createResponse(results);
        }
        String errorMsg = String.format("Error: Get param has invalid hospital code(s): %s, %s (Case-sensitive)", hosp1, hosp2);
        return new Response(errorMsg, 0, null);
    }

}
