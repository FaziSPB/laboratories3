package pl.pwr.tp.shop;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Inventory {

    private final Map<String, Product> productsBySku = new LinkedHashMap<>();
    private final Map<String, Integer> stockBySku = new LinkedHashMap<>();

    public void addStock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + quantity);
        }
        productsBySku.put(product.sku(), product);
        stockBySku.merge(product.sku(), quantity, Integer::sum);
    }

    public Optional<Product> find(String sku) {
        return Optional.ofNullable(productsBySku.get(sku));
    }

    public int available(String sku) {
        return stockBySku.getOrDefault(sku, 0);
    }

    public void take(String sku, int quantity) {
        int inStock = available(sku);
        if (inStock < quantity) {
            throw new OutOfStockException(sku, quantity, inStock);
        }
        stockBySku.put(sku, inStock - quantity);
    }

    public Collection<Product> products() {
        return Collections.unmodifiableCollection(productsBySku.values());
    }
}
