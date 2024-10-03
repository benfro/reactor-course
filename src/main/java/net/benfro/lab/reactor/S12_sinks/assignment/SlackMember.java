package net.benfro.lab.reactor.S12_sinks.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.BaseSubscriberAdapter;
import reactor.core.publisher.Sinks;

@Slf4j
@Data
@AllArgsConstructor
public class SlackMember extends BaseSubscriberAdapter<String> {

    private String name;
    private Sinks.Many<String> sink;

    public SlackMember (String name) {
        this.name = name;
        requestUnbounded();
    }

    public void connect(Sinks.Many<String> many) {
        sink = many;
    }

    public void says(String message) {
        sink.tryEmitNext(message);
    }

    @Override
    protected void hookOnNext(String value) {
        log.info("{} says {}",name, value);
    }
}
