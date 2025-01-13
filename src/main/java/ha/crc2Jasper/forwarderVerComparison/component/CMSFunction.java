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
    private String context_root = "";

    public CMSFunction(CMSFunction other){
        this.function = other.getFunction();
        this.hospCode = other.getHospCode();
        this.version = other.getVersion();
        this.context_root = other.getContext_root();
    }

    public boolean isDiffVersion(CMSFunction other){
        return !this.context_root.equals(other.getContext_root());
    }

    public void setAll(String function, String hospCode, String version, String context_root){
        this.function = function;
        this.hospCode = hospCode;
        this.version = version;
        this.context_root = context_root;
    }
}
