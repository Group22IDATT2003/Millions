package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stock {
    private final String symbol;
    private final String company;
    private List<BigDecimal> prices;

    public Stock(String symbol, String company, BigDecimal salesPrice) {
        this.symbol = requireNonBlank(symbol, "symbol");
        this.company = requireNonBlank(company, "company");
        Objects.requireNonNull(salesPrice, "salesPrice cannot be null");
        if(salesPrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("salesPrice must be > 0");
        }

        this.prices = new ArrayList<>();
        this.prices.add(salesPrice);
    }

    private String requireNonBlank(String value, String fieldName){
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName + " can not blank");
        }
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompany() {
        return company;
    }

    public BigDecimal getSalesPrice() {
        return prices.get(prices.size() - 1);
    }

    public void addNewSalesPrice(BigDecimal price) {
        Objects.requireNonNull(price, "price can not be null");
        if(price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("price must be > 0");
        }
        prices.add(Objects.requireNonNull(price, "price can not be null"));
    }

    public List<BigDecimal> getHistoricalPrices(){
        return List.copyOf(prices);
    }

    public BigDecimal getHighestPrice(){
        return prices.stream()
                .max(BigDecimal::compareTo)
                .orElseThrow(() -> new IllegalStateException("prices list is empty"));
    }

    public BigDecimal getLowestPrice(){
        return prices.stream()
        .min(BigDecimal::compareTo)
        .orElseThrow(() -> new IllegalStateException("prices list is empty"));
    }

    public BigDecimal getLatestPriceChange(){
        if(prices.size() < 2){
            return BigDecimal.ZERO;
        }
        BigDecimal latestPrice = getSalesPrice();
        BigDecimal previousPrice = prices.get(prices.size() - 2);
        return latestPrice.subtract(previousPrice);
    }
}
