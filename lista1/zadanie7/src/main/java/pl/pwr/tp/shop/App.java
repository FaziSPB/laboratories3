package pl.pwr.tp.shop;

import java.math.BigDecimal;

public final class App {

    private App() {
    }

    public static void main(String[] args) {
        Product keyboard = new Product("KB-01", "Keyboard", new BigDecimal("149.99"));
        Product mouse = new Product("MS-02", "Mouse", new BigDecimal("59.90"));
        Product monitor = new Product("MN-03", "Monitor", new BigDecimal("899.00"));

        Inventory inventory = new Inventory();
        inventory.addStock(keyboard, 5);
        inventory.addStock(mouse, 10);
        inventory.addStock(monitor, 1);

        DiscountPolicy discount = new ThresholdDiscount(new BigDecimal("500"), BigDecimal.TEN);
        Shop shop = new Shop(inventory, discount);

        ShoppingCart cart = new ShoppingCart();
        cart.add(keyboard, 1);
        cart.add(mouse, 2);
        printOrder(shop.checkout(cart));

        cart.add(monitor, 1);
        cart.add(keyboard, 1);
        printOrder(shop.checkout(cart));

        cart.add(monitor, 1);
        try {
            shop.checkout(cart);
        } catch (OutOfStockException e) {
            System.out.println("Order rejected: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Stock left:");
        for (Product product : inventory.products()) {
            System.out.printf("  %-10s %d%n", product.name(), inventory.available(product.sku()));
        }
    }

    private static void printOrder(Order order) {
        System.out.println("Order #" + order.number());
        for (CartItem item : order.items()) {
            System.out.printf("  %-10s x%d %10.2f%n",
                    item.product().name(), item.quantity(), item.subtotal());
        }
        System.out.printf("  Subtotal:  %.2f%n  Discount: -%.2f%n  Total:     %.2f%n%n",
                order.subtotal(), order.discount(), order.total());
    }
}
