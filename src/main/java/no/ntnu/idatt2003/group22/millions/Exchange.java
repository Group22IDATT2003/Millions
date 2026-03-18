package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Exchange {
    private final String name;
    private int week;
    private final Map<String, Stock> stockMap;
    private final Random random;

    public Exchange(String name, int week, Map<String, Stock> stockMap, Random random) {
        this.name = name;
        this.week = week;
        this.stockMap = stockMap;
        this.random = random;
    }

    public String getName() {
        return name;
    }

    public int getWeek() {
        if(week <= 0) throw new IllegalArgumentException("week can not be null or negative");
        return week;
    }

    public boolean hasStock(String symbol) {
        Objects.requireNonNull(symbol, "symbol can not be null");
        return stockMap.containsKey(symbol);
    }

    public Stock getStock(String symbol) {
        Objects.requireNonNull(symbol, "symbol can not be null");
        Stock stock = stockMap.get(symbol);
        if (stock == null){
            throw new IllegalArgumentException("Unknown stock symbol: " + symbol);
        }
        return stock;
    }

    public List<Stock> findStocks(String searchTerm) {
        return null;
    }

    public Transaction buy(String symbol, BigDecimal quantity, Player player) {
        Objects.requireNonNull(player, "player can not be nul");
        Objects.requireNonNull(quantity, "quantity can not be null");
        if(quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        return null;
    }

    public Transaction sell(Share share, Player player) {
        Objects.requireNonNull(player, "player can not be null");
        Objects.requireNonNull(share, "share can not be null");

        Transaction tx = new Sale(share, week);
        tx.commit(player);
        return tx;
    }

    public void advance() {
        week++;

        for (Stock stock : stockMap.values()) {
            BigDecimal lastPrice = stock.getSalesPrice();
            double changePercent = (random.nextDouble() * 0.2) - 0.1; // -10% to +10%
            
            BigDecimal changeAmount = lastPrice.multiply(BigDecimal.valueOf(changePercent));
            BigDecimal newPrice = lastPrice.add(changeAmount);

            if(newPrice.compareTo(BigDecimal.valueOf(0.01)) < 0){
                newPrice = BigDecimal.valueOf(0.01);
            }
            
            stock.addNewSalesPrice(newPrice);
        }
    }
}
