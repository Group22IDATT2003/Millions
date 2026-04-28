package no.ntnu.idatt2003.group22.millions.market;

import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import no.ntnu.idatt2003.group22.millions.transaction.Purchase;
import no.ntnu.idatt2003.group22.millions.transaction.Sale;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.*;

/**
 * Represents an exchange where players can buy and sell shares.
 * This class manages the stocks available for trading,
 * tracks the current week,
 * and provides methods to buy and sell shares.
 * It also provides methods to advance the exchange's time and
 * retrieve the top gainers and losers.
 */
public class Exchange {
    private final String name;
    private int week;
    private final Map<String, Stock> stockMap;
    private final Random random;

    /**
     * Constructor for Exchange.
     * @param name the name of the exchange.
     * @param stocks the list of stocks available for trading.
     */
    public Exchange(String name, List<Stock> stocks) {
        if(name == null){
            throw new IllegalArgumentException("name cannot be null");
        } 
        if(stocks == null){
            throw new IllegalArgumentException("stocks cannot be null");
        }
        this.name = name;
        this.week = 1;
        this.random = new Random();
        this.stockMap = new HashMap<>();

        for (Stock stock : stocks) {
            if(stock == null){
                throw new IllegalArgumentException("stock cannot be null");
            }

            String symbol = stock.getSymbol();
            if(stockMap.containsKey(symbol)){
                throw new IllegalArgumentException("duplicate stock symbol: " + symbol);
            }
            
            stockMap.put(stock.getSymbol(), stock);
        }

    }

    /**
     * Returns the name of the exchange.
     * @return the name of the exchange.
     * @throws IllegalStateException if the name is not set.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current week of the exchange.
     * @return the current week.
     * @throws IllegalArgumentException if the week is not set or negative.
     */
    public int getWeek() {
        return week;
    }

    /**
     * Checks if the exchange has a stock with the given symbol.
     * @param symbol the symbol of the stock to check.
     * @return true if the exchange has the stock, false otherwise.
     * @throws IllegalArgumentException if the symbol is null.
     */
    public boolean hasStock(String symbol) {
        if(symbol == null){
            throw new IllegalArgumentException("symbol cannot be null");
        }
        return stockMap.containsKey(symbol);
    }

    /**
     * Returns the stock with the given symbol.
     * @param symbol the symbol of the stock to retrieve.
     * @return the stock with the given symbol.
     * @throws IllegalArgumentException if the symbol is null or the exchange does not have the stock.
     */
    public Stock getStock(String symbol) {
        if(symbol == null){
            throw new IllegalArgumentException("symbol cannot be null");
        }
        
        if (!stockMap.containsKey(symbol)) {
            throw new IllegalArgumentException("Unknown stock symbol: " + symbol);
        }
        return stockMap.get(symbol);
    }

    /**
     * Finds stocks that contain the search term in their symbol or company name.
     * @param searchTerm the term to search for in stock symbols and company names.
     * @return a list of stocks that contain the search term.
     * @throws IllegalArgumentException if the search term is null.
     */
    public List<Stock> findStocks(String searchTerm) {
        if(searchTerm == null || searchTerm.isBlank()){
            return new ArrayList<>(stockMap.values());
        }
        List<Stock> result = new ArrayList<>();
        String lowerSearch = searchTerm.toLowerCase();

        for (Stock stock : stockMap.values()) {
            String symbol = stock.getSymbol().toLowerCase();
            String company = stock.getCompany().toLowerCase();

            if (symbol.contains(lowerSearch) || company.contains(lowerSearch)) {
                result.add(stock);
            }
        }
        return result;
    }

    /**
     * Buys shares of a stock with the given symbol and quantity.
     * @param symbol the symbol of the stock to buy.
     * @param quantity the quantity of shares to buy.
     * @param player the player who is buying the shares.
     * @return the transaction object representing the purchase.
     * @throws IllegalArgumentException if the symbol is null, the player is null, or the quantity is negative.
     */
    public Transaction buy(String symbol, BigDecimal quantity, Player player) {
        if(symbol == null){
            throw new IllegalArgumentException("symbol cannot be null");
        }
        if(player == null){
            throw new IllegalArgumentException("player cannot be null");
        }
        if(quantity == null){
            throw new IllegalArgumentException("quantity cannot be null");
        }
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }

        Stock stock = getStock(symbol);
        Share share = new Share(stock, quantity, stock.getSalesPrice());
        Transaction tx = new Purchase(share, week);
        tx.commit(player);
        return tx;
    }

    /**
     * Sells a share of a stock.
     * @param share 1 share of a stock.
     * @param player the player who is selling the share.
     * @return the transaction object representing the sale.
     * @throws IllegalArgumentException if the player is null or the share is null.
     */
    public Transaction sell(Share share, Player player) {
        if(player == null){
            throw new IllegalArgumentException("player cannot be null");
        }
        if(share == null){
            throw new IllegalArgumentException("share cannot be null");
        }

        Transaction tx = new Sale(share, week);
        tx.commit(player);
        return tx;
    }

    /**
     * Advances the exchange's time by one week.
     * This method updates the stock prices and the week number.
     * It also calculates the latest price change for each stock.
     */
    public void advance() {
        week++;

        for (Stock stock : stockMap.values()) {
            BigDecimal lastPrice = stock.getSalesPrice();
            double changePercent = (random.nextDouble() * 0.2) - 0.1; // -10% to +10%

            BigDecimal changeAmount = lastPrice.multiply(BigDecimal.valueOf(changePercent));
            BigDecimal newPrice = lastPrice.add(changeAmount);

            if (newPrice.compareTo(BigDecimal.valueOf(0.01)) < 0) {
                newPrice = BigDecimal.valueOf(0.01);
            }

            newPrice = newPrice.setScale(2, RoundingMode.HALF_UP);

            stock.addNewSalesPrice(newPrice);
        }
    }

    /**
     * Returns the top gainers in the exchange.
     * @param limit the number of stocks to return.
     * @return a list of the top gainers.
     * @throws IllegalArgumentException if the limit is less than or equal to 0.
     */
    public List<Stock> getGainers(int limit) {
        if(limit <= 0){
            throw new IllegalArgumentException("limit must be > 0");
        }

        List<Stock> stocks = new ArrayList<>(stockMap.values());

        stocks.sort((s1, s2) -> {
            BigDecimal change1 = s1.getLatestPriceChange();
            BigDecimal change2 = s2.getLatestPriceChange();
            return change2.compareTo(change1); // størst først
        });

        if (stocks.size() > limit) {
            return stocks.subList(0, limit);
        }
        return stocks;
    }

    /**
     * Returns the top losers in the exchange.
     * @param limit the number of stocks to return.
     * @return a list of the top losers.
     * @throws IllegalArgumentException if the limit is less than or equal to 0.
     */
    public List<Stock> getLosers(int limit) {
        if(limit <= 0){
            throw new IllegalArgumentException("limit must be > 0");
        }

        List<Stock> stocks = new ArrayList<>(stockMap.values());

        stocks.sort((s1,s2) -> {
            BigDecimal change1 = s1.getLatestPriceChange();
            BigDecimal change2 = s2.getLatestPriceChange();
            return change1.compareTo(change2); // minst først
        });
        
        if (stocks.size() > limit) {
            return stocks.subList(0, limit);
        }
        return stocks;
    }

    public List<Stock> getStocks() {
        return List.copyOf(stockMap.values());
    }


}
