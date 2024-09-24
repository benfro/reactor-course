package net.benfro.lab.reactor.S06_publishertypes.assignment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderMessage {

    private final String msg;
}
