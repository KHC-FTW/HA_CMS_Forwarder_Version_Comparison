package ha.crc2Jasper.forwarderVerComparison;

import ha.crc2Jasper.forwarderVerComparison.component.SetupConfig;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class APIService {
    private APIService() {}
    private static final WebClient WEB_CLIENT = WebClientConfig.getWebClient();

    public static void getSrcForwarderData() {
        SetupConfig SETUP_CONFIG = SetupConfig.getInstance();
        WEB_CLIENT.get()
                .uri(SETUP_CONFIG.getCms_forwarder_ver_API())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(resp -> {
                    JsonDataParser.parseForwarderData(resp);
                    return Mono.empty();
                });
    }

    public static void getSrcForwarderData_V2() {
        SetupConfig SETUP_CONFIG = SetupConfig.getInstance();
        Mono<String> monoData =  WEB_CLIENT.get()
                .uri(SETUP_CONFIG.getCms_forwarder_ver_API())
                .retrieve()
                .bodyToMono(String.class);
        JsonDataParser.parseForwarderData_V2(monoData);
    }
}
