package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;

public interface TransactionCalculator {

    BigDecimal calculateGross();

    BigDecimal calculateCommission();

    BigDecimal calculateTax();

    BigDecimal calculateNetAmount();
}
