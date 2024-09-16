package net.benfro.lab.reactor.helper;

import java.util.function.Consumer;

import net.benfro.lab.reactor.common.Util;
import reactor.core.publisher.FluxSink;

public class NameGenerator implements Consumer<FluxSink<String>> {

    private FluxSink<String> sink;

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        this.sink = stringFluxSink;
    }

    public void generate(int numNames) {
        for (int i = 0; i < numNames; i++) {
            this.sink.next(Util.faker.name().firstName());
        }
        //sink.complete();
    }
}
