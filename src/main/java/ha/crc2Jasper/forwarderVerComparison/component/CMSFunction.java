package ha.crc2Jasper.forwarderVerComparison.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CMSFunction {
    private String function = "";
    private String hospCode = "";
    private String version = "";

    public CMSFunction(CMSFunction other){
        this.function = other.getFunction();
        this.hospCode = other.getHospCode();
        this.version = other.getVersion();
    }

    public boolean isDiffVersion(CMSFunction other){
        return !this.version.equals(other.getVersion());
    }
}
