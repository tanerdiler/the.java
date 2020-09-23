package the.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BonusRateTest {

    @Test
    void should_return_0_if_rate_is_0() {
        var price = Price.of(new BigDecimal("400.0"));
        var rate = BonusRate.of(new BigDecimal("0"));
        var result = rate.applyTo(price);

        assertEquals(Price.of(new BigDecimal("0.0")), result, "should return 0 if rate is 0");
    }

    @Test
    void should_apply_to_price_then_return_price() {
        var price = Price.of(new BigDecimal("400.0"));
        var rate = BonusRate.of(new BigDecimal("5"));
        var result = rate.applyTo(price);

        assertEquals(Price.of(new BigDecimal("20.0")), result);
    }

    @Test
    void should_equal_to_same_value_with_diff_scale() {
        var rate1 = BonusRate.of(new BigDecimal("5.0"));
        var rate2 = BonusRate.of(new BigDecimal("5.000"));
        assertEquals(rate1, rate2,  "Price should equal to diff instance with same value");
    }

    @Test
    void should_equal_to_diff_instance_with_same_value() {
        var rate1 = BonusRate.of(new BigDecimal("5"));
        var rate2 = BonusRate.of(new BigDecimal("5"));
        assertEquals(rate1, rate2, "BonusRate should equal to diff instance with same value");
    }

    @Test
    void should_not_equal_to_diff_instance_with_same_value() {
        var rate1 = BonusRate.of(new BigDecimal("5"));
        var rate2 = BonusRate.of(new BigDecimal("15"));
        assertNotEquals(rate1, rate2, "BonusRate should not equal to diff instance with diff value");
    }

    @Test
    void should_generate_same_hash_code_with_instance_of_same_value() {
        var rate1 = BonusRate.of(new BigDecimal("5"));
        var rate2 = BonusRate.of(new BigDecimal("5"));
        assertEquals(rate1.hashCode(), rate2.hashCode(), "BonusRate should generate same hash code");
    }

    @Test
    void should_generate_diff_hash_code_with_instance_of_diff_value() {
        var rate1 = BonusRate.of(new BigDecimal("5"));
        var rate2 = BonusRate.of(new BigDecimal("15"));
        assertNotEquals(rate1.hashCode(), rate2.hashCode(), "BonusRate should not sgenerate same hash code");
    }

    @Test
    void should_return_value_without_modification() {
        var rate = BonusRate.of(new BigDecimal("5"));
        assertEquals(rate.getValue(), new BigDecimal("5"), "BonusRate should return value without modification");
    }
}