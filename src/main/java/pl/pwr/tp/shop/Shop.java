package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Turns shopping carts into orders: checks stock, applies the discount
 * and updates the inventory.
 */
public class Shop {

    private final Inventory inventory;
    private final DiscountPolicy discountPolicy;
    private final List<Order> placedOrders = new ArrayList<>();

    /**
     * Creates a shop.
     *
     * @param inventory      products and stock
     * @param discountPolicy discount applied to every order
     */
    public Shop(Inventory inventory, DiscountPolicy discountPolicy) {
        this.inventory = inventory;
        this.discountPolicy = discountPolicy;
    }

    /**
     * Places an order for everything in the cart and empties the cart.
     * Nothing is taken from the inventory if any product is missing.
     *
     * @param cart customer's cart
     * @return the placed order
     * @throws IllegalStateException if the cart is empty
     * @throws OutOfStockException   if a product does not have enough units
     */
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

    /**
     * Returns all orders placed so far.
     *
     * @return read-only list of orders
     */
    public List<Order> orders() {
        return Collections.unmodifiableList(placedOrders);
    }
}
