package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.util.List;

public record Order(int number, List<CartItem> items, BigDecimal subtotal, BigDecimal discount) {

    public Order {
        items = List.copyOf(items);
    }

    public BigDecimal total() {
        return subtotal.subtract(discount);
    }
}
