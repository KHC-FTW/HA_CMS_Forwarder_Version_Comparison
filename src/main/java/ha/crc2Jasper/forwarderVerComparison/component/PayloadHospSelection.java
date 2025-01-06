package ha.crc2Jasper.forwarderVerComparison.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PayloadHospSelection {
    @JsonProperty("payload")
    private List<Cluster> payload = new ArrayList<>();
}
