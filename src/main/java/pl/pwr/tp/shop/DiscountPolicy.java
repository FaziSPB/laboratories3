package pl.pwr.tp.shop;

import java.math.BigDecimal;

/**
 * Strategy that computes the discount for a cart total.
 */
@FunctionalInterface
public interface DiscountPolicy {

    /** Policy that never gives a discount. */
    DiscountPolicy NONE = total -> BigDecimal.ZERO;

    /**
     * Computes the discount amount.
     *
     * @param total cart total before discount
     * @return amount to subtract, between 0 and {@code total}
     */
    BigDecimal discountFor(BigDecimal total);
}
