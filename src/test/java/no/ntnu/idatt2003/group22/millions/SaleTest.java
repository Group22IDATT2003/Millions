package no.ntnu.idatt2003.group22.millions;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SaleTest {

    private Player player;
    private Share share;
    private Sale sale;

    @BeforeEach
    void setUp(){
        Stock stock = new Stock("AAPL", "Apple Inc", new BigDecimal("100.00"));
        share = new Share(stock, new BigDecimal("2"), new BigDecimal("90.00"));
        player = new Player("Player1", new BigDecimal("1000.00"));
        player.getPortfolio().addShare(share);
        sale = new Sale(share, 2);
    }

    @Test
    @DisplayName("Constructor: valid values create sale correctly")
    void constructor_validValues_createsSaleCorrectly(){
        assertEquals(share, sale.getShare());
        assertEquals(2, sale.getWeek());
        assertTrue(sale.getCalculator() instanceof SaleCalculator);
    }

    @Test
    @DisplayName("commit: valid sale removes share, adds money and archives transaction")
    void commit_validSale_updatesPlayerCorrectly(){
        sale.commit(player);

        assertFalse(player.getPortfolio().containsShare(share));
        assertTrue(player.getTransactionArchive()
        .getAllTransactions()
        .contains(sale));
        assertTrue(sale.isCommitted());

        BigDecimal expectedMoney = new BigDecimal("100.00");
        assertEquals(0, expectedMoney.compareTo(player.getMoney()));
    }

    @Test
    @DisplayName("commit: player who does not own share throws exception")
    void commit_playerDoesNotOwnShare_throwsException(){
        Player player = new Player("player", new BigDecimal("100.00"));

        assertThrows(IllegalStateException.class, () -> 
        sale.commit(player));
    }

    @Test
    @DisplayName("commit: null player throws exception")
    void commit_nullPlayer_throwsException(){
        assertThrows(NullPointerException.class, () -> 
        sale.commit(null));
    }

    @Test
    @DisplayName("commit: committing same sale twice throws exception")
    void commit_sameSaleTwice_throwsException(){
        sale.commit(player);

        assertThrows(IllegalStateException.class, () -> 
        sale.commit(player));
    }
    
}
