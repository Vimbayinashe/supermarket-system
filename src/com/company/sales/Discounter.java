package com.company.sales;

import java.math.BigDecimal;

public interface Discounter {
    BigDecimal applyDiscount(BigDecimal amount);

    static Discounter largePurchaseDiscounter() {
        return amount -> amount.multiply(BigDecimal.valueOf(0.9));
    }

}
