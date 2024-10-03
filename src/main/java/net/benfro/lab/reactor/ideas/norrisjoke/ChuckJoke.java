package net.benfro.lab.reactor.ideas.norrisjoke;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChuckJoke {

    @JsonIgnore
    List<String> categories;
    @JsonIgnore
    String created_at;
    String icon_url;
    String id;
    @JsonIgnore
    String updated_at;
    String url;
    String value;


}
