package net.benfro.lab.reactor.common;

import java.time.Duration;

import com.github.javafaker.Faker;

public class Util {

    public final static Faker faker = new Faker();

    public static void sleep(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void noOp() {
    }


}
