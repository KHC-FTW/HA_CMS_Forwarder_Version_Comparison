package ha.crc2Jasper.forwarderVerComparison;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;

public class APIService {
    private APIService() {
    }

    private static final WebClient webClient = WebClientConfig.customWebClient();

    private static final String SAMPLE_API = "http://160.98.17.133:30083/ForwarderTable/weblogic_12c.json";
    private static final String SAMPLE_TEMP_DIR = "C:\\Users\\CKH637\\Desktop\\CKH637_DocsAndStorage\\_Project\\HA_CMS_Forwarder_Version_Comparison\\test.json";

    public static void getSrcForwarderData() {
        String results = webClient
                .get()
                .uri(SAMPLE_API)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JsonDataParser.parseForwarderData(results);
    }
}
