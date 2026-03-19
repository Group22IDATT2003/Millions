package no.ntnu.idatt2003.group22.millions;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    private Share share;
    private Player player;

    private static class TestTransaction extends Transaction{
        private boolean doCommitCalled = false;

        TestTransaction(Share share, int week, TransactionCalculator calculator){
            super(share, week, calculator);
        }

        @Override
        protected void doCommit(Player player){
            doCommitCalled = true;
        }

        boolean isDoCommitCalled(){
            return doCommitCalled;
        }
    }

    @BeforeEach
    void setUp(){
        Stock stock = new Stock("AAPL", "Apple Inc", new BigDecimal("100.00"));
        share = new Share(stock, new BigDecimal("2"), new BigDecimal("90.00"));
        player = new Player("Player1", new BigDecimal("1000.00"));
    }

    @Test
    @DisplayName("Constructor: valid values creates transaction correctly")
    void constructor_validValues_createsTransactionCorrectly(){
        TransactionCalculator calculator = new PurchaseCalculator(share);
        TestTransaction transaction = new TestTransaction(share, 1, calculator);

        assertEquals(share, transaction.getShare());
        assertEquals(1, transaction.getWeek());
        assertEquals(calculator, transaction.getCalculator());
        assertFalse(transaction.isCommitted());
    }

    @Test
    @DisplayName("Constructor: null share throws exception")
    void constructor_nullShare_throwsException(){
        TransactionCalculator calculator = new PurchaseCalculator(share);

        assertThrows(NullPointerException.class, () -> 
        new TestTransaction(null, 1, calculator));
    }

    @Test
    @DisplayName("Constructor: invalid week throws exception")
    void constructor_invalidWeek_throwsException() {
        TransactionCalculator calculator = new PurchaseCalculator(share);

        assertThrows(IllegalArgumentException.class, () -> 
        new TestTransaction(share, 0, calculator));
    }

    @Test
    @DisplayName("Construcotr: null calcultor throws exception")
    void constructor_nullCalculator_throwsException() {
        assertThrows(NullPointerException.class, () -> 
        new TestTransaction(share, 1, null));
    }

    @Test
    @DisplayName("commit: first commit runs doCommit and marks transaction as commited")
    void commit_firstCommit_runsDoCommitAndMarksAsCommited(){
        TransactionCalculator calculator = new PurchaseCalculator(share);
        TestTransaction transaction = new TestTransaction(share, 1, calculator);

        transaction.commit(player);

        assertTrue(transaction.isDoCommitCalled());
        assertTrue(transaction.isCommitted());
    }

    @Test
    @DisplayName("commit: null player throws exception")
    void commit_nullPlayer_throwsException(){
        TransactionCalculator calculator = new PurchaseCalculator(share);
        TestTransaction transaction = new TestTransaction(share, 1, calculator);

        assertThrows(NullPointerException.class, () -> 
        transaction.commit(null));
    }

    @Test
    @DisplayName("commit: second commit throws exception")
    void commit_secondCommitt_throwsException(){
        TransactionCalculator calculator = new PurchaseCalculator(share);
        TestTransaction transaction = new TestTransaction(share, 1, calculator);

        transaction.commit(player);

        assertThrows(IllegalStateException.class, () -> 
        transaction.commit(player));
    }
    
}
