package net.benfro.lab.reactor.S13_context.ratelimiter;

import java.util.Map;
import java.util.function.Function;

import reactor.util.context.Context;

public class UserService {

    private static final Map<String, String> USER_CATEGOR = Map.of(
        "sam", "standard",
        "mike", "prime"
    );

    static Function<Context, Context> userCategoryContext() {
        return ctx -> ctx.<String>getOrEmpty("user")
            .filter(USER_CATEGOR::containsKey)
            .map(USER_CATEGOR::get)
            .map(category -> ctx.put("category", category))
            .orElse(Context.empty()); // Prevent "hack" by entering ["category", "prime"] on producer side
    }
}
