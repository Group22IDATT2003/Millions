package no.ntnu.idatt2003.group22.millions.transaction.factory;

import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import no.ntnu.idatt2003.group22.millions.transaction.Purchase;
import no.ntnu.idatt2003.group22.millions.transaction.Sale;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionFactoryTest {
    
    private TransactionFactory factory;
    private Share share;

    @BeforeEach
    void setUp(){
        factory = new TransactionFactory();
        Stock stock = new Stock("AAPL", "Apple", new BigDecimal("100.00"));
        share = new Share(stock, new BigDecimal("2"), new BigDecimal("90.00"));

    }

    @Test
    @DisplayName("createPurchase: returns a Purchase transaction")
    void createPurchase_returnPurchase(){
        Transaction transaction = factory.createPurchase(share, 1);

        assertNotNull(transaction);
        assertTrue(transaction instanceof Purchase);
        assertFalse(transaction.isCommitted());
        assertEquals(1, transaction.getWeek());

    }

    @Test
    @DisplayName("createSale: returns a Sale transaction")
    void createSale_returnsSale(){
        Transaction transaction = factory.createSale(share, 1);

        assertNotNull(transaction);
        assertTrue(transaction instanceof Sale);
        assertFalse(transaction.isCommitted());
        assertEquals(1, transaction.getWeek());
    }

    @Test
    @DisplayName("createPurchase: null share throws exception")
    void createPurchase_nullShare_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> factory.createPurchase(null, 1));
    }

    @Test
    @DisplayName("createSale: null share throws exception")
    void createSale_nullShare_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> factory.createSale(null, 1));
    }
}
