package net.benfro.lab.reactor.helper;

import net.benfro.lab.reactor.common.Util;

public class InstrumentGenerator extends AbstractFluxSinkGenerator<String> {

    public void generate() {
        this.sink.next(Util.faker.music().instrument());
    }
}
