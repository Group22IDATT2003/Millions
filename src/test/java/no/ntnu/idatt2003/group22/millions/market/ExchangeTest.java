package no.ntnu.idatt2003.group22.millions.market;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExchangeTest {

    private Exchange exchange;
    private Player player;

    @BeforeEach
    void SetUp() {
        Stock stock1 = new Stock("AAPL", "Apple", new BigDecimal("100"));
        Stock stock2 = new Stock("TSLA", "Tesla", new BigDecimal("200"));

        player = new Player("TestPlayer", new BigDecimal("10000"));
        exchange = new Exchange("NASDAQ", List.of(stock1, stock2));
    }

    @Test
    @DisplayName("getName: returning the correct stockname")
    void getNameReturnCorrectName() {
        assertEquals("NASDAQ", exchange.getName());
    }

    @Test
    @DisplayName("getWeek: weeks starts from one")
    void getWeekStartAtOne() {
        assertEquals(1, exchange.getWeek());
    }

    @Test
    @DisplayName("hasStock: return true when stock exists")
    void hasStockReturnTrueWhenStockExists() {
        assertTrue(exchange.hasStock("AAPL"));
    }

    @Test
    @DisplayName("hasStock: return false when stock does not exist")
    void hasStockReturnFalseWhenStockDoesNotExist() {
        assertFalse(exchange.hasStock("XYZ"));
    }

    @Test
    @DisplayName("getStock: returns correct stock")
    void getStockReturnCorrectStock() {
        assert exchange.getStock("AAPL").getSymbol().equals("AAPL");
    }

    @Test
    @DisplayName("getStock: throws exception when stock does not exist")
    void getStockThrowsExceptionWhenStockDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> exchange.getStock("XYZ"));
    }

    @Test
    @DisplayName("getStock: throws exception when symbol is null")
    void getStockThrowsExceptionWhenSymbolIsNull() {
        assertThrows(IllegalArgumentException.class, () -> exchange.getStock(null));
    }

    @Test
    @DisplayName("findStocks: returns correct stocks")
    void findStocksReturnCorrectStocks() {
        List<Stock> stocks = exchange.findStocks("AAPL");
        assert stocks.size() == 1;
        assert stocks.get(0).getSymbol().equals("AAPL");
    }

    @Test
    @DisplayName("findStocks: returns empty list when no stocks found")
    void findStocksReturnEmptyListWhenNoStocksFound() {
        List<Stock> stocks = exchange.findStocks("XYZ");
        assert stocks.isEmpty();
    }

    @Test
    @DisplayName("findStocks: throws exception when searchTerm is null")
    void findStocksThrowsExceptionWhenSearchTermIsNull() {
        assertThrows(IllegalArgumentException.class, () -> exchange.findStocks(null));
    }

    @Test
    @DisplayName("buy: creates transaction when buying")
    void buyCreatesTransaction(){
        Transaction tx = exchange.buy("AAPL", new BigDecimal("2"), player);
        assertNotNull(tx);
    }

    @Test
    @DisplayName("buy: theow exception if quantity is less than zero")
    void buyThrowsExceptionIfQuantityIsLessThanZero(){
        assertThrows(IllegalArgumentException.class, () -> exchange.buy("AAPL", new BigDecimal("-1"), player));
    }

    @Test
    @DisplayName("buy: theow exception if player is null")
    void buyThrowsExceptionIfPlayerIsNull(){
        assertThrows(IllegalArgumentException.class, () -> exchange.buy("AAPL", new BigDecimal("1"), null));
    }

    @Test
    @DisplayName("sell: creates transaction when selling")
    void sellCreatesTransaction(){
        Stock stock = exchange.getStock("AAPL");
        Share share = new Share(stock, new BigDecimal("2"), new BigDecimal("90"));

        player.getPortfolio().addShare(share);

        Transaction tx = exchange.sell(share, player);

        assertNotNull(tx);
        assertTrue(tx.isCommitted());
    }

    @Test
    @DisplayName("sell: throws exception when player is null")
    void sellThrowsExceptionWhenShareIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> exchange.sell(null, player));
    }

    @Test
    @DisplayName("advanse: increases by weeks")
    void advanceIncreasesWeek() {
        exchange.advance();
        assertEquals(2, exchange.getWeek());
    }

    @Test
    void advanceUpdatesStockPrices() {
    	Stock stock = exchange.getStock("AAPL");
        BigDecimal oldPrice = stock.getSalesPrice();
        exchange.advance();
        BigDecimal newPrice = stock.getSalesPrice();
        assert !oldPrice.equals(newPrice);
    }

    @Test
    @DisplayName("getGainers: returnes limeted numer of stocks")
    void getGainersReturnsLimitedNumberOfStocks() {
        List<Stock> gainers = exchange.getGainers(1);
        assertEquals(1, gainers.size());
    }

    @Test
    @DisplayName("getGainers: throws exception when limit is less than or equal to 0")
    void getGainersThrowsExceptionWhenLimitIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> exchange.getGainers(0));
    }

    @Test
    @DisplayName("getLoser: returnes limeted numer of stocks")
    void getLosersReturnsLimitedNumberOfStocks() {
        List<Stock> losers = exchange.getLosers(1);
        assertEquals(1, losers.size());
    }

    @Test
    @DisplayName("getLosers: throws exception when limit is less than or equal to 0")
    void getLosersThrowsExceptionWhenLimitIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> exchange.getLosers(-1));
    }

}
