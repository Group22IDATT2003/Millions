package no.ntnu.idatt2003.group22.millions.transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionArchiveTest {
    private TransactionArchive archive;
    private Purchase purchaseWeek1;
    private Sale saleWeek2;
    private Purchase purchaseWeek2;

    @BeforeEach
    void setUp() {
        archive = new TransactionArchive();

        Stock stock = new Stock("AAPL", "Apple", new BigDecimal("100.00"));
        Share share1 = new Share(stock, new BigDecimal("2"), new BigDecimal("90.00"));
        Share share2 = new Share(stock, new BigDecimal("1"), new BigDecimal("95.00"));

        purchaseWeek1 = new Purchase(share1, 1);
        saleWeek2 = new Sale(share1, 2);
        purchaseWeek2 = new Purchase(share2, 2);
    }

    @Test
    @DisplayName(" Verifies that a valid transaction can be added. ")
    void add_validTransaction_returnsTrue() {
        assertTrue(archive.add(purchaseWeek1));
    }

    @Test
    @DisplayName("Verifies that adding null throws an exception. ")
    void add_nullTransaction_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> archive.add(null));
    }

    @Test
    @DisplayName(" Verifies that a new archive is empty. ")
    void isEmpty_newArchive_returnsTrue() {
        assertTrue(archive.isEmpty());
    }

    @Test
    @DisplayName(" Verifies that the archive is not empty after adding a transaction. ")
    void isEmpty_afterAdd_returnsFalse() {
        archive.add(purchaseWeek1);
        assertFalse(archive.isEmpty());
    }

    @Test
    @DisplayName("Verifies taht transactions are filtered by week. ")
    void getTransactions_week_returnsOnlyThatWeek() {
        archive.add(purchaseWeek1);
        archive.add(saleWeek2);
        archive.add(purchaseWeek2);

        List<Transaction> result = archive.getTransactions(2);

        assertEquals(2, result.size());
        assertTrue(result.contains(saleWeek2));
        assertTrue(result.contains(purchaseWeek2));
    }

    @Test
    @DisplayName(" Verifies that an invalid week throws an exception. ")
    void getTransactions_invalidWeek_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> archive.getTransactions(0));
    }

    @Test
    @DisplayName(" Verifies that purchases are filtered by week. ")
    void getPurchases_week_returnsOnlyPurchasesForWeek() {
        archive.add(purchaseWeek1);
        archive.add(saleWeek2);
        archive.add(purchaseWeek2);

        List<Purchase> result = archive.getPurchases(2);

        assertEquals(1, result.size());
        assertTrue(result.contains(purchaseWeek2));
    }

    @Test
    @DisplayName(" Verifies that sales are filtered by week. ")
    void getSales_week_returnsOnlySalesForWeek() {
        archive.add(purchaseWeek1);
        archive.add(saleWeek2);
        archive.add(purchaseWeek2);

        List<Sale> result = archive.getSales(2);

        assertEquals(1, result.size());
        assertTrue(result.contains(saleWeek2));
    }

    @Test
    @DisplayName(" Verifies that distinct transaction weeks are counted correctly")
    void countDistinctWeeks_returnsCorrectCount() {
        archive.add(purchaseWeek1);
        archive.add(saleWeek2);
        archive.add(purchaseWeek2);

        assertEquals(2, archive.countDistinctWeeks());
    }

    @Test
    @DisplayName(" Verifies that all transactions are returned as an immutable copy. ")
    void getAllTransactions_returnsImmutableCopy() {
        archive.add(purchaseWeek1);
        List<Transaction> transactions = archive.getAllTransactions();

        assertEquals(1, transactions.size());
        assertThrows(UnsupportedOperationException.class,
                () -> transactions.add(saleWeek2));
    }
}
