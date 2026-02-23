package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Exchange {
    private final String name;
    private int week;
    private final Map<String, Stock> stockMap;
    private Random random;

    public Exchange(String name, int week, Map<String, Stock> stockMap) {
        this.name = name;
        this.week = week;
        this.stockMap = stockMap;
    }
    public String getName() {
        return name;
    }
    public int getWeek() {
        return week;
    }
    public boolean hasStock(String symbol){
        return stockMap.containsKey(symbol);
    }
    public Stock getStock(String symbol){
        return stockMap.get(symbol);
    }
    public List<Stock> findStocks(String searchTerm){
        return null;
    }
    public Transaction buy(String symbol, BigDecimal quantity, Player player){
        return null;
    }
    public Transaction sell(Share share, Player player){
        return null;
    }
    public void advance(){
        week++;
        for(Stock stock : stockMap.values()){
            BigDecimal lastPrice = stock.getSalesPrice();
            double changePercent = (random.nextDouble() * 0.2) - 0.1; // -10% to +10%
            BigDecimal changeAmount = lastPrice.multiply(BigDecimal.valueOf(changePercent));
            BigDecimal newPrice = lastPrice.add(changeAmount).max(BigDecimal.valueOf(0.01)); // Ensure price doesn't go below 0.01
            stock.addNewSalesPrice(newPrice);
        }
    }
}
