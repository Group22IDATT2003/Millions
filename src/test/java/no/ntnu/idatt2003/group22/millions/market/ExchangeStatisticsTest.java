package no.ntnu.idatt2003.group22.millions.market;

import no.ntnu.idatt2003.group22.millions.model.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeStatisticsTest {
    @Test
    void getGainers_returnsSortedList() {
        Stock s1 = new Stock("A", "A", new BigDecimal("100"));
        Stock s2 = new Stock("B", "B", new BigDecimal("100"));

        s1.addNewSalesPrice(new BigDecimal("150"));
        s2.addNewSalesPrice(new BigDecimal("110"));

        Exchange exchange = new Exchange("Test", List.of(s1, s2));
        
        List<Stock> gainers = exchange.getGainers(1);
        
        assertEquals("A", gainers.get(0).getSymbol());
    }

    @Test
    void getLosers_returnsSortedList() {
        Stock s1 = new Stock("A", "A", new BigDecimal("100"));
        Stock s2 = new Stock("B", "B", new BigDecimal("100"));

        s1.addNewSalesPrice(new BigDecimal("100"));
        s2.addNewSalesPrice(new BigDecimal("110"));

        Exchange exchange = new Exchange("Test", List.of(s1, s2));
        
        List<Stock> losers = exchange.getLosers(1);
        
        assertEquals("A", losers.get(0).getSymbol());
    }
}

    
