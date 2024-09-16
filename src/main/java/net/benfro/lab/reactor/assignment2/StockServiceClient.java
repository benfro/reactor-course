package net.benfro.lab.reactor.assignment2;

import net.benfro.lab.reactor.common.AbstractHttpClient;
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
