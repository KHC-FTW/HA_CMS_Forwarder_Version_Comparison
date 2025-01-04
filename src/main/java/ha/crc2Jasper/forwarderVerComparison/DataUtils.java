package ha.crc2Jasper.forwarderVerComparison;

import ha.crc2Jasper.forwarderVerComparison.component.CMSFunction;
import ha.crc2Jasper.forwarderVerComparison.component.Cluster;
import ha.crc2Jasper.forwarderVerComparison.component.PayloadHospSelection;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {
    private DataUtils() {}

    private static final Database db = Database.getInstance();

    public static Map<String, Map<String, CMSFunction>> compareForwarderVersion(List<String> hospToCompare) {
        Map<String, Map<String, CMSFunction>> allFuncHospMap = db.getAllFuncHospMap();
        Map<String, Map<String, CMSFunction>> results = new LinkedHashMap<>();
        if(!allFuncHospMap.isEmpty()){
            allFuncHospMap.forEach((function, hospMap) -> {
                for(int i = 0; i < hospToCompare.size() - 1; i++) {
                    CMSFunction currCmsFunction = hospMap.get(hospToCompare.get(i));
                    CMSFunction nextCmsFunction = hospMap.get(hospToCompare.get(i + 1));
                    if(!currCmsFunction.getVersion().equals(nextCmsFunction.getVersion())) {
                        Map<String, CMSFunction> diffMap = new LinkedHashMap<>();
                        hospToCompare.forEach(hospCode -> {
                            CMSFunction cmsFunction = hospMap.get(hospCode);
                            diffMap.put(hospCode, cmsFunction);
                        });
                        results.put(function, diffMap);
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
