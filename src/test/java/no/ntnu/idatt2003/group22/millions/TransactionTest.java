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

        boolean isDoCommitCalles(){
            return doCommitCalled;
        }
    }

    @BeforeEach
    void setUp(){
        Stock stock = new Stock("AAPL", "Apple Inc", new BigDecimal("100.00"));
        share = new Share(stock, new BigDecimal("2"), new BigDecimal("90.00"));
        player = new Player("Player1", new BigDecimal("1000.00"));
    }
    
}
