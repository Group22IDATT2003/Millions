package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;

public class SaleCalculator {
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private BigDecimal quantity;

    public SaleCalculator(Share share) {
        purchasePrice = share.getPurchasePrice();
        salePrice = share.getStock().getSalesPrice();
        quantity = share.getQuantity();
    }

    public BigDecimal calculateGross() {
        return salePrice.multiply(quantity);
    }

    public BigDecimal calculateCommission() {
        return BigDecimal.ZERO;
    }

    public BigDecimal calculateTax() {
        return BigDecimal.ZERO;
    }

    public BigDecimal calculateTotal() {
        return calculateGross().add(calculateCommission()).add(calculateTax());
    }
}
