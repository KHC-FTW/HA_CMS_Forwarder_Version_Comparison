package ha.crc2Jasper.forwarderVerComparison.component;

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
public class Result {
    private String function = "";
    private List<CMSFunction> hospForwarder = new ArrayList<>();
}
