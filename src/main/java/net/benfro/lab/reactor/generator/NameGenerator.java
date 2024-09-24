package net.benfro.lab.reactor.generator;

public class NameGenerator extends AbstractFluxSinkGenerator<String> {

    public void generate() {
        this.sink.next(GenerateUtility.faker.name().firstName());
    }
}
