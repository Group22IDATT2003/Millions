package no.ntnu.idatt2003.group22.millions.transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import no.ntnu.idatt2003.group22.millions.transaction.calculator.PurchaseCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PurchaseTest {

    private Player player;
    private Share share;
    private Purchase purchase;

    @BeforeEach
    void setUp() {
        Stock stock = new Stock("AAPL", "Apple Inc", new BigDecimal("100.00"));
        share = new Share(stock, new BigDecimal("2"), new BigDecimal("90.00"));
        player = new Player("Player1", new BigDecimal("1000.00"));
        purchase = new Purchase(share, 1);
    }

    @Test
    @DisplayName("Constructor: valid values create purchase correctly")
    void constructor_validValues_createsPurchaseCorrectly() {
        assertEquals(share, purchase.getShare());
        assertEquals(1, purchase.getWeek());
        assertTrue(purchase.getCalculator() instanceof PurchaseCalculator);
    }

    @Test
    @DisplayName("commit: valid purchase withdraws money, adds share and archived transaction")
    void commit_validPurchase_updatesPlayerCorrectly() {
        purchase.commit(player);

        assertTrue(player.getPortfolio().containsShare(share));
        assertTrue(player.getTransactionArchive()
                .getAllTransactions()
                .contains(purchase));
        assertTrue(purchase.isCommitted());

        BigDecimal startingMoney = new BigDecimal("1000.00");
        BigDecimal totalCost = purchase.getCalculator().calculateTotal();

        System.out.println("Shares in portfolio: " + player.getPortfolio().getShares());
        System.out.println("Size: " + player.getPortfolio().getShares().size());
        System.out.println("containsShare(share): " + player.getPortfolio().containsShare(share));
        System.out.println("list contains share: " + player.getPortfolio().getShares().contains(share));
        System.out.println("same reference as first element: " + (player.getPortfolio().getShares().get(0) == share));

        BigDecimal expectedMoney = startingMoney.subtract(totalCost);
        assertEquals(0, expectedMoney.compareTo(player.getMoney()));
    }

    @Test
    @DisplayName("commit: purchase with insufficient funds throws exception")
    void commit_insufficientFunds_throwsException() {
        Player player = new Player("player", new BigDecimal("50.00"));

        assertThrows(IllegalStateException.class, () ->
                purchase.commit(player));
    }

    @Test
    @DisplayName("commit: null player throws exception")
    void commit_nullPlayer_throwsException() {
        assertThrows(NullPointerException.class, () -> purchase.commit(null));
    }

    @Test
    @DisplayName("commit: committing same purchase twice throws exception")
    void commit_samePurchaseTwice_throwsException() {
        purchase.commit(player);

        assertThrows(IllegalStateException.class, () ->
                purchase.commit(player));
    }

}
