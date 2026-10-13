package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shop {

    private final Inventory inventory;
    private final DiscountPolicy discountPolicy;
    private final List<Order> placedOrders = new ArrayList<>();

    public Shop(Inventory inventory, DiscountPolicy discountPolicy) {
        this.inventory = inventory;
        this.discountPolicy = discountPolicy;
    }

    public Order checkout(ShoppingCart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        List<CartItem> items = cart.items();
        for (CartItem item : items) {
            String sku = item.product().sku();
            int inStock = inventory.available(sku);
            if (inStock < item.quantity()) {
                throw new OutOfStockException(sku, item.quantity(), inStock);
            }
        }
        for (CartItem item : items) {
            inventory.take(item.product().sku(), item.quantity());
        }

        BigDecimal subtotal = cart.total();
        BigDecimal discount = discountPolicy.discountFor(subtotal);
        Order order = new Order(placedOrders.size() + 1, items, subtotal, discount);
        placedOrders.add(order);
        cart.clear();
        return order;
    }

    public List<Order> orders() {
        return Collections.unmodifiableList(placedOrders);
    }
}
