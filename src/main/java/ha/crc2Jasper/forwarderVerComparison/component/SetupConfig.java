package ha.crc2Jasper.forwarderVerComparison.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetupConfig {
    @Getter
    private static final SetupConfig instance = new SetupConfig();

    private String cms_forwarder_ver_API;
    private PayloadHospSelection allClusters;

    public List<String> getHospListByCluster(String cluster) {
        for (Cluster c : allClusters.getPayload()) {
            if (c.getCluster().equalsIgnoreCase(cluster)) {
                return c.getHospList();
            }
        }
        return null;
    }
}
