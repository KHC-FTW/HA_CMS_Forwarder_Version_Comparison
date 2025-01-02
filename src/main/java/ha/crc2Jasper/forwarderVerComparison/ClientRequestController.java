package ha.crc2Jasper.forwarderVerComparison;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@CrossOrigin
@RequestMapping(value = "/api/v1")
public class ClientRequestController {

    private static final Database db = Database.getInstance();

    @ResponseBody
    @GetMapping("/testGET")
    public Map<String, Object> retrieveTest() {
        return Collections.singletonMap("Test_Result", db.getAllFuncHospMap());
    }

}
