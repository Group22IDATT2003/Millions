package no.ntnu.idatt2003.group22.millions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StockTest {

    private Stock stock;

    @BeforeEach
    void setUp(){
        stock = new Stock("AAPL", "Apple", new BigDecimal("100"));
    }

    @Test
    @DisplayName("getSalesPrice: returnerer siste pris etter prisoppdatering")
    void getSalesPrice_afterAddingNewPrice_returnsLatestPrice(){
        stock.addNewSalesPrice(new BigDecimal("120"));

        BigDecimal result = stock.getSalesPrice();

        assertEquals(new BigDecimal("120"), result);
    }

    @Test
    @DisplayName("constuctor: creates stock correctly")
    void constructorCreatesStockCorrectly() {
        assertEquals("AAPL", stock.getSymbol());
        assertEquals("Apple", stock.getCompany());
        assertEquals(new BigDecimal("100"), stock.getSalesPrice());
    }

    @Test
    @DisplayName("constructor: throws exception when price is less than or equal to zero")
    void constructorThrowsExceptionWhenSymbolIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Stock(null, "Apple", new BigDecimal("100")));
    }

    @Test
    @DisplayName("constructor: throws exception when symbol is empty")
    void constructorThrowsExceptionWhenCompanyIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Stock("AAPL", null, new BigDecimal("100")));
    }

    @Test
    @DisplayName("constructor: throws exception when company is empty")
    void constructorThrowsExceptionWhenPriceIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Stock("AAPL", "Apple", null));
    }

    @Test
    @DisplayName("constructor: throws exception when price is empty")
    void constructorThrowsExceptionWhenPriceIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Stock("AAPL", "Apple", BigDecimal.ZERO));
    }

    @Test
    @DisplayName("addNewSalesPrice: adds new price")
    void addNewSalesPriceAddsPrice() {
        stock.addNewSalesPrice(new BigDecimal("120"));

        assertEquals(new BigDecimal("120"), stock.getSalesPrice());
    }

    @Test
    @DisplayName("addNewSalesPrice: throws exception when price is empty")
    void addNewSalesPriceThrowsExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class,
                () -> stock.addNewSalesPrice(null));
    }

    @Test
    @DisplayName("addNewSalesPrice: throws exception when price is less than or equal to zero")
    void addNewSalesPriceThrowsExceptionWhenNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> stock.addNewSalesPrice(new BigDecimal("-10")));
    }

    @Test
    @DisplayName("getHistoricalPrices: returns copy ")
    void getHistoricalPricesReturnsCopy() {
        List<BigDecimal> prices = stock.getHistoricalPrices();

        assertEquals(1, prices.size());

        assertThrows(UnsupportedOperationException.class,
                () -> prices.add(new BigDecimal("200")));
    }

    @Test
    @DisplayName("getHighestAndLowestPrice: returns highest and lowest price")
    void getHighestAndLowestPriceWorks() {
        stock.addNewSalesPrice(new BigDecimal("120"));
        stock.addNewSalesPrice(new BigDecimal("80"));

        assertEquals(new BigDecimal("120"), stock.getHighestPrice());
        assertEquals(new BigDecimal("80"), stock.getLowestPrice());
    }

    @Test
    @DisplayName("getLatestPriceChange: returns difference between latest price and previous price")
    void getLatestPriceChangeReturnsCorrectDifference() {
        stock.addNewSalesPrice(new BigDecimal("120"));

        assertEquals(new BigDecimal("20"), stock.getLatestPriceChange());
    }

}
