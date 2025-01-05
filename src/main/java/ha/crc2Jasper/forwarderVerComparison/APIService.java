package ha.crc2Jasper.forwarderVerComparison;

import ha.crc2Jasper.forwarderVerComparison.component.SetupConfig;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class APIService {
    private APIService() {}
    private static final WebClient WEB_CLIENT = WebClientConfig.getWebClient();
    private static final SetupConfig SETUP_CONFIG = SetupConfig.getInstance();

    private static final String SAMPLE_API = "http://160.98.17.133:30083/ForwarderTable/weblogic_12c.json";
    private static final String SAMPLE_TEMP_DIR = "C:\\Users\\CKH637\\Desktop\\CKH637_DocsAndStorage\\_Project\\HA_CMS_Forwarder_Version_Comparison\\test.json";

    public static void getSrcForwarderData() {
        WEB_CLIENT.get()
                .uri(SAMPLE_API)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(resp -> {
                    JsonDataParser.parseForwarderData(resp);
                    return Mono.empty();
                });
    }
}
