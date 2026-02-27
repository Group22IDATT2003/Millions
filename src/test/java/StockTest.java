package no.ntnu.idatt2003.group22.millions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.math.BigDecimal;
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
    @DisplayName("Konstruktør: setter symbol riktig")
    void constructor_setsSymbolCorrectly(){
        String symbol = stock.getSymbol();

        assertEquals("AAPL", symbol);

    }
    
}
