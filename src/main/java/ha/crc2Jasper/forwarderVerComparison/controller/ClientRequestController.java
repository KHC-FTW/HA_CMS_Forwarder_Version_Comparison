package ha.crc2Jasper.forwarderVerComparison.controller;

import ha.crc2Jasper.forwarderVerComparison.component.*;
import ha.crc2Jasper.forwarderVerComparison.service.APIService;
import ha.crc2Jasper.forwarderVerComparison.utils.DataUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/api")
public class ClientRequestController {
    private ClientRequestController() {}
//    private static final Database DB = Database.getInstance();
//    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = EventController.getREAD_LOCK();

    @ResponseBody
    @GetMapping("/v1/testGET")
    public Response retrieveTest() {
        return new Response("GET request successful!", "", 0, null);
    }

    @ResponseBody
    @GetMapping("/v2/find-forwarder-diff/all-hosp")
    public Response v2_find_forwarder_diff_all_hosp() {
        return APIService.getSrcForwarderDataReactive(SetupConfig.getInstance().getAllHospCode());
    }

    @ResponseBody
    @PostMapping("/v2/find-forwarder-diff/selected-hosp")
    public Response v2_find_forwarder_diff_selected_hosp(@RequestBody PayloadHospSelection payload) {
        return APIService.getSrcForwarderDataReactive(DataUtils.getHospListFromPayload(payload));
    }

    @ResponseBody
    @GetMapping("/v2/find-forwarder-diff-by-cluster/{cluster}")
    public Response v2_find_forwarder_diff_by_cluster(@PathVariable("cluster") String cluster) {
        final SetupConfig SETUP_CONFIG = SetupConfig.getInstance();
        List<String> hospList = SETUP_CONFIG.getHospListByCluster(cluster);
        if(hospList != null) {
            return APIService.getSrcForwarderDataReactive(hospList);
        }
        String errorMsg = "Error: Cluster '" + cluster + "' not found! Only valid clusters are accepted: " + SETUP_CONFIG.getValidClusters();
        return new Response(errorMsg, "", 0, null);
    }


    /* Below are obsoleted functions */


    /*@ResponseBody
    @GetMapping("/v1/find-forwarder-diff/all-hosp")
    public Response v1_find_forwarder_diff_all_hosp() {
        try{
            READ_LOCK.lock();
            Response response = DB.getPreComputedResponseAllHosp();
            return response != null ? response : new Response();
        } finally {
            READ_LOCK.unlock();
        }
    }

    @ResponseBody
    @GetMapping("/v1/get-all-forwarders/all-hosp")
    public Response v1_get_all_forwarders_all_hosp() {
        try{
            READ_LOCK.lock();
            Map<String, Map<String, CMSFunction>> allFuncHospMap = DB.getAllFuncHospMap();
            return DataUtils.createResponse(allFuncHospMap);
        } finally {
            READ_LOCK.unlock();
        }
    }

    @ResponseBody
    @PostMapping("/v1/find-forwarder-diff/selected-hosp")
    public Response v1_find_forwarder_diff_selected_hosp(@RequestBody PayloadHospSelection payload) {
        try{
            READ_LOCK.lock();
            Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp = DB.getFuncWithDiffVerMap_allHosp();
            if (funcWithDiffVerMap_allHosp != null) {
                List<String> hospList = DataUtils.getHospListFromPayload(payload);
                Map<String, Map<String, CMSFunction>> results = DataUtils.compareForwarderVersion(hospList, funcWithDiffVerMap_allHosp);
                return DataUtils.createResponse(results);
            }
            return new Response();
        } finally {
            READ_LOCK.unlock();
        }
    }

    @ResponseBody
    @GetMapping("/v1/find-forwarder-diff-by-cluster/{cluster}")
    public Response v1_find_forwarder_diff_by_cluster(@PathVariable("cluster") String cluster) {
        try{
            READ_LOCK.lock();
            Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp = DB.getFuncWithDiffVerMap_allHosp();
            final SetupConfig SETUP_CONFIG = SetupConfig.getInstance();
            if (funcWithDiffVerMap_allHosp != null) {
                List<String> hospList = SETUP_CONFIG.getHospListByCluster(cluster);
                if(hospList != null) {
                    Map<String, Map<String, CMSFunction>> results = DataUtils.compareForwarderVersion(hospList, funcWithDiffVerMap_allHosp);
                    return DataUtils.createResponse(results);
                }
                String errorMsg = "Error: Cluster '" + cluster + "' not found! Only valid clusters are accepted: " + SETUP_CONFIG.getValidClusters();
                return new Response(errorMsg, 0, null);
            }
            return new Response();
        } finally {
            READ_LOCK.unlock();
        }
    }

    @ResponseBody
    @GetMapping("/v1/find-forwarder-diff-2-hosp/hosp1={hosp1}&hosp2={hosp2}")
    public Response v1_find_forwarder_diff_2_hosp(@PathVariable("hosp1") String hosp1, @PathVariable("hosp2") String hosp2) {
        try{
            READ_LOCK.lock();
            Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp = DB.getFuncWithDiffVerMap_allHosp();
            Map<String, CMSFunction> hospMapPlaceholder = DB.getHospMapPlaceholder();
            if(hospMapPlaceholder.containsKey(hosp1) && hospMapPlaceholder.containsKey(hosp2)) {
                List<String> hospList = new ArrayList<>(Arrays.asList(hosp1, hosp2));
                Map<String, Map<String, CMSFunction>> results = DataUtils.compareForwarderVersion(hospList, funcWithDiffVerMap_allHosp);
                return DataUtils.createResponse(results);
            }
            String errorMsg = String.format("Error: GET param has invalid hospital code(s): %s, %s (Uppercase needed)", hosp1, hosp2);
            return new Response(errorMsg, 0, null);
        } finally {
            READ_LOCK.unlock();
        }
    }

    @ResponseBody
    @GetMapping("/v1/find-forwarder-diff-by-function/{function}")
    public Response v1_find_forwarder_diff_by_function(@PathVariable("function") String function) {
        try{
            READ_LOCK.lock();
            Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp = DB.getFuncWithDiffVerMap_allHosp();
            if (funcWithDiffVerMap_allHosp != null && funcWithDiffVerMap_allHosp.containsKey(function)) {
                Map<String, Map<String, CMSFunction>> results = Collections.singletonMap(function, funcWithDiffVerMap_allHosp.get(function));
                return DataUtils.createResponse(results);
            }
            String errorMsg = String.format("Error: GET param has invalid function name: '%s'", function);
            return new Response(errorMsg, 0, null);
        } finally {
            READ_LOCK.unlock();
        }
    }*/
}
