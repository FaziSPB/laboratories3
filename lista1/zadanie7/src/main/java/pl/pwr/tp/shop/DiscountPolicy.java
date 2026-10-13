package pl.pwr.tp.shop;

import java.math.BigDecimal;

@FunctionalInterface
public interface DiscountPolicy {

    DiscountPolicy NONE = total -> BigDecimal.ZERO;

    BigDecimal discountFor(BigDecimal total);
}
