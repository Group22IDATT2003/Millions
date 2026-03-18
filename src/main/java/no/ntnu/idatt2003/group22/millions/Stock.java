package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stock {
    private String symbol;
    private String company;
    private List<BigDecimal> prices;

    public Stock(String symbol, String company, BigDecimal salesPrice) {
        this.symbol = requireNonBlank(symbol, "symbol");
        this.company = requireNonBlank(company, "company");

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
        prices.add(Objects.requireNonNull(price, "price can not be null"));
    }

    public List<BigDecimal> getHistoricalPrices(){
        return List.copyOf(prices);
    }

    public BigDecimal getHistoricalPrice(){
        return prices.get(prices.size() - 1);
    }

    public BigDecimal getLowestPrice(){
        return prices.get(0);
    }

    public BigDecimal getLatestPriceChange(){
        return prices.get(prices.size() - 1)
                .subtract(prices.get(prices.size() - 2));
    }
}
