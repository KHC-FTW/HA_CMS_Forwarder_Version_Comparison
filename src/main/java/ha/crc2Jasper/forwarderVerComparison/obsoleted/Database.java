package ha.crc2Jasper.forwarderVerComparison.obsoleted;

public class Database {
    /* Obsoleted class */
    /*private Database() {
        hospMapPlaceholder = createHospPlaceholderMap();
        allHospCode = new ArrayList<>(hospMapPlaceholder.keySet());
    }

    @Getter
    private static Database instance = new Database();
    private final Map<String, Map<String, CMSFunction>> allFuncHospMap = new LinkedHashMap<>();
    private Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHosp;
    private final Map<String, CMSFunction> hospMapPlaceholder;
    private final List<String> allHospCode;
    private Response preComputedResponseAllHosp;

    public void resetData(){
        allFuncHospMap.clear();
        funcWithDiffVerMap_allHosp = null;
        preComputedResponseAllHosp = null;
    }

    public void addNewHospItem(String function, String hospCode, String version, String context_root){
        if(hospMapPlaceholder.containsKey(hospCode)){
            if (version.isBlank()) version = "";
            if (context_root.isBlank()) context_root = "";

            if(allFuncHospMap.containsKey(function)){
                Map<String, CMSFunction> hospMap = allFuncHospMap.get(function);
                CMSFunction existingCMSFunction = hospMap.get(hospCode);
                String existingContextRoot = existingCMSFunction.getContext_root();
                if(existingContextRoot.isBlank()){
                    existingCMSFunction.setAll(function, hospCode, version, context_root);
                    return;
                }
                if(!existingContextRoot.contains(context_root)){
                    existingCMSFunction.setContext_root(existingContextRoot + "\n\n" + context_root);
                }
            }else{
                Map<String, CMSFunction> hospMap = deepCopyFromHospMapPlaceholder();
                hospMap.get(hospCode).setAll(function, hospCode, version, context_root);
                allFuncHospMap.put(function, hospMap);
            }
        }
    }

    private Map<String, CMSFunction> createHospPlaceholderMap(){
        Map<String, CMSFunction> hospMap = new LinkedHashMap<>();
        final SetupConfig SETUP_CONFIG = SetupConfig.getInstance();
        SETUP_CONFIG.getAllClusters().forEach(cluster -> {
            cluster.getHospList().forEach(hospCode -> {
                hospMap.put(hospCode, new CMSFunction("", hospCode, "", ""));
            });
        });
        return hospMap;
    }

    private Map<String, CMSFunction> deepCopyFromHospMapPlaceholder(){
        Map<String, CMSFunction> copyResult = new LinkedHashMap<>();
        hospMapPlaceholder.forEach((key, value) -> {
            copyResult.put(key, new CMSFunction(value));
        });
        return copyResult;
    }*/

}
