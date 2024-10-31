package net.benfro.lab.reactor.S10_batching.assignment.grouping;

import com.github.javafaker.Faker;
import lombok.Getter;

public record PurchaseOrder(
    @Getter String item,
    @Getter String category,
    @Getter Integer price) {
    private static final Faker faker = new Faker();

    public static PurchaseOrder generate() {
        var item = faker.commerce().productName();
        var category = faker.commerce().department();
        var price = faker.number().numberBetween(50, 300);
        return new PurchaseOrder(item, category, price);
    }

    public PurchaseOrder freebie() {
        return new PurchaseOrder(item, category, 0);
    }

}
