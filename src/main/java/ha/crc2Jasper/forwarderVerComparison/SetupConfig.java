package ha.crc2Jasper.forwarderVerComparison;

import java.util.ArrayList;
import java.util.List;

public class SetupConfig {
    private static final SetupConfig instance = new SetupConfig();
    public static SetupConfig getInstance() {return instance;}

    private String cms_forwarder_ver_API;
    private List<Cluster> allClusters = new ArrayList<>();

    public List<Cluster> getAllClusters() {
        return allClusters;
    }

    public class Cluster{
        private String name;
        private List<String> hospCodeList = new ArrayList<>();

        public List<String> getHospCodeList() {
            return hospCodeList;
        }
    }

}
