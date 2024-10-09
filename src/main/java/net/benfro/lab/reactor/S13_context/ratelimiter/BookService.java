package net.benfro.lab.reactor.S13_context.ratelimiter;

import net.benfro.lab.reactor.common.webclient.AbstractHttpClient;
import reactor.core.publisher.Mono;

public class BookService extends AbstractHttpClient {

    public Mono<String> getBook() {
        return httpClient.get()
            .uri("/demo07/book")
            .responseContent()
            .asString()
            .startWith(RateLimiter.limitCalls()) // Throws error on "not allowed"
            .contextWrite(UserService.userCategoryContext()) // Counts down on allowed access number
            .next();
    }

}
