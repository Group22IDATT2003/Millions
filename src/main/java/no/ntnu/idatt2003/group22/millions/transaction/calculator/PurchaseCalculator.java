package no.ntnu.idatt2003.group22.millions.transaction.calculator;

import no.ntnu.idatt2003.group22.millions.model.Share;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Calculates values for purchase transactions
 */
public final class PurchaseCalculator implements TransactionCalculator {
    private static final BigDecimal COMMISION_RATE = BigDecimal.valueOf(0.005);

    private final  BigDecimal purchasePrice;
    private final BigDecimal quantity;

    /**
     * Creates a purchase calculator fr a share
     *
     * @param share the purchased share
     * @throws NullPointerException if share is null
     */
    public PurchaseCalculator(Share share) {
        Objects.requireNonNull(share, "share can not be null");
        this.purchasePrice = share.getPurchasePrice();
        this.quantity = share.getQuantity();
    }


    /**
     * Returns the gross purchase value
     * 
     * @return the gross value
     */
    @Override
    public BigDecimal calculateGross() {

        return purchasePrice.multiply(quantity)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Returns the purchase commission
     * 
     * @return the commission
     */
    @Override
    public BigDecimal calculateCommission() {
        return calculateGross()
                .multiply(COMMISION_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Returns the purchase tax
     * 
     * @return the tax value
     */
    @Override
    public BigDecimal calculateTax(){
        return BigDecimal.ZERO.setScale(2);
    }

    /**
     * Returns the total purchase cost
     * 
     * @retun the total cost
     */
    @Override
    public BigDecimal calculateTotal() {
        return calculateGross()
                .add(calculateCommission())
                .add(calculateTax())
                .setScale(2, RoundingMode.HALF_UP);
    }
}
