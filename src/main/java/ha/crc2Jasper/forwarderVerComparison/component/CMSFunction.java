package ha.crc2Jasper.forwarderVerComparison.component;

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
public class CMSFunction {
    private String function = "";
    private String hospCode = "";
    private String version = "";
    private String context_root = "";
    private String last_updated = "";

    public CMSFunction(CMSFunction other){
        this.function = other.getFunction();
        this.hospCode = other.getHospCode();
        this.version = other.getVersion();
        this.context_root = other.getContext_root();
        this.last_updated = other.getLast_updated();
    }

    public boolean isDiffVersion(CMSFunction other){
        return !(this.context_root.equals(other.getContext_root()) || this.version.equals(other.getVersion()));
    }

    public void setAll(String function, String hospCode, String version, String context_root, String last_updated){
        this.function = function;
        this.hospCode = hospCode;
        this.version = version;
        this.context_root = context_root;
        this.last_updated = last_updated;
    }
}
