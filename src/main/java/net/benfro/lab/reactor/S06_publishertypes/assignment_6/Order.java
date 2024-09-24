package net.benfro.lab.reactor.S06_publishertypes.assignment_6;

public record Order(String item, String category, int price, int quantity) {

    public static Order fromString(String line) {
        String[] parts = line.split(":");
        return new Order(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }

    public static Order fromMessage(OrderMessage message) {
        return fromString(message.getMsg());
    }

    public int value() {
        return price * quantity;
    }
}
