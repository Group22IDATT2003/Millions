package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a stock in the financial market.
 * This class stores the symbol, company name, and historical sales prices of a stock.
 * It provides methods to retrieve the latest sales price,
 * the highest and lowest prices,
 * and the price change since the last trading day.
 */
public class Stock {
    private final String symbol;
    private final String company;
    private final List<BigDecimal> prices;

    /**
     * Constructor for Stock.
     * @param symbol the unique symbol of the stock.
     * @param company the name of the company that owns the stock.
     * @param salesPrice the current sales price of the stock.
     *
     * @throws IllegalArgumentException if the symbol, company, or salesPrice is null or not greater than zero.
     */
    public Stock(String symbol, String company, BigDecimal salesPrice) {
        if (symbol == null) {
            throw new IllegalArgumentException("symbol can not be null");
        }
        if (company == null) {
            throw new IllegalArgumentException("company can not be null");
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
     * Gets the symbol of the stock.
     * @return the symbol of the stock.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the company name of the stock.
     * @return the company name of the stock.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Gets the latest sales price of the stock.
     * @return the latest sales price of the stock.
     */
    public BigDecimal getSalesPrice() {
        return prices.get(prices.size() - 1);
    }

    /**
     * Adds a new sales price to the stock's historical prices.
     * @param price the sales price to add.
     * @throws IllegalArgumentException if the price is null or not greater than zero.
     */
    public void addNewSalesPrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("price can not be null");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price must be > 0");
        }
        prices.add(price);
    }

    /**
     * Gets a copy of the stock's historical prices.
     * @return a copy of the stock's historical prices.
     */
    public List<BigDecimal> getHistoricalPrices(){
        return List.copyOf(prices);
    }

    /**
     * Gets the highest sales price of the stock.
     * @return the highest sales price of the stock.
     */
    public BigDecimal getHighestPrice(){
        return prices.stream()
                .max(BigDecimal::compareTo)
                .orElseThrow(() -> new IllegalStateException("prices list is empty"));
    }

    /**
     * Gets the lowest sales price of the stock.
     * @return the lowest sales price of the stock.
     */
    public BigDecimal getLowestPrice(){
        return prices.stream()
        .min(BigDecimal::compareTo)
        .orElseThrow(() -> new IllegalStateException("prices list is empty"));
    }

    /**
     * Gets the price change since the last trading day.
     * @return the price change since the last trading day.
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
