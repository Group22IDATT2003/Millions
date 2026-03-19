package no.ntnu.idatt2003.group22.millions;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ShareTest {

    private Stock stock;

    @BeforeEach
    void setUp(){
        stock = new Stock("AAPL", "Apple", new BigDecimal("100"));
    }

    @Test
    @DisplayName("Constructor: valid values create share correctly")
    void constructor_validValues_createsShareCorrectly(){
        Share share = new Share(stock, new BigDecimal("10"), new BigDecimal("90.00"));

        assertEquals(stock, share.getStock());
        assertEquals(new BigDecimal("10"), share.getQuantity());
        assertEquals(new BigDecimal("90.00"), share.getPurchasePrice());
    }

    @Test
    @DisplayName("Constructor: null stock throws exception")
    void constructor_nullStock_throwsException(){
        assertThrows(NullPointerException.class, () -> 
        new Share(null, new BigDecimal("10"), new BigDecimal("90.00")));

    }

    @Test
    @DisplayName("Constructor: null quantity throws exception")
    void constructor_nullQuantity_throesException() {
        assertThrows(NullPointerException.class, () -> 
        new Share(stock, null, new BigDecimal("90.00")));
    }

    @Test
    @DisplayName("Constructor: null purchase price throws exception")
    void constructor_nullPurchase_throwsException(){
        assertThrows(NullPointerException.class, () -> 
        new Share(stock, new BigDecimal("10"), null));
    }

    @Test
    @DisplayName("Constructor: zero quantity price throws exception")
    void constructor_zeroQuantity_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> 
        new Share(stock, BigDecimal.ZERO, new BigDecimal("90.00")));
    }

    @Test
    @DisplayName("Constructor: negative quantity throws exception")
    void constructor_negativeQuantity_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> 
        new Share(stock, new BigDecimal("-1"), new BigDecimal("95.00")));
    }

    @Test
    @DisplayName("Constructor: zero purchase price throws exception")
    void constructor_zeroPurchasePrice_throesException(){
        assertThrows(IllegalArgumentException.class, () -> 
        new Share(stock, new BigDecimal("10"), BigDecimal.ZERO));
    }

    @Test
    @DisplayName("Constructor: negative purchase peice throws exception")
    void constructor_negativePurchasePrice_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> 
        new Share(stock, new BigDecimal("10"), new BigDecimal("-2.00")));
    }





    
}
