package no.ntnu.idatt2003.group22.millions.market;

import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;
import no.ntnu.idatt2003.group22.millions.transaction.factory.TransactionFactory;
import no.ntnu.idatt2003.group22.millions.observer.GameObservable;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.*;

/**
 * Represents a stock exchange for trading shares
 */
public class Exchange extends GameObservable{
    private final String name;
    private int week;
    private final Map<String, Stock> stockMap;
    private final Random random;
    private final TransactionFactory transactionFactory;

    /**
     * Creates an exchange with a name and stocks
     * 
     * @param name the exchange name
     * @param stocks the available stocks
     * @throws IllegalArgumentException if values are invalid
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
        this.transactionFactory = new TransactionFactory();

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
     * Returns the exchange name
     * 
     * @return the exchange name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current exchange week
     * 
     * @return the current week
     */
    public int getWeek() {
        return week;
    }

    /**
     * Checks if a stock exists on the exchange
     * 
     * @param symbol the stock symbol
     * @return true if the stock exists
     * @throws IllegalArgumentException if the symbol is null
     */
    public boolean hasStock(String symbol) {
        if(symbol == null){
            throw new IllegalArgumentException("symbol cannot be null");
        }
        return stockMap.containsKey(symbol);
    }

    /**
     * Returns the stock with the given symbol
     * 
     * @param symbol the stock symbol
     * @return the matching stock
     * @throws IllegalArgumentException if symbol is invalid
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
     * Finds stocks matching a search team
     * 
     * @param searchTerm the search term
     * @return a list of matching stocks
     */
    public List<Stock> findStocks(String searchTerm) {
        if(searchTerm == null){
            throw new IllegalArgumentException("searchTerm cannot be null");
        }

        if(searchTerm.isBlank()){
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
     * Buys shares of a stock
     * 
     * @param symbol the stock symbol
     * @param quantity the share quantity
     * @param player the player buying shares
     * @return the completed shares
     * @throws IllegalArgumentException if values are invalid
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


        Transaction tx = transactionFactory.createPurchase(share, week);
        tx.commit(player);
        notifyObservers();
        return tx;
    }

    /**
     * Sells a share
     * 
     * @param share the share to sell
     * @param player the player selling the share
     * @return the completed transaction
     * @throws IllegalArgumentException if values are invalid
     */
    public Transaction sell(Share share, Player player) {
        if(player == null){
            throw new IllegalArgumentException("player cannot be null");
        }
        if(share == null){
            throw new IllegalArgumentException("share cannot be null");
        }

        Transaction tx = transactionFactory.createSale(share, week);
        tx.commit(player);
        notifyObservers();
        return tx;
    }

        public Transaction sell(Share share, BigDecimal quantityToSell, Player player) {
        if(player == null){
            throw new IllegalArgumentException("player cannot be null");
        }
        if(share == null){
            throw new IllegalArgumentException("share cannot be null");
        }

        if(quantityToSell == null){
            throw new IllegalArgumentException("quantityToSell cannot be null");
        }

        if(quantityToSell.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        if(quantityToSell.compareTo(share.getQuantity()) > 0){
            throw new IllegalArgumentException("Cannot sell more than you own");
        }

        if(!player.getPortfolio().containsShare(share)){
            throw new IllegalStateException("Player does not own this share");
        }

        if(quantityToSell.compareTo(share.getQuantity()) == 0){
            return sell(share, player);
        }

        BigDecimal remainingQuantity = share.getQuantity().subtract(quantityToSell);

        Share shareToSell = new Share(
            share.getStock(),
            quantityToSell,
            share.getPurchasePrice()
        );

        Share remainingShare = new Share(
            share.getStock(),
            remainingQuantity,
            share.getPurchasePrice()
        );

        player.getPortfolio().removeShare(share);
        player.getPortfolio().addShare(shareToSell);

        Transaction tx = transactionFactory.createSale(shareToSell, week);
        tx.commit(player);

        player.getPortfolio().addShare(remainingShare);
        
        notifyObservers();
        return tx;
    }

    /**
     * Advances the exchange to the next week
     * Updates all stock prices randomly
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

        notifyObservers();
    }

    /**
     * Returns the top gaining stocks
     * 
     * @param limit maximum number of stocks
     * @return list of the top gainers.
     * @throws IllegalArgumentException of limit is invalid
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
     * Returns the top losing stocks
     * 
     * @param limit maximum number of stocks
     * @return list of the top losers.
     * @throws IllegalArgumentException if limit is invalid
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
