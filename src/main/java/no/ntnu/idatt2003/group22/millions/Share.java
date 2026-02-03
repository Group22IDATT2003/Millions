package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;

public class Share {
    private Stock Stock;
    private BigDecimal quantity;
    private BigDecimal purchasePrice;

    public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
        this.Stock = stock;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    public Stock getStock() {
        return Stock;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

}
