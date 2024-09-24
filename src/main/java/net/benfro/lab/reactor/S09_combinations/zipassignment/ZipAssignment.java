package net.benfro.lab.reactor.S09_combinations.zipassignment;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.webclient.ConfigurableWebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class ZipAssignment {

    public static void main(String[] args) {
        var webClient = new ConfigurableWebClient();

        for (int i = 1; i <= 10; i++) {
            var productStream = webClient.stringMono(i, "/demo05/product/");
            var reviewStream = webClient.stringMono(i, "/demo05/review/");
            var priceStream = webClient.intMono(i, "/demo05/price/");

            Mono.zip(productStream, reviewStream, priceStream)
                    .map(p -> new Product(p.getT1(), p.getT2(), p.getT3()))
                    .doOnNext(p -> log.info("Product: {}", p))
                    .subscribe(DefaultSubscriber.of("Product Zipper"));
            ;
        }
    }
}
