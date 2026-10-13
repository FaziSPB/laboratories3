package pl.pwr.tp.shop;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Gives a percentage discount when the total reaches a threshold.
 */
public class ThresholdDiscount implements DiscountPolicy {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private final BigDecimal threshold;
    private final BigDecimal percent;

    /**
     * Creates the policy.
     *
     * @param threshold minimal total that qualifies for the discount
     * @param percent   discount in percent, from 0 to 100
     */
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
