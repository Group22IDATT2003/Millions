package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

public final class PurchaseCalculator implements TransactionCalculator{
    private final  BigDecimal purchasePrice;
    private final BigDecimal quantity;

    public PurchaseCalculator(Share share){
        Objects.requireNonNull(share, "share can not be null");
        this.purchasePrice = share.getPurchasePrice();
        this.quantity = share.getQuantity();
    }

    @Override
    public BigDecimal calculateGross(){
        return purchasePrice.multiply(quantity);
    }

    @Override
    public BigDecimal calculateCommission(){
        return calculateGross()
        .multiply(BigDecimal.valueOf(0.005))
        .setScale(2);
    }

    @Override
    public BigDecimal calculateTax(){
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateTotal(){
        return calculateGross()
                .add(calculateCommission())
                .add(calculateTax());
    }
}
