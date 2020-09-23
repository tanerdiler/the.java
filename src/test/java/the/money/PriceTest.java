package the.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void should_multiply_immutably() {
        var price1 = Price.of(new BigDecimal("200.0"));
        var result = price1.multiply(BigDecimal.valueOf(5));
        assertEquals(result, Price.of(new BigDecimal("1000.0")) , "Money should be to multiplied immutably");
    }

    @Test
    void should_equal_to_same_value_with_diff_scale() {
        var price1 = Price.of(new BigDecimal("200.00"));
        var price2 = Price.of(new BigDecimal("200.000"));
        assertEquals(price1, price2, "Price should equal to diff instance with same value");
    }

    @Test
    void should_equal_to_diff_instance_with_same_value() {
        var price1 = Price.of(new BigDecimal("200.01"));
        var price2 = Price.of(new BigDecimal("200.01"));
        assertEquals(price1, price2, "Price should equal to diff instance with same value");
    }

    @Test
    void should_not_equal_to_diff_instance_with_same_value() {
        var price1 = Price.of(new BigDecimal("200.01"));
        var price2 = Price.of(new BigDecimal("200.02"));
        assertNotEquals(price1, price2, "Price should not equal to diff instance with diff value");
    }

    @Test
    void should_generate_same_hash_code_with_instance_of_same_value() {
        var price1 = Price.of(new BigDecimal("200.01"));
        var price2 = Price.of(new BigDecimal("200.01"));
        assertEquals(price1.hashCode(), price2.hashCode(), "Price should generate same hash code");
    }

    @Test
    void should_generate_diff_hash_code_with_instance_of_diff_value() {
        var price1 = Price.of(new BigDecimal("200.01"));
        var price2 = Price.of(new BigDecimal("200.02"));
        assertNotEquals(price1.hashCode(), price2.hashCode(), "Price should not sgenerate same hash code");
    }

    @Test
    void should_return_value_without_modification() {
        var price = Price.of(new BigDecimal("200.01"));
        assertEquals(price.getValue(), new BigDecimal("200.01"), "Price should return value without modification");
    }
}