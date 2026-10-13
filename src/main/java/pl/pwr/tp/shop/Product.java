package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A product that can be sold in the shop.
 *
 * @param sku   unique product code
 * @param name  human readable name
 * @param price unit price, never negative
 */
public record Product(String sku, String name, BigDecimal price) {

    /**
     * Validates the product data.
     */
    public Product {
        Objects.requireNonNull(sku, "sku");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(price, "price");
        if (price.signum() < 0) {
            throw new IllegalArgumentException("Price cannot be negative: " + price);
        }
    }
}
