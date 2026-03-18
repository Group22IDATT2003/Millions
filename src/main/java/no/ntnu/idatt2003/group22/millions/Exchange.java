package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.*;

public class Exchange {
    private final String name;
    private int week;
    private final Map<String, Stock> stockMap;
    private final Random random;

    public Exchange(String name, List<Stock> stocks) {
        this.name = name;
        this.week = 1;
        this.random = new Random();
        this.stockMap = new HashMap<>();
        for (Stock stock : stocks) {
            this.stockMap.put(stock.getSymbol(), stock);
        }
    }

    public String getName() {
        if(name == null) throw new IllegalStateException("name is not set");
        return name;
    }

    public int getWeek() {
        if(week < 1) throw new IllegalStateException("week is not set");
        return week;
    }

    public boolean hasStock(String symbol) {
        Objects.requireNonNull(symbol, "symbol can not be null");
        return stockMap.containsKey(symbol);
    }

    public Stock getStock(String symbol) {
        Objects.requireNonNull(symbol, "symbol can not be null");
        if (!stockMap.containsKey(symbol)) {
            throw new IllegalArgumentException("Unknown stock symbol: " + symbol);
        }
        return stockMap.get(symbol);
    }

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

    public Transaction buy(String symbol, BigDecimal quantity, Player player) {
        Objects.requireNonNull(symbol, "symbol can not be null");
        Objects.requireNonNull(player, "player can not be nul");
        Objects.requireNonNull(quantity, "quantity can not be null");
        if(quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }

        Stock stock = getStock(symbol);
        Share share = new Share(stock, quantity, stock.getSalesPrice());
        Transaction tx = new Purchase(share, week);
        tx.commit(player);
        return tx;
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
