package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;

/**
 * Represents a sale transaction where a player sells a share.
 * A sale adds the share to the player's portfolio,
 * calculates the total cost of the transaction,
 * and deducts the total cost from the player's balance.
 * An exception is thrown if the player does not own the share being sold
 * or if the transaction is committed more than once.
 */
public class SaleCalculator implements TransactionCalculator{
    private final BigDecimal purchasePrice;
    private final BigDecimal salePrice;
    private final BigDecimal quantity;

    /**
     * Constructor for SaleCalculator.
     * Initializes the calculator with the purchase price,
     * sale price, and quantity of the share being sold.
     *
     * @param share the share being sold. Must not be null.
     */
    public SaleCalculator(Share share) {
        purchasePrice = share.getPurchasePrice();
        salePrice = share.getStock().getSalesPrice();
        quantity = share.getQuantity();
    }

    /**
     * Calculates the gross amount of the sale by multiplying the sale price by the quantity.
     * This represents the total revenue generated from the sale before any commissions or taxes are applied.
     *
     * @return the gross amount of the sale as a BigDecimal.
     */
    public BigDecimal calculateGross() {
        return salePrice.multiply(quantity);
    }

    /**
     * Calculates the commission for the sale.
     * In this implementation, the commission is set to zero,
     * but it can be modified to include any applicable fees or commissions based on the sale.
     *
     * @return the commission for the sale as a BigDecimal. 0 by default.
     */
    public BigDecimal calculateCommission() {
        return BigDecimal.ZERO;
    }

    /**
     * Calculates the tax for the sale.
     * In this implementation, the tax is set to zero,
     * but it can be modified to include any applicable taxes based on the sale.
     *
     * @return the tax for the sale as a BigDecimal. 0 by default.
     */
    public BigDecimal calculateTax() {
        return BigDecimal.ZERO;
    }

    /**
     * Calculates the total amount of the sale by summing the gross amount, commission, and tax.
     * This represents the final amount that will be added to the player's balance
     * after the sale is completed.
     *
     * @return the total amount of the sale as a BigDecimal.
     */
    public BigDecimal calculateTotal() {
        return calculateGross()
                .add(calculateCommission())
                .add(calculateTax());
    }
}
