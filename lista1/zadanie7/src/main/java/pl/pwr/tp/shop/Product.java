package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.util.Objects;

public record Product(String sku, String name, BigDecimal price) {

    public Product {
        Objects.requireNonNull(sku, "sku");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(price, "price");
        if (price.signum() < 0) {
            throw new IllegalArgumentException("Price cannot be negative: " + price);
        }
    }
}
