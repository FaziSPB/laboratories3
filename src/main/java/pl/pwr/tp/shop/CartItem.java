package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A product together with the ordered quantity.
 *
 * @param product  ordered product
 * @param quantity number of units, at least 1
 */
public record CartItem(Product product, int quantity) {

    /**
     * Validates the item data.
     */
    public CartItem {
        Objects.requireNonNull(product, "product");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + quantity);
        }
    }

    /**
     * Returns the price of all units of this item.
     *
     * @return unit price multiplied by quantity
     */
    public BigDecimal subtotal() {
        return product.price().multiply(BigDecimal.valueOf(quantity));
    }
}
