package net.benfro.lab.reactor.S02_mono.assignment1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class FileServiceImpl implements FileService {


    private final static String BASE_PATH = "src/main/resources/assignment1";

    @Override
    public Mono<String> read(String fileName) {
        return Mono.fromCallable(
            () -> String.join("", Files.readAllLines(Paths.get(BASE_PATH, fileName).toAbsolutePath()))
        );
    }

    @Override
    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(() -> readInternal(fileName, content));
    }

    @Override
    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(BASE_PATH, fileName).toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void readInternal(String fileName, String content) {
        try {
            Files.writeString(Paths.get(BASE_PATH, fileName).toAbsolutePath(), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
