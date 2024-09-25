package net.benfro.lab.reactor.S09_combinations.zipassignment;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import net.benfro.lab.reactor.webclient.ConfigurableWebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class ZipAssignment {

    public static void main(String[] args) {
        var webClient = new ConfigurableWebClient();

        for (int i = 1; i <= 10; i++) {
            var productStream = webClient.stringMono(i, "/demo05/product/").delayElement(Duration.ofSeconds(2));
            var reviewStream = webClient.stringMono(i, "/demo05/review/").delayElement(Duration.ofSeconds(3));;
            var priceStream = webClient.stringMono(i, "/demo05/price/").delayElement(Duration.ofSeconds(1));;

            Mono.zip(productStream, reviewStream, priceStream)
                    .map(p -> new Product(p.getT1(), p.getT2(), p.getT3()))
                    .doOnNext(p -> log.info("Product: {}", p))
                    .subscribe(DefaultSubscriber.of("Product Zipper"));
            ;
        }

        RunUtilities.sleep(5);
    }
}
