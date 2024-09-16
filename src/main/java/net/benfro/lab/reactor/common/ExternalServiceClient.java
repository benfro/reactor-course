package net.benfro.lab.reactor.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<String> getProductName(int productId) {
        return this.httpClient.get()
            .uri("/demo01/product/" + productId)
            .responseContent()
            .asString()
            .next();
    }

    public Flux<String> getNames() {
        return this.httpClient.get()
            .uri("/demo02/name/stream")
            .responseContent()
            .asString();
    }

    public Flux<Integer> getPriceChanges() {
        return this.httpClient.get()
            .uri("/demo02/stock/stream")
            .responseContent()
            .asString()
            .map(Integer::parseInt);
    }

}
