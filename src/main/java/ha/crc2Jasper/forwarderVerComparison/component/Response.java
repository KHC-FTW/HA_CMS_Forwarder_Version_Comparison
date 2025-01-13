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
public class Response {
    private String status = "Error returning correct response! Please try again later.";
    private String lastUpdated = "";
    private int total;
    private List<Result> results = new ArrayList<>();
}
