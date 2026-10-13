package pl.pwr.tp.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopTest {

    private final Product pen = new Product("PEN", "Pen", new BigDecimal("2.50"));
    private final Product book = new Product("BOOK", "Book", new BigDecimal("40.00"));

    private Inventory inventory;
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        inventory.addStock(pen, 10);
        inventory.addStock(book, 2);
        cart = new ShoppingCart();
    }

    @Test
    void cartMergesSameProduct() {
        cart.add(pen, 2);
        cart.add(pen, 3);

        assertEquals(1, cart.items().size());
        assertEquals(5, cart.items().getFirst().quantity());
        assertEquals(new BigDecimal("12.50"), cart.total());
    }

    @Test
    void checkoutTakesStockAndEmptiesCart() {
        Shop shop = new Shop(inventory, DiscountPolicy.NONE);
        cart.add(pen, 4);
        cart.add(book, 1);

        Order order = shop.checkout(cart);

        assertEquals(new BigDecimal("50.00"), order.total());
        assertEquals(6, inventory.available("PEN"));
        assertEquals(1, inventory.available("BOOK"));
        assertTrue(cart.isEmpty());
        assertEquals(1, shop.orders().size());
    }

    @Test
    void discountAppliedAboveThreshold() {
        DiscountPolicy policy = new ThresholdDiscount(new BigDecimal("50"), BigDecimal.TEN);
        Shop shop = new Shop(inventory, policy);
        cart.add(book, 2);

        Order order = shop.checkout(cart);

        assertEquals(new BigDecimal("8.00"), order.discount());
        assertEquals(new BigDecimal("72.00"), order.total());
    }

    @Test
    void noDiscountBelowThreshold() {
        DiscountPolicy policy = new ThresholdDiscount(new BigDecimal("50"), BigDecimal.TEN);

        assertEquals(BigDecimal.ZERO, policy.discountFor(new BigDecimal("49.99")));
    }

    @Test
    void missingStockRejectsWholeOrder() {
        Shop shop = new Shop(inventory, DiscountPolicy.NONE);
        cart.add(pen, 1);
        cart.add(book, 3);

        assertThrows(OutOfStockException.class, () -> shop.checkout(cart));
        assertEquals(10, inventory.available("PEN"));
        assertEquals(2, inventory.available("BOOK"));
        assertTrue(shop.orders().isEmpty());
    }

    @Test
    void emptyCartCannotBeCheckedOut() {
        Shop shop = new Shop(inventory, DiscountPolicy.NONE);

        assertThrows(IllegalStateException.class, () -> shop.checkout(cart));
    }
}
