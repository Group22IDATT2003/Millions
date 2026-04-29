package no.ntnu.idatt2003.group22.millions.transaction.calculator;

import no.ntnu.idatt2003.group22.millions.model.Share;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a sale transaction where a player sells a share.
 * A sale adds the share to the player's portfolio,
 * calculates the total cost of the transaction,
 * and deducts the total cost from the player's balance.
 * An exception is thrown if the player does not own the share being sold
 * or if the transaction is committed more than once.
 */
public class SaleCalculator implements TransactionCalculator {
    private static final BigDecimal COMMISION_RATE = BigDecimal.valueOf(0.01);
    private static final BigDecimal TAX_RATE = new BigDecimal("0.22");
    
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
        if(share == null){
            throw new IllegalArgumentException("share cannot be null");
        }
        this.purchasePrice = share.getPurchasePrice();
        this.salePrice = share.getStock().getSalesPrice();
        this.quantity = share.getQuantity();
    }

    @Override
    public BigDecimal calculateGross() {
        return salePrice.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateCommission() {
        return calculateGross()
        .multiply(COMMISION_RATE)
        .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateProfitBeforeTax(){
        BigDecimal purchaseCost = purchasePrice.multiply(quantity);
        return calculateGross()
        .subtract(calculateCommission())
        .subtract(purchaseCost)
        .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateTax() {
        BigDecimal profit = calculateProfitBeforeTax();

        if (profit.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return profit.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateTotal() {
        return calculateGross()
        .subtract(calculateCommission())
        .subtract(calculateTax())
        .setScale(2, RoundingMode.HALF_UP);
    }
}
