package ha.crc2Jasper.forwarderVerComparison.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetupConfig {
    @Getter
    private static final SetupConfig instance = new SetupConfig();

    private String cms_forwarder_ver_API;
    private PayloadHospSelection allClusters;
}
