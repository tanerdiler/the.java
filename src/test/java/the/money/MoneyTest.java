package the.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MoneyTest {

    @Test
    void should_multiply_immutably() {
        var money1 = Money.of(new BigDecimal("200.0"));
        var result = money1.multiply(BigDecimal.valueOf(5));
        assertEquals(result, Money.of(new BigDecimal("1000.0")) , "Money should be to multiplied immutably");
    }

    @Test
    void should_equal_to_same_value_with_diff_scale() {
        var money1 = Money.of(new BigDecimal("200.0"));
        var money2 = Money.of(new BigDecimal("200.000"));
        assertEquals(money1, money2, "Money should equal to same value with diff scale");
    }

    @Test
    void should_equal_to_diff_instance_with_same_value() {
        var money1 = Money.of(new BigDecimal("200.01"));
        var money2 = Money.of(new BigDecimal("200.01"));
        assertEquals(money1, money2, "Money should equal to diff instance with same value");
    }

    @Test
    void should_not_equal_to_diff_instance_with_same_value() {
        var money1 = Money.of(new BigDecimal("200.01"));
        var money2 = Money.of(new BigDecimal("200.02"));
        assertNotEquals(money1, money2, "Money should not equal to diff instance with diff value");
    }

    @Test
    void should_generate_same_hash_code_with_instance_of_same_value() {
        var money1 = Money.of(new BigDecimal("200.01"));
        var money2 = Money.of(new BigDecimal("200.01"));
        assertEquals(money1.hashCode(), money2.hashCode(), "Money should generate same hash code");
    }

    @Test
    void should_generate_diff_hash_code_with_instance_of_diff_value() {
        var money1 = Money.of(new BigDecimal("200.01"));
        var money2 = Money.of(new BigDecimal("200.02"));
        assertNotEquals(money1.hashCode(), money2.hashCode(), "Money should not sgenerate same hash code");
    }

    @Test
    void should_return_value_without_modification() {
        var money = Money.of(new BigDecimal("200.01"));
        assertEquals(money.getValue(), new BigDecimal("200.01"), "Money should return value without modification");
    }
}