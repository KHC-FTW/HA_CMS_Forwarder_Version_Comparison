package ha.crc2Jasper.forwarderVerComparison;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataParser {
    private JsonDataParser() {}
    private static final ObjectMapper objectMapper = SystemIni.getObjectMapper();
    private static final Database db = Database.getInstance();

    public static void parseForwarderData(String jsonData) {
        try {
            JsonNode rawJsonData = objectMapper.readTree(jsonData);
            JsonNode allData = rawJsonData.get("data");
            allData.forEach(data -> {
                String function = data.get("function").asText();
                String hospCode = data.get("hosp_code").asText();
                String version = data.get("version").asText();
                db.addNewHospItem(function, hospCode, version);
            });
            System.out.println("Data parsed successfully");
        } catch (Exception e) {
            ExceptionService.printException(e);
        }
    }
}
