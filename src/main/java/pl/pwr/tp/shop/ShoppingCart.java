package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Products chosen by a customer before checkout.
 */
public class ShoppingCart {

    private final Map<String, CartItem> itemsBySku = new LinkedHashMap<>();

    /**
     * Adds units of a product, increasing the quantity if it is already in the cart.
     *
     * @param product  product to add
     * @param quantity number of units, at least 1
     */
    public void add(Product product, int quantity) {
        CartItem current = itemsBySku.get(product.sku());
        int total = current == null ? quantity : current.quantity() + quantity;
        itemsBySku.put(product.sku(), new CartItem(product, total));
    }

    /**
     * Removes a product from the cart.
     *
     * @param sku product code
     */
    public void remove(String sku) {
        itemsBySku.remove(sku);
    }

    /**
     * Returns the items in the order they were added.
     *
     * @return copy of the cart items
     */
    public List<CartItem> items() {
        return new ArrayList<>(itemsBySku.values());
    }

    /**
     * Returns the sum of all item subtotals.
     *
     * @return total price before discounts
     */
    public BigDecimal total() {
        return itemsBySku.values().stream()
                .map(CartItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Tells whether the cart has no items.
     *
     * @return true if the cart is empty
     */
    public boolean isEmpty() {
        return itemsBySku.isEmpty();
    }

    /**
     * Removes all items.
     */
    public void clear() {
        itemsBySku.clear();
    }
}
