package ha.crc2Jasper.forwarderVerComparison.service;

import ha.crc2Jasper.forwarderVerComparison.config.WebClientConfig;
import ha.crc2Jasper.forwarderVerComparison.component.Response;
import ha.crc2Jasper.forwarderVerComparison.component.SetupConfig;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class APIService {
    private APIService() {}
    private static final WebClient WEB_CLIENT = WebClientConfig.getWebClient();

    public static Response getSrcForwarderDataReactive(List<String> hospList) {
        SetupConfig SETUP_CONFIG = SetupConfig.getInstance();
        Mono<String> monoData = WEB_CLIENT.get()
                                    .uri(SETUP_CONFIG.getCms_forwarder_ver_API())
                                    .retrieve()
                                    .bodyToMono(String.class);
        return JsonDataParser.parseForwarderDataAndCompileResponse(monoData, hospList);
    }

    /* Below are obsoleted functions */

    /*    public static void getSrcForwarderData() {
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
    }*/
}
