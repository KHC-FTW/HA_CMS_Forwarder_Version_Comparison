package ha.crc2Jasper.forwarderVerComparison;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class Database {
    private Database() {
        hospMapPlaceholder = createHospPlaceholderMap();
    }
    private static Database instance = new Database();
    public static Database getInstance() {return instance;}
    private Map<String, Map<String, CMSFunction>> allFuncHospMap = new LinkedHashMap<>();
    private Map<String, Map<String, CMSFunction>> funcWithDiffVersionMap = new LinkedHashMap<>();
    private Map<String, CMSFunction> hospMapPlaceholder;

    private static final SetupConfig setupConfig = SetupConfig.getInstance();

    public void addNewHospItem(String function, String hospCode, String version){
        if(hospMapPlaceholder.containsKey(hospCode)){
            if(allFuncHospMap.containsKey(function)){
                Map<String, CMSFunction> hospMap = allFuncHospMap.get(function);
                CMSFunction existingCMSFunction = hospMap.get(hospCode);
                String existingVersion = existingCMSFunction.getVersion();
                if(existingVersion.isBlank()){
                    existingCMSFunction.setVersion(version);
                    return;
                }
                if(!existingVersion.equals(version)){
                    existingCMSFunction.setVersion(existingVersion + ", " + version);
                }
            }else{
                Map<String, CMSFunction> hospMap = new LinkedHashMap<>(hospMapPlaceholder);
                CMSFunction existingCMSFunction = hospMap.get(hospCode);
                existingCMSFunction.setFunction(function);
                existingCMSFunction.setHospCode(hospCode);
                existingCMSFunction.setVersion(version);
                allFuncHospMap.put(function, hospMap);
            }
        }
    }

    private Map<String, CMSFunction> createHospPlaceholderMap(){
        Map<String, CMSFunction> hospMap = new LinkedHashMap<>();
        setupConfig.getAllClusters().forEach(cluster -> {
            cluster.getHospCodeList().forEach(hospCode -> {
                hospMap.put(hospCode, new CMSFunction());
            });
        });
        return hospMap;
    }

}
