package net.benfro.lab.reactor.S02_mono.assignment1;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.S02_mono.assignment.FileService;
import net.benfro.lab.reactor.S02_mono.assignment.FileServiceImpl;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@Slf4j
class FileServiceImplTest {

    FileService instance = new FileServiceImpl();


    @Test
    @Order(1)
    void testRead() {
        instance.read("one.txt")
                .subscribe(DefaultSubscriber.of());
    }

    @Test
    @Order(2)
    void testDelete() {
        instance.delete("one.txt")
                .subscribe(DefaultSubscriber.of());
    }

    @Test
    @Order(3)
    void testWrite() {
        instance.write("one.txt", "apa")
                .subscribe(DefaultSubscriber.of());
    }
}
