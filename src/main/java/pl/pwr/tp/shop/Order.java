package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.util.List;

/**
 * A placed order: what was bought and how much it cost.
 *
 * @param number   order number
 * @param items    bought items
 * @param subtotal total before discount
 * @param discount discount amount
 */
public record Order(int number, List<CartItem> items, BigDecimal subtotal, BigDecimal discount) {

    /**
     * Makes the item list immutable.
     */
    public Order {
        items = List.copyOf(items);
    }

    /**
     * Returns the amount the customer pays.
     *
     * @return subtotal minus discount
     */
    public BigDecimal total() {
        return subtotal.subtract(discount);
    }
}
