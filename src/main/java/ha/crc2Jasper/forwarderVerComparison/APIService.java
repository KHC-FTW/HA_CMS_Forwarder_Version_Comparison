package ha.crc2Jasper.forwarderVerComparison;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class APIService {
    private APIService() {}
    private static final WebClient webClient = WebClientConfig.getWebClient();

    private static final String SAMPLE_API = "http://160.98.17.133:30083/ForwarderTable/weblogic_12c.json";
    private static final String SAMPLE_TEMP_DIR = "C:\\Users\\CKH637\\Desktop\\CKH637_DocsAndStorage\\_Project\\HA_CMS_Forwarder_Version_Comparison\\test.json";

    public static void getSrcForwarderData() {
        webClient.get()
                .uri(SAMPLE_API)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(resp -> {
                    JsonDataParser.parseForwarderData(resp);
                    return Mono.empty();
                });
    }
}
