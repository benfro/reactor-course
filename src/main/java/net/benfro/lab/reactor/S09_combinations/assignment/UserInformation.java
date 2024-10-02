package net.benfro.lab.reactor.S09_combinations.assignment;

import java.util.List;

import net.benfro.lab.reactor.S09_combinations.assignment.order.Order;

public record UserInformation(
    Integer userId,
    String userName,
    Integer balance,
    List<Order> orders) {
}
