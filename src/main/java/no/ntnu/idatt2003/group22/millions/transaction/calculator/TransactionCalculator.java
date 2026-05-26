package no.ntnu.idatt2003.group22.millions.transaction.calculator;

import java.math.BigDecimal;

/**
 * Defines calculations for financial transactions
 */
public interface TransactionCalculator {

    /**
     * Returns the gross transaction value
     * 
     * @return the gross value
     */
    BigDecimal calculateGross();

    /**
     * Returns the transaction commission
     * 
     * @return the commission value
     */
    BigDecimal calculateCommission();

    /**
     * Returns the transaction tax
     * 
     * @return the tax amount
     */
    BigDecimal calculateTax();

    /**
     * Returns the total transaction value
     * 
     * @return the total value
     */
    BigDecimal calculateTotal();
}
