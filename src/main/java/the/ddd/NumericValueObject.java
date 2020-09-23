package the.ddd;

import java.math.BigDecimal;

public interface NumericValueObject<T> extends DataValueObject {
    BigDecimal PERCENTAGE = BigDecimal.valueOf(100d);

    T multiply(BigDecimal multiplicand);
}
