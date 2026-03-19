package no.ntnu.idatt2003.group22.millions;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PortfolioTest {

    private Portfolio portfolio;
    private Share appleShare1;
    private Share appleShare2;
    private Share samsungShare;

    @BeforeEach
    void setUp(){
        portfolio = new Portfolio();

        Stock apple = new Stock("AAPL", "Apple", new BigDecimal("100.00"));
        Stock samsung = new Stock("SSNG", "Samsung", new BigDecimal("100.00"));

        appleShare1 = new Share(apple, new BigDecimal("2"), new BigDecimal("95.00"));
        appleShare2 = new Share(apple, new BigDecimal("5"), new BigDecimal("99.00"));
        samsungShare = new Share(samsung, new BigDecimal("1"), new BigDecimal("190.00"));
    }

    @Test
    @DisplayName("addShare: validshare is added to portfolio")
    void addShare_validShare_returnsTrueAndAddsShare(){
        boolean added = portfolio.addShare(appleShare1);

        assertTrue(added);
        assertTrue(portfolio.containsShare(appleShare1));
        assertEquals(1, portfolio.getShares().size());
    }


    @Test
    @DisplayName("addShare: null share throws exception")
    void addShare_nullShare_throwsException(){
        assertThrows(NullPointerException.class, () -> 
        portfolio.addShare(null));
    }

    @Test
    @DisplayName("removeShare: existing share is removed")
    void removeShare_existingShare_returnsTrue() {
        portfolio.addShare(appleShare1);

        boolean removed = portfolio.removeShare(appleShare1);

        assertTrue(removed);
        assertFalse(portfolio.containsShare(appleShare1));
    }

    @Test
    @DisplayName("removeShare: non existing shares returns false")
    void remove_share_nonExistingShare_returnsFalse(){
        boolean removed = portfolio.removeShare(appleShare1);

        assertFalse(removed);
    }

    @Test
    @DisplayName("removeShare: null share throws exception")
    void removeShare_nullShare_throwsException(){
        assertThrows(NullPointerException.class, () -> 
        portfolio.removeShare(null)); 
    }

    @Test
    @DisplayName("conainsShare: existing share returns true")
    void containsShare_existingShare_returnsTrue(){
        portfolio.addShare(appleShare1);

        assertTrue(portfolio.containsShare(appleShare1));
    }

    @Test
    @DisplayName("containsShare: missing share returns false")
    void containsShare_missingShare_returnsFalse(){
        assertFalse(portfolio.containsShare(appleShare1));
    }

    @Test
    @DisplayName("containsShare: null share throws exception")
    void containsShare_nullShare_throwsException(){
        assertThrows(NullPointerException.class, () -> 
        portfolio.containsShare(appleShare1));
    }

    @Test
    @DisplayName("getShares: returns all shares in portfolio")
    void getShares_retueensAllShares(){
        portfolio.addShare(appleShare1);
        portfolio.addShare(samsungShare);

        List<Share> shares = portfolio.getShares();

        assertEquals(2, shares.size());
        assertTrue(shares.contains(appleShare1));
        assertTrue(shares.contains(samsungShare));
    }

    @Test
    @DisplayName("getShares(symbol): retuen only matching symbol")
    void getShares_symbol_returnsMatchingSharesOnly(){
        portfolio.addShare(appleShare1);
        portfolio.addShare(appleShare2);
        portfolio.addShare(samsungShare);

        List<Share> shares = portfolio.getShares("AAPL");

        assertEquals(2, shares.size());
        assertTrue(shares.contains(appleShare1));
        assertTrue(shares.contains(appleShare2));
        assertFalse(shares.contains(samsungShare));
    }

    @Test
    @DisplayName("getShares(symbol): unknown symbol returns empty list")
    void getShares_unknownSymbol_retuensEmptyList(){
        portfolio.addShare(appleShare1);

        List<Share> shares = portfolio.getShares("XXXX");

        assertTrue(shares.isEmpty());
    }

    @Test
    @DisplayName("getShares(symbol): null symbol throws exception")
    void getShares_nullSymbol_throwsException(){
        assertThrows(NullPointerException.class, () -> 
        portfolio.getShares(null));
    }

    @Test
    @DisplayName("getShares: returnes list is immutable")
    /**
     * fikk hjelp av chat til denne testen
     */

    void getShares_returnedListIsImmutable(){
        portfolio.addShare(appleShare1);

        List<Share> shares = portfolio.getShares();

        assertThrows(UnsupportedOperationException.class, () -> { 
        shares.add(samsungShare);
    });
    }

}
