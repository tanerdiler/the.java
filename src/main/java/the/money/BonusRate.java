package the.money;

import the.ddd.NumericValueObject;

import java.math.BigDecimal;
import java.util.Objects;

public class BonusRate implements NumericValueObject<BonusRate> {

    private BigDecimal value;

    private BonusRate(BigDecimal value) {
        Objects.requireNonNull(value, "BonusRate value cannot be null!");
        this.value = value;
    }

    public static BonusRate of(BigDecimal value) {
        return new BonusRate(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public <T extends NumericValueObject<T>> T applyTo(T valueObject) {
        return valueObject.multiply(value.divide(PERCENTAGE));
    }

    @Override
    public BonusRate multiply(BigDecimal multiplicand) {
        return BonusRate.of(value.multiply(multiplicand));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var rate = (BonusRate) o;
        return value.compareTo(rate.value)==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "BonusRate{" +
                "value=" + value +
                '}';
    }
}
