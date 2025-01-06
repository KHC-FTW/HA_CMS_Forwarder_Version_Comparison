package ha.crc2Jasper.forwarderVerComparison;

import ha.crc2Jasper.forwarderVerComparison.component.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {
    private DataUtils() {}

    private static final Database DB = Database.getInstance();

    public static void initializeFuncWithDiffVerMapForAllHosp(){
        if(DB.getFuncWithDiffVerMap_allHosp() == null){
            DB.setFuncWithDiffVerMap_allHosp(compareForwarderVersion(DB.getAllHospCode(), DB.getAllFuncHospMap()));
            DebugUtils.print("FuncWithDiffVerMap_allHosp initialized!");
        }
        if(DB.getPreComputedResponseAllHosp() == null){
            DB.setPreComputedResponseAllHosp(createResponse(DB.getFuncWithDiffVerMap_allHosp()));
            DebugUtils.print("PreComputedResponseAllHosp initialized!");
        }
    }

    public static Response createResponse(Map<String, Map<String, CMSFunction>> funcHospMap){
        List<Result> results = new ArrayList<>();
        funcHospMap.forEach((function, hospMap) -> {
            Result result = new Result(function, hospMap.values().stream().toList());
            results.add(result);
        });
        return new Response("OK", results.size(), results);
    }

    public static Map<String, Map<String, CMSFunction>> compareForwarderVersion_V2(List<String> hospToCompare) {
        Map<String, Map<String, CMSFunction>> funcWithDiffVerMap_allHospital = DB.getFuncWithDiffVerMap_allHosp();
        Map<String, Map<String, CMSFunction>> results = new LinkedHashMap<>();
        funcWithDiffVerMap_allHospital.forEach((function, hospMap) -> {
            Map<String, CMSFunction> diffMap = new LinkedHashMap<>();
            hospToCompare.forEach(hospCode -> {
                diffMap.put(hospCode, hospMap.get(hospCode));
            });
            results.put(function, diffMap);
        });
        return results;
    }


    public static Map<String, Map<String, CMSFunction>> compareForwarderVersion(
            List<String> hospToCompare,
            Map<String, Map<String, CMSFunction>> funcHospMap) {

        Map<String, Map<String, CMSFunction>> results = new LinkedHashMap<>();
        if(!funcHospMap.isEmpty()){
            funcHospMap.forEach((function, hospMap) -> {
                for(int i = 0; i < hospToCompare.size() - 1; i++) {
                    CMSFunction currCmsFunction = hospMap.get(hospToCompare.get(i));
                    CMSFunction nextCmsFunction = hospMap.get(hospToCompare.get(i + 1));
                    if(currCmsFunction.isDiffVersion(nextCmsFunction)) {
                        Map<String, CMSFunction> diffMap = new LinkedHashMap<>();
                        hospToCompare.forEach(hospCode -> {
                            CMSFunction cmsFunction = hospMap.get(hospCode);
                            diffMap.put(hospCode, cmsFunction);
                        });
                        results.put(function, diffMap);
                        break;
                    }
                }
            });
        }
        return results;
    }

    public static List<String> getHospListFromPayload(PayloadHospSelection payload) {
        return payload.getPayload()
                .stream().map(Cluster::getHospList)
                .flatMap(List::stream).toList();
    }
}
