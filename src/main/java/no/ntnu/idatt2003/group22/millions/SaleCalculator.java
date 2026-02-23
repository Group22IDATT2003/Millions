package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

public class SaleCalculator implements TransactionCalculator{
    private static final BigDecimal commission_Rate = BigDecimal.valueOf(0.01);
    private static final BigDecimal tax_Rate = BigDecimal.valueOf(0.30);
    
    private final BigDecimal purchasePrice;
    private final BigDecimal salePrice;
    private final BigDecimal quantity;

    public SaleCalculator(Share share) {
        Objects.requireNonNull(share, "share can not be null");
        purchasePrice = share.getPurchasePrice();
        salePrice = share.getStock().getSalesPrice();
        quantity = share.getQuantity();
    }

    @Override
    public BigDecimal calculateGross() {
        return salePrice.multiply(quantity);
    }

    @Override
    public BigDecimal calculateCommission() {
        return calculateGross()
        .multiply(commission_Rate)
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

        return profit.multiply(tax_Rate)
        .setScale(2);
    }

    public BigDecimal calculateNetAmount() {
        return calculateGross()
        .subtract(calculateCommission())
        .subtract(calculateTax())
        .setScale(2);
    }
}
