package net.benfro.lab.reactor.common.webclient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ConfigurableWebClient extends AbstractHttpClient {

    public Flux<String> stringFlux(String uri) {
        return this.httpClient.get()
                .uri(uri)
                .responseContent()
                .asString();
    }

    public Flux<Integer> intFlux(String uri) {
        return this.httpClient.get()
                .uri(uri)
                .responseContent()
                .asString()
                .map(Integer::parseInt);
    }

    public Mono<String> stringMono(int id, String uri) {
        return this.httpClient.get()
                .uri(uri + id)
                .responseContent()
                .asString()
                .next();
    }

    public Mono<String> stringMono(String uri) {
        return this.httpClient.get()
            .uri(uri)
            .responseContent()
            .asString()
            .next();
    }

    public Mono<Integer> intMono(int id, String uri) {
        return this.httpClient.get()
                .uri(uri + id)
                .responseContent()
                .asString()
                .map(Integer::parseInt)
                .next();
    }

}
