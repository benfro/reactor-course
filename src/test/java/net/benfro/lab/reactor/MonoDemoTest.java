package net.benfro.lab.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MonoDemoTest {

    MonoDemo instance = new MonoDemo();

    @Test
    void testSubscribe() {
        instance.getMono("hej")
                .subscribe(i -> log.info(i));
    }
}