package net.benfro.lab.reactor.S12_sinks.assignment;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
@Data
@AllArgsConstructor
public class SlackRoom {

    private final Sinks.Many<String> many = Sinks.many().replay().all();
    private Flux<String> messages;

    private String name;

    public SlackRoom(String name) {
        this(null, name);
    }

    public void addMember(SlackMember member) {
        if (Objects.isNull(messages)) {
            messages = many.asFlux().doOnNext(s -> log.info("room got {} from {}", s, member.getName()));
        }

        messages.subscribe(member);
        member.connect(many);

    }
}
