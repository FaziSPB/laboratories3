package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final Map<String, CartItem> itemsBySku = new LinkedHashMap<>();

    public void add(Product product, int quantity) {
        CartItem current = itemsBySku.get(product.sku());
        int total = current == null ? quantity : current.quantity() + quantity;
        itemsBySku.put(product.sku(), new CartItem(product, total));
    }

    public void remove(String sku) {
        itemsBySku.remove(sku);
    }

    public List<CartItem> items() {
        return new ArrayList<>(itemsBySku.values());
    }

    public BigDecimal total() {
        return itemsBySku.values().stream()
                .map(CartItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() {
        return itemsBySku.isEmpty();
    }

    public void clear() {
        itemsBySku.clear();
    }
}
