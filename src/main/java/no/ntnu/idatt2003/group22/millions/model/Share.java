package no.ntnu.idatt2003.group22.millions.model;

import java.math.BigDecimal;

/**
 * Represents a purchased share of a stock
 */

public class Share {
    private final Stock stock;
    private final BigDecimal quantity;
    private final BigDecimal purchasePrice;

    /**
     * Creates a share with stock, quantity, and purchase price
     * 
     * @param stock the stock
     * @param quantity the amount of shares
     * @param purchasePrice the purchase price per share
     * @throws IllegalArgumentException if values are invalid
     */
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

    /**
     * Returns the current market value of the share
     * 
     * @return the current value
     */
    public BigDecimal getCurrentValue(){
        return stock.getSalesPrice().multiply(quantity);
    }

    /**
     * Returns the percentage price change since purchase
     * 
     * @return the percentage price change
     */
    public BigDecimal getPriceChangePercentage(){
        BigDecimal currentValue = getCurrentValue();
        BigDecimal purchaseValue = purchasePrice.multiply(quantity);
        return currentValue.subtract(purchaseValue)
                .divide(purchaseValue, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Returns the quantity of shares
     * 
     * @return the quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Returns the purchase price per share
     * 
     * @return the purchase price
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Returns the stock symbol
     * 
     * @return the stock symbol
     */
    public String getSymbol(){
        return stock.getSymbol();
    }

    /**
     * Returns the current market value
     * 
     * @return the market value
     */
    public BigDecimal getMarketValue(){
        return stock.getSalesPrice().multiply(quantity);
    }

    /**
     * Compares this share with another object
     * 
     * @param o the object to compare with
     * @return true if the shares are equal
     */
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Share other)) return false;

        return stock.equals(other.stock)
        && quantity.compareTo(other.quantity) == 0
        && purchasePrice.compareTo(other.purchasePrice) == 0;
    }

    /**
     * Returns the hash code for the share
     * 
     * @return the hash code
     */
    @Override
    public int hashCode(){
        int result = stock.hashCode();
        result = 31 * result + quantity.stripTrailingZeros().hashCode();
        result = 31 * result + purchasePrice.stripTrailingZeros().hashCode();
        return result;
    }

    /**
     * Returns a string representation of the share
     * 
     * @return the share as a string
     */
    @Override
    public String toString(){
        return "Share{" +
        "symbol=' " + stock.getSymbol() + '\'' +
        ", quantity= " + quantity +
        ", purchasePrice =" + purchasePrice +
        '}';
    }

}
