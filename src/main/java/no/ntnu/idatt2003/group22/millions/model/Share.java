package no.ntnu.idatt2003.group22.millions.model;

import java.math.BigDecimal;

public class Share {
    private final Stock stock;
    private final BigDecimal quantity;
    private final BigDecimal purchasePrice;

    public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
        if(stock == null){
            throw new IllegalArgumentException("stock cannot be null");
        }
        
        if(quantity == null){
            throw new IllegalArgumentException("quantity cannot be null");
        }

        if(purchasePrice == null){
            throw new IllegalArgumentException("purchasePrice cannot be null");
        }

        if(quantity.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("quantity must be > 0");
        }
        if(purchasePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("price of the purchase must be > 0");
        }

        this.stock = stock;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    public Stock getStock() {
        return stock;
    }

    public BigDecimal getCurrentValue(){
        return stock.getSalesPrice().multiply(quantity);
    }

    public BigDecimal getPriceChangePercentage(){
        BigDecimal currentValue = getCurrentValue();
        BigDecimal purchaseValue = purchasePrice.multiply(quantity);
        return currentValue.subtract(purchaseValue)
                .divide(purchaseValue, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
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
        int result = stock.hashCode();
        result = 31 * result + quantity.stripTrailingZeros().hashCode();
        result = 31 * result + purchasePrice.stripTrailingZeros().hashCode();
        return result;
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
