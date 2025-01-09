package ha.crc2Jasper.forwarderVerComparison;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class JsonDataParser {
    private JsonDataParser() {}
    private static final ObjectMapper objectMapper = SystemIni.getObjectMapper();
    private static final Database DB = Database.getInstance();

    public static void parseForwarderData(String jsonData) {
        try {
            JsonNode rawJsonData = objectMapper.readTree(jsonData);
            JsonNode allData = rawJsonData.get("data");
            allData.forEach(data -> {
                String function = data.get("function").asText();
                String hospCode = data.get("hosp_code").asText();
                String version = data.get("version").asText();
                DB.addNewHospItem(function, hospCode, version);
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
                DB.addNewHospItem(function, hospCode, version);
            });
            LocalDateTime now = LocalDateTime.now();
            String msg = "Data parsed successfully at " + now;
            DebugUtils.print(msg);
        } catch (Exception e) {
            ExceptionService.printException(e);
        }
    }
}
