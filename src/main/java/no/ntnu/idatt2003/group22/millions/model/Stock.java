package no.ntnu.idatt2003.group22.millions.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a stock in the financial market.
 */
public class Stock {
    private final String symbol;
    private final String company;
    private final List<BigDecimal> prices;

    /**
     * Creates a stock with symbol, company name and start price.
     * 
     * @param symbol the stock symbol
     * @param company the cmpany name
     * @param salesPrice the starting sales price
     * @throws IllegalArgumentException if values are invalid
     */
    public Stock(String symbol, String company, BigDecimal salesPrice) {
        if (symbol == null) {
            throw new IllegalArgumentException("symbol can not be null");
        }
        if(symbol.isBlank()){
            throw new IllegalArgumentException("symbol cannot be blank");
        }
        if (company == null) {
            throw new IllegalArgumentException("company can not be null");
        }
        if(company.isBlank()){
            throw new IllegalArgumentException("company cannot be blank");
        }
        if (salesPrice == null) {
            throw new IllegalArgumentException("salesPrice can not be null");
        }
        if (salesPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("salesPrice must be > 0");
        }

        this.symbol = symbol;
        this.company = company;
        this.prices = new ArrayList<>();
        this.prices.add(salesPrice);
    }

    /**
     * Returns the stock symbol
     * 
     * @return the stock symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the company name
     * 
     * @return the company name.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Returns the latest sales price
     * 
     * @return the latest sales price.
     */
    public BigDecimal getSalesPrice() {
        return prices.get(prices.size() - 1);
    }

    /**
     * Adds a new sales price to the history
     * 
     * @param price the new sales price
     * @throws IllegalArgumentException if the price is NULL or not greater than zero.
     */
    public void addNewSalesPrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price must be > 0");
        }
        prices.add(price);
    }

    /**
     * Returns all historical prices.
     * 
     * @return a immutable list og prices
     */
    public List<BigDecimal> getHistoricalPrices(){
        return List.copyOf(prices);
    }

    /**
     * Returns the highest registered price
     * 
     * @return the highest price
     */
    public BigDecimal getHighestPrice(){
        return prices.stream()
                .max(BigDecimal::compareTo)
                .orElseThrow(() -> new IllegalStateException("prices list is empty"));
    }

    /**
     * Returns the lowest registered price
     * 
     * @return the lowest price
     */
    public BigDecimal getLowestPrice(){
        return prices.stream()
        .min(BigDecimal::compareTo)
        .orElseThrow(() -> new IllegalStateException("prices list is empty"));
    }

    /**
     * Returns the price change from the previous week
     * 
     * @return the latest price difference
     */
    public BigDecimal getLatestPriceChange(){
        if(prices.size() < 2){
            return BigDecimal.ZERO;
        }
        BigDecimal latestPrice = getSalesPrice();
        BigDecimal previousPrice = prices.get(prices.size() - 2);
        return latestPrice.subtract(previousPrice);
    }
}
