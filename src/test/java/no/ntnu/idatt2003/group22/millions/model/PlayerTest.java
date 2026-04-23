package no.ntnu.idatt2003.group22.millions.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.group22.millions.transaction.Purchase;

public class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp(){
        player = new Player("player1", new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Constructor: valid values create player correctly")
    void constructor_validValues_createsPlayerCorrectly(){
        assertEquals("player1", player.getName());
        assertEquals(0,new BigDecimal("100.00").compareTo(player.getStartingMoney()));
        assertEquals(0,new BigDecimal("100.00").compareTo(player.getMoney()));
        assertNotNull(player.getPortfolio());
        assertNotNull(player.getTransactionArchive());
    }

    @Test
    @DisplayName("Constructor: null name throws exception")
    void constructor_nullName_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> 
        new Player(null, new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Constructor: null starting money throws exception")
    void constructor_nullStartingMoney_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> 
        new Player("Player1", null));
    }
    

    @Test
    @DisplayName("Constructor: negative starting money throws exception")
    void constructor_negativeStartingMoney_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> 
        new Player("Player1", new BigDecimal("-5.00")));
    }

    @Test
    @DisplayName("addMoney: poritive amount increases money")
    void addMoney_positiveAmount_increasesMoney(){
        player.addMoney(new BigDecimal("500.00"));

        assertEquals(new BigDecimal("600.00"), player.getMoney());
    }

    @Test
    @DisplayName("addMoney: zero amount is allowed")
    void addMoney_zeroAmount_keepsMoneyUnchanged(){
        player.addMoney(BigDecimal.ZERO);

        assertEquals(new BigDecimal("100.00"), player.getMoney());
    }

    @Test
    @DisplayName("addMoney: null amount throws exception")
    void addMoney_nullAmount_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> 
        player.addMoney(null));
    }

    @Test
    @DisplayName("addMoney: negative amount throws exception")
    void addMoney_negativeAmount_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> 
        player.addMoney(new BigDecimal("-5.00")));
    }

    @Test
    @DisplayName("withdrawMoney: valid amount decreases money")
    void withdrawMoney_validAmount_deacreasesMoney(){
        player.withdrawMoney(new BigDecimal("50.00"));

        assertEquals(new BigDecimal("50.00"), player.getMoney());
    }

    @Test
    @DisplayName("withdrawMoney: zero amount is allowed")
    void withdrawMoney_zeroAmount_keepsMoneyUnchanged(){
        player.withdrawMoney(BigDecimal.ZERO);

        assertEquals(new BigDecimal("100.00"), player.getMoney());
    }

    @Test
    @DisplayName("withdrawMoney: null amount throws exception")
    void withdrawMoney_nullAmount_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> 
        player.withdrawMoney(null));
    }

    @Test
    @DisplayName("withdrawMoney: negative amount throws exception")
    void withdrawMoney_negativeAmount_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> 
        player.withdrawMoney(new BigDecimal("-5.00")));
    }

    @Test
    @DisplayName("withdrawMoney: amount greater than balance throws exception")
    void withdrawMoney_amountGreaterThan_throwsException(){
        assertThrows(IllegalStateException.class, () -> 
        player.withdrawMoney(new BigDecimal("500.00")));
    }

    @Test
    @DisplayName("getNetWorth: returns money plus portfolio value")
    void getNetWorth_returnsMoneyPlusPortfolioValue() {
        Stock stock = new Stock("AAPL", "Apple", new BigDecimal("100.00"));
        Share share = new Share(stock, new BigDecimal("2"), new BigDecimal("90.00"));
        player.getPortfolio().addShare(share);

        // sale total for 2 shares at 100 with 1% commission and tax on profit
        assertEquals(0, new BigDecimal("292.60").compareTo(player.getNetWorth()));
    }

    @Test
    @DisplayName("getStatus: returns novice for new player")
    void getStatus_newPlayer_returnsNovice() {
        assertEquals("NOVICE", player.getStatus());
    }

    @Test
    @DisplayName("getStatus: returns investor after 10 active weeks and 20 percent gain")
    void getStatus_investorRequirements_returnsInvestor() {
        player.addMoney(new BigDecimal("50.00")); // now player has 150

        Stock stock = new Stock("AAPL", "Apple", new BigDecimal("10.00"));
        Share share = new Share(stock, BigDecimal.ONE, new BigDecimal("10.00"));

        for (int week = 1; week <= 10; week++) {
            Purchase purchase = new Purchase(share, week);
            player.getTransactionArchive().add(purchase);
        }

        assertEquals("INVESTOR", player.getStatus());
    }

    @Test
    @DisplayName("getStatus: returns speculator after 20 active weeks and doubled net worth")
    void getStatus_speculatorRequirements_returnsSpeculator() {
        player.addMoney(new BigDecimal("120.00")); // now player has 220

        Stock stock = new Stock("AAPL", "Apple", new BigDecimal("10.00"));
        Share share = new Share(stock, BigDecimal.ONE, new BigDecimal("10.00"));

        for (int week = 1; week <= 20; week++) {
            Purchase purchase = new Purchase(share, week);
            player.getTransactionArchive().add(purchase);
        }

        assertEquals("SPECULATOR", player.getStatus());
    }
    
}

