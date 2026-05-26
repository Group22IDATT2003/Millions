package no.ntnu.idatt2003.group22.millions.transaction.calculator;

import no.ntnu.idatt2003.group22.millions.model.Share;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates values for sale transactions
 */
public class SaleCalculator implements TransactionCalculator {
    private static final BigDecimal COMMISION_RATE = new BigDecimal("0.01");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.30");
    
    private final BigDecimal purchasePrice;
    private final BigDecimal salePrice;
    private final BigDecimal quantity;

    /**
     * Creates a sale calculator for a share
     *
     * @param share the sold share
     * @throws IllegalArgumentException is share is null
     */
    public SaleCalculator(Share share) {
        if(share == null){
            throw new IllegalArgumentException("share cannot be null");
        }
        this.purchasePrice = share.getPurchasePrice();
        this.salePrice = share.getStock().getSalesPrice();
        this.quantity = share.getQuantity();
    }

    /**
     * Returns the gross sale value
     * 
     * @return the gross value
     */
    @Override
    public BigDecimal calculateGross() {
        return salePrice.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Returns the sale commission
     * 
     * @return the sale commission
     */
    @Override
    public BigDecimal calculateCommission() {
        return calculateGross()
        .multiply(COMMISION_RATE)
        .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates profit before tax
     * 
     * @return the profit before tax
     */
    private BigDecimal calculateProfitBeforeTax(){
        BigDecimal purchaseCost = purchasePrice.multiply(quantity);
        return calculateGross()
        .subtract(calculateCommission())
        .subtract(purchaseCost)
        .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Returns the tax on the sale profit
     * @return the tax amount
     */
    @Override
    public BigDecimal calculateTax() {
        BigDecimal profit = calculateProfitBeforeTax();

        if (profit.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return profit.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Returns the total sale payout
     * 
     * @return the total payout
     */
    @Override
    public BigDecimal calculateTotal() {
        return calculateGross()
        .subtract(calculateCommission())
        .subtract(calculateTax())
        .setScale(2, RoundingMode.HALF_UP);
    }
}
