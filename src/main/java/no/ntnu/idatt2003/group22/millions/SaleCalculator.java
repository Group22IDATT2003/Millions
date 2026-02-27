package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a sale transaction where a player sells a share.
 * A sale adds the share to the player's portfolio,
 * calculates the total cost of the transaction,
 * and deducts the total cost from the player's balance.
 * An exception is thrown if the player does not own the share being sold
 * or if the transaction is committed more than once.
 */
public class SaleCalculator implements TransactionCalculator{
    private static final BigDecimal COMMISION_RATE = BigDecimal.valueOf(0.01);
    private static final BigDecimal TAX_RATE = BigDecimal.valueOf(0.30);
    
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
        Objects.requireNonNull(share, "share can not be null");
        this.purchasePrice = share.getPurchasePrice();
        this.salePrice = share.getStock().getSalesPrice();
        this.quantity = share.getQuantity();
    }

    @Override
    public BigDecimal calculateGross() {
        return salePrice.multiply(quantity);
    }

    @Override
    public BigDecimal calculateCommission() {
        return calculateGross()
        .multiply(COMMISION_RATE)
        .setScale(2);
    }

    @Override
    public BigDecimal calculateTax() {
        BigDecimal purchaseCost = purchasePrice.multiply(quantity);

        BigDecimal profit = calculateGross()
        .subtract(calculateCommission())
        .subtract(purchaseCost);

        if (profit.compareTo(BigDecimal.ZERO) <= 0){
            return BigDecimal.ZERO.setScale(2);
        }

        return profit
        .multiply(TAX_RATE)
        .setScale(2);
    }
    
    @Override
    public BigDecimal calculateTotal() {
        return calculateGross()
        .subtract(calculateCommission())
        .subtract(calculateTax())
        .setScale(2);
    }
}
