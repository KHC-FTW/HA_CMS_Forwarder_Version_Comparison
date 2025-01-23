package ha.crc2Jasper.forwarderVerComparison.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetupConfig {

    private static SetupConfig instance = new SetupConfig();

    public static SetupConfig getInstance() { return instance; }

    public static void setInstance(SetupConfig instance) {
        SetupConfig.instance = instance;
    }

    @JsonIgnore
    private String configInputPath = "";
    @JsonIgnore
    private final String ROW_FORMAT = "%-25s | %s";
    @JsonIgnore
    private String validClusters = "";
    @JsonIgnore
    private List<String> allHospCode;
    @JsonIgnore
    private List<String> allClusterAndHospCode;
    @JsonIgnore
    private Pattern clusterHospCodePattern;

    @JsonProperty("cms_forwarder_API_host")
    private String cms_forwarder_API_host;

    @JsonProperty("cms_forwarder_API")
    private String cms_forwarder_ver_API;

    @JsonProperty("allClusters")
    private List<Cluster> allClusters = new ArrayList<>();

    public List<String> getHospListByCluster(String cluster) {
        for (Cluster c : allClusters) {
            if (c.getCluster().equalsIgnoreCase(cluster)) {
                return c.getHospList();
            }
        }
        return null;
    }

    @Override
    public String toString(){
        StringBuilder results = new StringBuilder(String.format(ROW_FORMAT, "< PARAMETERS >", "< VALUES >\n"))
                .append(String.format(ROW_FORMAT, "cms_forwarder_API_host", cms_forwarder_API_host)).append("\n")
                .append(String.format(ROW_FORMAT, "cms_forwarder_ver_API", cms_forwarder_ver_API)).append("\n");

        Cluster firstCluster = allClusters.getFirst();
        String firstClusterData = String.format("%-4s : %s", firstCluster.getCluster(), firstCluster.getHospList().toString());

        results.append(String.format(ROW_FORMAT, "allClusters", firstClusterData)).append("\n");
        final int clusterSize = allClusters.size();
        for (int i = 1; i < clusterSize; i++) {
            Cluster cluster = allClusters.get(i);
            results.append(String.format(ROW_FORMAT, "", String.format("%-4s : %s", cluster.getCluster(), cluster.getHospList().toString()))).append("\n");
        }
        return results.toString();
    }
}
