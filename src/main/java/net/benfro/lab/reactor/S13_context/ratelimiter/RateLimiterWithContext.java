package net.benfro.lab.reactor.S13_context.ratelimiter;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.webclient.ConfigurableWebClient;
import reactor.core.publisher.Flux;

@Slf4j
public class RateLimiterWithContext {

    static Flux<String> getBooks() {
        return new ConfigurableWebClient()
                .stringFlux("/demo07/book");
    }

}
