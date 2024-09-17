package net.benfro.lab.reactor.assignment3;

import reactor.core.publisher.Flux;

/**
 * - only when subscribed
 * - based on demand
 * - stop when subscriber cancels
 * - produce only requested items
 * - file closed once done
 */
public interface FileReader {

    Flux<String> read(String filepath);
}
