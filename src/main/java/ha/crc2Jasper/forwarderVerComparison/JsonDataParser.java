package ha.crc2Jasper.forwarderVerComparison;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ha.crc2Jasper.forwarderVerComparison.component.CMSFunction;
import ha.crc2Jasper.forwarderVerComparison.component.Response;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JsonDataParser {
    private JsonDataParser() {}
    private static final ObjectMapper objectMapper = SystemIni.getObjectMapper();

    public static Response parseForwarderDataAndCompileResponse(Mono<String> monoData, List<String> hospList){
        String jsonData = monoData.block();
        Map<String, Map<String, CMSFunction>> allForwarderHospMap = new TreeMap<>();
        String lastUpdated = "";
        try {
            JsonNode rawJsonData = objectMapper.readTree(jsonData);
            lastUpdated = rawJsonData.get("lastUpdate").asText();
            JsonNode allData = rawJsonData.get("data");
            allData.forEach(data -> {
                String hospCode = data.get("hosp_code").asText();
                if (hospList.contains(hospCode)){
                    String function = data.get("function").asText();
                    String version = data.get("version").asText();
                    String context_root = data.get("context_root").asText();

                    if (version.isBlank()) version = "";
                    if (context_root.isBlank()) context_root = "";

                    if(allForwarderHospMap.containsKey(function)){
                        Map<String, CMSFunction> hospMap = allForwarderHospMap.get(function);
                        CMSFunction existingCMSFunction = hospMap.get(hospCode);
                        String existingContextRoot = existingCMSFunction.getContext_root();
                        if(existingContextRoot.isBlank()){
                            existingCMSFunction.setAll(function, hospCode, version, context_root);
                        }else if(!existingContextRoot.contains(context_root)){
                            existingCMSFunction.setContext_root(existingContextRoot + "\n\n" + context_root);
                        }
                    }else{
                        Map<String, CMSFunction> hospMap = DataUtils.createCustomHospPlaceholderMap(hospList);
                        hospMap.get(hospCode).setAll(function, hospCode, version, context_root);
                        allForwarderHospMap.put(function, hospMap);
                    }
                }
            });
        } catch (Exception e) {
            ExceptionService.printException(e);
        }
        return DataUtils.createResponse(DataUtils.compareForwarderVersion(hospList, allForwarderHospMap), lastUpdated);
    }

    /* Below are obsoleted functions */

    /*    public static void parseForwarderData(String jsonData) {
        try {
            JsonNode rawJsonData = objectMapper.readTree(jsonData);
            JsonNode allData = rawJsonData.get("data");
            allData.forEach(data -> {
                String function = data.get("function").asText();
                String hospCode = data.get("hosp_code").asText();
                String version = data.get("version").asText();
                String context_root = data.get("context_root").asText();
                DB.addNewHospItem(function, hospCode, version, context_root);
            });
        } catch (Exception e) {
            ExceptionService.printException(e);
        }
    }

    public static void parseForwarderData_V2(Mono<String> monoData) {
        String jsonData = monoData.block();
        try {
            JsonNode rawJsonData = objectMapper.readTree(jsonData);
            JsonNode allData = rawJsonData.get("data");
            allData.forEach(data -> {
                String function = data.get("function").asText();
                String hospCode = data.get("hosp_code").asText();
                String version = data.get("version").asText();
                String context_root = data.get("context_root").asText();
                DB.addNewHospItem(function, hospCode, version, context_root);
            });
            LocalDateTime now = LocalDateTime.now();
            String msg = "Data parsed successfully at " + now;
            DebugUtils.print(msg);
        } catch (Exception e) {
            ExceptionService.printException(e);
        }
    }*/
}
