package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;

/**
 * Represents a calculator for purchase transactions.
 * This class implements the TransactionCalculator interface and provides
 * methods to calculate the gross amount, commission, tax, and total cost
 * of a purchase transaction based on the purchase price and quantity of the share.
 */
public final class PurchaseCalculator implements TransactionCalculator{
    private final  BigDecimal purchasePrice;
    private final BigDecimal quantity;

    /**
     * Constructor for PurchaseCalculator.
     * Initializes the calculator with the purchase price and quantity of the share being purchased.
     *
     * @param share the share being purchased. Must not be null.
     */
    public PurchaseCalculator(Share share){
        purchasePrice = share.getPurchasePrice();
        quantity = share.getQuantity();
    }

    /**
     * Calculates the gross amount of the purchase by multiplying the purchase price by the quantity.
     * This represents the total cost of the purchase before any commissions or taxes are applied.
     *
     * @return the gross amount of the purchase as a BigDecimal.
     */
    public BigDecimal calculateGross(){
        return purchasePrice.multiply(quantity);
    }

    /**
     * Calculates the commission for the purchase.
     * In this implementation, the commission is set to zero,
     * but it can be modified to include any applicable fees or commissions based on the purchase.
     *
     * @return the commission for the purchase as a BigDecimal. 0 by default.
     */
    public BigDecimal calculateCommission(){
        return BigDecimal.ZERO;
    }

    /**
     * Calculates the tax for the purchase.
     * In this implementation, the tax is set to zero,
     * but it can be modified to include any applicable taxes based on the purchase.
     *
     * @return the tax for the purchase as a BigDecimal. 0 by default.
     */
    public BigDecimal calculateTax(){
        return BigDecimal.ZERO;
    }

    /**
     * Calculates the total cost of the purchase by summing the gross amount, commission, and tax.
     *
     * @return the total cost of the purchase as a BigDecimal.
     */
    public BigDecimal calculateTotal(){
        return calculateGross()
                .add(calculateCommission())
                .add(calculateTax());
    }
}
