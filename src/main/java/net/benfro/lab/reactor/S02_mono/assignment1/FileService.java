package net.benfro.lab.reactor.S02_mono.assignment1;

import reactor.core.publisher.Mono;

public interface FileService {
    Mono<String> read(String fileName);

    Mono<Void> write(String fileName, String content);

    Mono<Void> delete(String fileName);
}
