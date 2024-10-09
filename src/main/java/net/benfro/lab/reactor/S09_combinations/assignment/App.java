package net.benfro.lab.reactor.S09_combinations.assignment;

import net.benfro.lab.reactor.S09_combinations.assignment.user.User;
import net.benfro.lab.reactor.S09_combinations.assignment.user.UserService;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;

public class App {

    // TODO Check if complete!
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
}
