package no.ntnu.idatt2003.group22.millions;

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
        this.name = name;
        this.week = 1;
        this.random = new Random();
        this.stockMap = new HashMap<>();
        for (Stock stock : stocks) {
            this.stockMap.put(stock.getSymbol(), stock);
        }

    }

    /**
     * Returns the name of the exchange.
     * @return the name of the exchange.
     * @throws IllegalStateException if the name is not set.
     */
    public String getName() {
        if (name == null) throw new IllegalStateException("name is not set");
        return name;
    }

    /**
     * Returns the current week of the exchange.
     * @return the current week.
     * @throws IllegalArgumentException if the week is not set or negative.
     */
    public int getWeek() {
        if(week <= 0) throw new IllegalArgumentException("week can not be null or negative");
        return week;
    }

    /**
     * Checks if the exchange has a stock with the given symbol.
     * @param symbol the symbol of the stock to check.
     * @return true if the exchange has the stock, false otherwise.
     * @throws IllegalArgumentException if the symbol is null.
     */
    public boolean hasStock(String symbol) {
        Objects.requireNonNull(symbol, "symbol can not be null");
        return stockMap.containsKey(symbol);
    }

    /**
     * Returns the stock with the given symbol.
     * @param symbol the symbol of the stock to retrieve.
     * @return the stock with the given symbol.
     * @throws IllegalArgumentException if the symbol is null or the exchange does not have the stock.
     */
    public Stock getStock(String symbol) {
        Objects.requireNonNull(symbol, "symbol can not be null");
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
        Objects.requireNonNull(searchTerm, "searchTerm can not be null");
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
        Objects.requireNonNull(symbol, "symbol can not be null");
        Objects.requireNonNull(player, "player can not be nul");
        Objects.requireNonNull(quantity, "quantity can not be null");
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
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
        Objects.requireNonNull(player, "player can not be null");
        Objects.requireNonNull(share, "share can not be null");

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
        List<Stock> stocks = new ArrayList<>(stockMap.values());

        stocks.sort((s1, s2) -> {
            BigDecimal change1 = s1.getLatestPriceChange();
            BigDecimal change2 = s2.getLatestPriceChange();
            return change2.compareTo(change1); // størst først
        });

        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be more than 0");
        }
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
        List<Stock> stocks = new ArrayList<>(stockMap.values());

        stocks.sort((s1,s2) -> {
            BigDecimal change1 = s1.getLatestPriceChange();
            BigDecimal change2 = s2.getLatestPriceChange();
            return change1.compareTo(change2); // minst først
        });
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be more than 0");
        }
        if (stocks.size() > limit) {
            return stocks.subList(0, limit);
        }
        return stocks;
    }


}
