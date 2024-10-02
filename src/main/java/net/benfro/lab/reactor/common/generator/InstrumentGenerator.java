package net.benfro.lab.reactor.common.generator;

public class InstrumentGenerator extends AbstractFluxSinkGenerator<String> {

    public void generate() {
        this.sink.next(GenerateUtility.faker.music().instrument());
    }
}
