package ha.crc2Jasper.forwarderVerComparison;

import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {
    private WebClientConfig() {}
    private static final int MAX_MEMORY_BUFFER = 50 * 1024 * 1024;
    private static final WebClient webClient = customWebClient();

    public static WebClient getWebClient(){return webClient;}

    private static WebClient customWebClient(){
        return WebClient
                .builder()
                .codecs(item -> item.defaultCodecs()
                        .maxInMemorySize(MAX_MEMORY_BUFFER))
                .build();
    }
}
