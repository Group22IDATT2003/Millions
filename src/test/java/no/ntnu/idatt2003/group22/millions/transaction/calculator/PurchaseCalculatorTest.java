package no.ntnu.idatt2003.group22.millions.transaction.calculator;

import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests PurchaseCalculator class
 */
public class PurchaseCalculatorTest {
    private PurchaseCalculator calculator;

    @BeforeEach
    void setUp(){
        Stock stock = new Stock("AAPL", "Apple Incoporations", new BigDecimal("100"));
        Share share = new Share(stock, new BigDecimal("10"), new BigDecimal("100"));
        calculator = new PurchaseCalculator(share);
    }

    @Test
    @DisplayName("calculatingGross: buying_price + amount")
    void calculateGross_validShare_returnsCorrectGross(){
        BigDecimal gross = calculator.calculateGross();
        
        assertEquals(0, new BigDecimal("1000").compareTo(gross));
    }

    @Test
    @DisplayName("calculatingCommision: 0.5% og brutto of purchase")
    void calculateCommission_validShare_returnsCorrectCommision(){
        BigDecimal commission = calculator.calculateCommission();
        
        assertEquals(0, new BigDecimal("5.000").compareTo(commission));
    }

    @Test
    @DisplayName("CalculateTax: tax is 0 by purchase")
    void calculateTax_Purchase_ReturnsZero(){
        BigDecimal tax = calculator.calculateTax();
        
        assertEquals(0, BigDecimal.ZERO.compareTo(tax));
    }

    @Test
    @DisplayName("calculateTotal: brutto + tax + ")
    void calculateTotal_validShare_returnsGrossPlusFees(){
        BigDecimal total = calculator.calculateTotal();
        
        assertEquals(0, new BigDecimal("1005.00").compareTo(total));

    }
    
}
