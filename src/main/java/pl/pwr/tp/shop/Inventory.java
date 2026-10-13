package pl.pwr.tp.shop;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Keeps the product catalog and the number of units in stock.
 */
public class Inventory {

    private final Map<String, Product> productsBySku = new LinkedHashMap<>();
    private final Map<String, Integer> stockBySku = new LinkedHashMap<>();

    /**
     * Adds units of a product to the stock, registering the product if it is new.
     *
     * @param product  product to add
     * @param quantity number of units, at least 1
     */
    public void addStock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + quantity);
        }
        productsBySku.put(product.sku(), product);
        stockBySku.merge(product.sku(), quantity, Integer::sum);
    }

    /**
     * Finds a product by its code.
     *
     * @param sku product code
     * @return the product, or empty if it is not in the catalog
     */
    public Optional<Product> find(String sku) {
        return Optional.ofNullable(productsBySku.get(sku));
    }

    /**
     * Returns the number of units in stock.
     *
     * @param sku product code
     * @return units in stock, 0 for unknown products
     */
    public int available(String sku) {
        return stockBySku.getOrDefault(sku, 0);
    }

    /**
     * Removes units of a product from the stock.
     *
     * @param sku      product code
     * @param quantity number of units to remove
     * @throws OutOfStockException if there are not enough units
     */
    public void take(String sku, int quantity) {
        int inStock = available(sku);
        if (inStock < quantity) {
            throw new OutOfStockException(sku, quantity, inStock);
        }
        stockBySku.put(sku, inStock - quantity);
    }

    /**
     * Returns all products in the catalog.
     *
     * @return read-only view of the products
     */
    public Collection<Product> products() {
        return Collections.unmodifiableCollection(productsBySku.values());
    }
}
