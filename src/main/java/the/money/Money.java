package the.money;

import the.ddd.NumericValueObject;

import java.math.BigDecimal;
import java.util.Objects;

public class Money implements NumericValueObject<Money> {

    private BigDecimal value;

    private Money(BigDecimal value) {
        Objects.requireNonNull(value, "Money value cannot be null!");
        this.value = value;
    }

    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Money multiply(BigDecimal multiplicand) {
        return Money.of(value.multiply(multiplicand));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var rate = (Money) o;
        return value.compareTo(rate.value)==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                '}';
    }
}
