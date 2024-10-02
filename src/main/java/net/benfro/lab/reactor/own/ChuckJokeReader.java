package net.benfro.lab.reactor.own;

import java.time.Duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import net.benfro.lab.reactor.common.webclient.ConfigurableWebClient;

public class ChuckJokeReader {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        new ConfigurableWebClient()
            .getChuckJoke("https://api.chucknorris.io/jokes/random")
            .repeatWhen(flux  -> flux.delayElements(Duration.ofSeconds(2)).take(15))
            .map(s -> getChuck(s, mapper).getValue())
            .subscribe(DefaultSubscriber.of("chuckie"));


        RunUtilities.sleep(30);
    }

    private static ChuckJoke getChuck(String s, ObjectMapper mapper) {
        try {
            return mapper.readValue(s, ChuckJoke.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
