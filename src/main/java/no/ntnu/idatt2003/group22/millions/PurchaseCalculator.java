package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;

public class PurchaseCalculator implements TransactionCalculator{
    private BigDecimal purchasePrice;
    private BigDecimal quantity;

    public PurchaseCalculator(Share share){
        purchasePrice = share.getPurchasePrice();
        quantity = share.getQuantity();
    }

    public BigDecimal calculateGross(){
        return purchasePrice.multiply(quantity);
    }

    public BigDecimal calculateCommission(){
        return BigDecimal.ZERO;
    }

    public BigDecimal calculateTax(){
        return BigDecimal.ZERO;
    }

    public BigDecimal calculateTotal(){
        return calculateGross().add(calculateCommission()).add(calculateTax());
    }
}
