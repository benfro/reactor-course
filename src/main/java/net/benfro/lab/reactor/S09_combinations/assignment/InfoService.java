package net.benfro.lab.reactor.S09_combinations.assignment;


import java.util.List;

import net.benfro.lab.reactor.S09_combinations.assignment.order.Order;
import net.benfro.lab.reactor.S09_combinations.assignment.order.OrderService;
import net.benfro.lab.reactor.S09_combinations.assignment.user.User;
import net.benfro.lab.reactor.S09_combinations.assignment.user.UserService;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public class InfoService {

    public static void main(String[] args) {

        Flux<User> allUsers = UserService.getAllUsers();

//        allUsers
//            .map(user -> new UserInformation(user.id(), user.userName(), PaymentService.getBalance(user.id()).block(),
//                OrderService.getUserOrders(user.id()).collectList()))
//            .subscribe(DefaultSubscriber.of());

        allUsers
            .flatMap(InfoService::getUserInformation)
            .subscribe(DefaultSubscriber.of("uff"));

        RunUtilities.sleep(10);
    }

    private static Mono<UserInformation> getUserInformation(User user) {
        return Mono.zip(
            PaymentService.getBalance(user.id()),
            OrderService.getUserOrders(user.id()).collectList()
        ).map(zip -> new UserInformation(user.id(), user.userName(), zip.getT1(), zip.getT2()));
    }
}
