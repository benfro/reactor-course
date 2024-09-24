package net.benfro.lab.reactor.S03_flux.assignment2;

import java.time.Duration;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockInvestor implements Subscriber<Integer> {

    int startCapital = 1000;
    int buyLimit = 90;
    int sellLimit = 110;
    int result = 0;
    int noOfStocks = 0;
    Subscription subscription;


    public static void main(String[] args) throws InterruptedException {
        var client = new StockServiceClient();
        client.getPriceChanges()
            .subscribe(new StockInvestor());

        Thread.sleep(Duration.ofSeconds(30));
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        result = startCapital;

        this.subscription = subscription;
    }

    @Override
    public void onNext(Integer course) {
        if (course <= buyLimit && result >= course) {
            log.info("Buying at price {} from own capital {}", course, result);
            result -= course;
            noOfStocks++;
        } else if (course >= sellLimit) {
            log.info("Selling at price {}", course);
            result += course * noOfStocks;
            subscription.cancel();
            log.info("Completed. Result is {}", result);
        } else {
            log.info("Ignored - Course is {}", course);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("Completed. Result is {}", result);
    }
}
