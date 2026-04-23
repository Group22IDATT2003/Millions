package no.ntnu.idatt2003.group22.millions.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Share {
    private final Stock stock;
    private final BigDecimal quantity;
    private final BigDecimal purchasePrice;

    public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
        if(stock == null){
            throw new IllegalArgumentException("stock cannot be null");
        }
        this.stock = stock;
        
        if(quantity == null){
            throw new IllegalArgumentException("quantity cannot be null");
        }
        this.quantity = quantity;

        if(purchasePrice == null){
            throw new IllegalArgumentException("purchasePrice cannot be null");
        }
        this.purchasePrice = purchasePrice;

        if(quantity.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("quantity must be > 0");
        }
        if(purchasePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("price of the purchase must be > 0");
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

    public String getSymbol(){
        return stock.getSymbol();
    }

    public BigDecimal getMarketValue(){
        return stock.getSalesPrice().multiply(quantity);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Share other)) return false;
        return stock.equals(other.stock)
        && quantity.compareTo(other.quantity) == 0
        && purchasePrice.compareTo(other.purchasePrice) == 0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(
            stock,
            quantity.stripTrailingZeros(),
            purchasePrice.stripTrailingZeros()
        );
    }

    @Override
    public String toString(){
        return "Share{" +
        "symbol=' " + stock.getSymbol() + '\'' +
        ", quantity= " + quantity +
        ", purchasePrice =" + purchasePrice +
        '}';
    }

}
