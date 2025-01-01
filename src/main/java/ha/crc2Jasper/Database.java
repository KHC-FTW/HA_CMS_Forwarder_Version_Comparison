package ha.crc2Jasper;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public void addNewHospItem(String funcName, String hospCode, CMSFunction cmsFunction){
        if(allFuncHospMap.containsKey(funcName)){
            allFuncHospMap.get(funcName).put(hospCode, cmsFunction);
        }else{
            // TODO: set up empty hospMap for all hospitals
            Map<String, CMSFunction> hospMap = new LinkedHashMap<>(hospMapPlaceholder);
            hospMap.put(hospCode, cmsFunction);
            allFuncHospMap.put(funcName, hospMap);
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
