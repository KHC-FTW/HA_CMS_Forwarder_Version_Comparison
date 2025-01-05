package ha.crc2Jasper.forwarderVerComparison;

import ha.crc2Jasper.forwarderVerComparison.component.CMSFunction;
import ha.crc2Jasper.forwarderVerComparison.component.Response;
import ha.crc2Jasper.forwarderVerComparison.component.SetupConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Database {
    private Database() {
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
                Map<String, CMSFunction> hospMap = deepCopyFromHospMapPlaceholder();
                CMSFunction emptyCMSFunction = hospMap.get(hospCode);
                emptyCMSFunction.setFunction(function);
                emptyCMSFunction.setHospCode(hospCode);
                emptyCMSFunction.setVersion(version);
                allFuncHospMap.put(function, hospMap);
            }
        }
    }

    private Map<String, CMSFunction> createHospPlaceholderMap(){
        Map<String, CMSFunction> hospMap = new LinkedHashMap<>();
        setupConfig.getAllClusters().getPayload().forEach(cluster -> {
            cluster.getHospList().forEach(hospCode -> {
                hospMap.put(hospCode, new CMSFunction());
            });
        });
        return hospMap;
    }

    private Map<String, CMSFunction> deepCopyFromHospMapPlaceholder(){
        Map<String, CMSFunction> hospMap = new LinkedHashMap<>();
        hospMapPlaceholder.forEach((key, value) -> {
            hospMap.put(key, new CMSFunction(value));
        });
        return hospMap;
    }

}
