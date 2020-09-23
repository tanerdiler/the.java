package the.money;

import the.ddd.NumericValueObject;

import java.math.BigDecimal;
import java.util.Objects;

public class Price implements NumericValueObject<Price> {

    private BigDecimal value;

    private Price(BigDecimal value) {
        Objects.requireNonNull(value, "Price value cannot be null!");
        this.value = value;
    }

    public static Price of(BigDecimal value) {
        return new Price(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Price multiply(BigDecimal multiplicand) {
        return Price.of(value.multiply(multiplicand));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return value.compareTo(price.value)==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                '}';
    }
}
