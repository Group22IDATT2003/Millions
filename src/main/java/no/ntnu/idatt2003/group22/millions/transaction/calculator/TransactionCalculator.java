package no.ntnu.idatt2003.group22.millions.transaction.calculator;

import java.math.BigDecimal;

/**
 * Interface for calculating the financial aspects of a transaction, including gross amount,
 * commission, tax, and total cost.
 * This interface is implemented by specific transaction calculators,
 * such as PurchaseCalculator and SaleCalculator,
 * which provide the logic for calculating these values based on the details of the transaction.
 */
public interface TransactionCalculator {

    BigDecimal calculateGross();

    BigDecimal calculateCommission();

    BigDecimal calculateTax();

    BigDecimal calculateTotal();
}
