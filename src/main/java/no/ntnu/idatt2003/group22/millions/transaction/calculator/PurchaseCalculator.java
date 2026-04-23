package no.ntnu.idatt2003.group22.millions.transaction.calculator;

import no.ntnu.idatt2003.group22.millions.model.Share;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a calculator for purchase transactions.
 * This class implements the TransactionCalculator interface and provides
 * methods to calculate the gross amount, commission, tax, and total cost
 * of a purchase transaction based on the purchase price and quantity of the share.
 */
public final class PurchaseCalculator implements TransactionCalculator {
    private static final BigDecimal COMMISION_RATE = BigDecimal.valueOf(0.005);

    private final  BigDecimal purchasePrice;
    private final BigDecimal quantity;

    /**
     * Constructor for PurchaseCalculator.
     * Initializes the calculator with the purchase price and quantity of the share being purchased.
     *
     * @param share the share being purchased. Must not be null.
     */
    public PurchaseCalculator(Share share) {
        Objects.requireNonNull(share, "share can not be null");
        this.purchasePrice = share.getPurchasePrice();
        this.quantity = share.getQuantity();
    }


    @Override
    public BigDecimal calculateGross() {
        return purchasePrice.multiply(quantity);
    }

    @Override
    public BigDecimal calculateCommission() {
        return calculateGross()
                .multiply(BigDecimal.valueOf(0.005))
                .setScale(3);
    }


    @Override
    public BigDecimal calculateTax(){
        return BigDecimal.ZERO.setScale(2);
    }

    @Override
    public BigDecimal calculateTotal() {
        return calculateGross()
                .add(calculateCommission())
                .add(calculateTax())
                .stripTrailingZeros();
    }
}
