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
            for (JsonNode data: allData){
                String function = objectMapper.treeToValue(data.get("function"), String.class);
                String hospCode = objectMapper.treeToValue(data.get("hosp_code"), String.class);
                String version = objectMapper.treeToValue(data.get("version"), String.class);
                db.addNewHospItem(function, hospCode, version);
            }
            System.out.println("Data parsed successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
