package net.benfro.lab.reactor.S03_flux.assignment;

import net.benfro.lab.reactor.common.webclient.AbstractHttpClient;
import reactor.core.publisher.Flux;

public class StockServiceClient extends AbstractHttpClient {

    public Flux<Integer> getPriceChanges() {
        return this.httpClient.get()
            .uri("/demo02/stock/stream")
            .responseContent()
            .asString()
            .map(Integer::parseInt);
    }
}
