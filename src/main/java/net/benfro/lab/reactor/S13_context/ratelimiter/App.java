package net.benfro.lab.reactor.S13_context.ratelimiter;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.util.context.Context;

/**
 * Two classes of users, 'standard' and 'prime' can access a given number of book titles per time unit.
 * Rate is controlled with the Context.
 * Standard: 2 books every 5 seconds
 * Prime: 3 books every five seconds
 */

@Slf4j
public class App {

    public static void main(String[] args) {

        BookService bookService = new BookService();
        for (int i = 0; i < 20; i++) {
            bookService.getBook()
                .contextWrite(Context.of("user", "mike")) // Notify context of current user
                .subscribe(DefaultSubscriber.of());
            RunUtilities.sleep(1); // Slow emission down a bit (every 5 secs we are getting a new batch)
        }

        RunUtilities.sleep(15);
    }

}
