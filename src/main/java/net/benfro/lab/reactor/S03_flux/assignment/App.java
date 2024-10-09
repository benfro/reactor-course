package net.benfro.lab.reactor.S03_flux.assignment;

import java.time.Duration;

import net.benfro.lab.reactor.common.RunUtilities;

public class App {

    /**
     * Requires started backend
     * @param args
     */
    public static void main(String[] args) {

        var client = new StockServiceClient();

        client.getPriceChanges()
            .subscribe(new StockInvestor());

        RunUtilities.sleep(30);
    }
}
