package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ThresholdDiscount implements DiscountPolicy {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private final BigDecimal threshold;
    private final BigDecimal percent;

    public ThresholdDiscount(BigDecimal threshold, BigDecimal percent) {
        if (percent.signum() < 0 || percent.compareTo(HUNDRED) > 0) {
            throw new IllegalArgumentException("Percent must be in 0..100: " + percent);
        }
        this.threshold = threshold;
        this.percent = percent;
    }

    @Override
    public BigDecimal discountFor(BigDecimal total) {
        if (total.compareTo(threshold) < 0) {
            return BigDecimal.ZERO;
        }
        return total.multiply(percent).divide(HUNDRED, 2, RoundingMode.HALF_UP);
    }
}
