package ha.crc2Jasper.forwarderVerComparison;

import java.util.Map;

public class DataUtils {
    private DataUtils() {
    }

    private static final Database db = Database.getInstance();

//    public static void compareForwarderVersion_allHosp() {
//        Map<String, Map<String, CMSFunction>> allFuncHospMap = db.getAllFuncHospMap();
//        if(allFuncHospMap.isEmpty()){
//            return;
//        }
//        allFuncHospMap.forEach((function, hospMap) -> {
//            hospMap.forEach((hospCode, cmsFunction) -> {
//                if(cmsFunction.getVersion().contains(",")){
//                    db.addFuncWithDiffVersion(function, hospCode, cmsFunction.getVersion());
//                }
//            });
//        });
//    }


}
