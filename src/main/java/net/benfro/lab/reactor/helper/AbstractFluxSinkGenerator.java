package net.benfro.lab.reactor.helper;

import java.util.function.Consumer;

import reactor.core.publisher.FluxSink;

public abstract class AbstractFluxSinkGenerator<T> implements Consumer<FluxSink<T>> {

    protected FluxSink<T> sink;

    abstract public void generate();

    @Override
    public void accept(FluxSink<T> sink) {
        this.sink = sink;
    }

    public void generate(int numberOf) {
        for (int i = 0; i < numberOf; i++) {
            generate();
        }
    }
}
