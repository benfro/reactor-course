package net.benfro.lab.reactor.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

@Slf4j
public class FileReaderImpl implements FileReader {

    private final static String BASE_PATH = "src/main/resources/assignment3";

    @Override
    public Flux<String> read(String fileName) {
        //return Flux.defer(() -> Flux.fromIterable(getStrings(fileName)));
        // Correct solution
        return Flux.generate(
            () -> getBufReader(fileName),
            this::readLine,
            this::closeBuffer
        );
    }

    private void closeBuffer(BufferedReader buffer) {
        try {
            log.info("Closing buffer");
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader getBufReader(String fileName) {
        try {
            log.info("Getting reader {}", fileName);
            return Files.newBufferedReader(Paths.get(BASE_PATH, fileName).toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader readLine(BufferedReader reader, SynchronousSink<String> sink) {
        try {
            var line = reader.readLine();
            if (Objects.isNull(line)) {
                sink.complete();
            } else {
                sink.next(line);
            }
            return reader;
        } catch (IOException e) {
            sink.error(e);
        }
        return null;
    }


    private static List<String> getStrings(String fileName) {
        try {
            return Files.readAllLines(Paths.get(BASE_PATH, fileName).toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
