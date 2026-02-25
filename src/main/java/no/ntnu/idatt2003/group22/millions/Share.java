package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

public class Share {
    private final Stock stock;
    private final BigDecimal quantity;
    private final BigDecimal purchasePrice;

    public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
        this.stock = Objects.requireNonNull(stock, "stock cannot be null");
        this.quantity = Objects.requireNonNull(quantity, "quantity cannot be null");
        this.purchasePrice = Objects.requireNonNull(purchasePrice, "purchase price cannot be null");

        if(quantity.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("quantity must be < 0");
        }
        if(purchasePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("price of the purchase must be < 0");
        }
    }

    public Stock getStock() {
        return stock;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

}
