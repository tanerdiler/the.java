package the.money;

import the.ddd.DataValueObject;

import java.util.Objects;

public class Count implements DataValueObject {

    private Long value;

    private Count(Long value) {
        Objects.requireNonNull(value, "Count value cannot be null!");
        this.value = value;
    }

    public static Count of(Long value) {
        return new Count(value);
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var rate = (Count) o;
        return value.compareTo(rate.value)==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Count{" +
                "value=" + value +
                '}';
    }
}
