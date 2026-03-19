package no.ntnu.idatt2003.group22.millions;

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
    void SetUp(){
        Stock stock = new Stock("AAPL", "Apple Incoporations", new BigDecimal("100"));
        Share share = new Share(stock, new BigDecimal("10"), new BigDecimal("100"));
        calculator = new PurchaseCalculator(share);
    }

    @Test
    @DisplayName("calculatingGross: buying_price + amount")
    void calculateGross_validShare_returnsCorrectGross(){
        BigDecimal gross = calculator.calculateGross();
        assertEquals(new BigDecimal("1000"), gross);
    }

    @Test
    @DisplayName("calculatingCommision: 0.5% og brutto of purchase")
    void calculateCommission_validShare_returnsCorrectCommision(){
        BigDecimal commission = calculator.calculateCommission();
        assertEquals(new BigDecimal("5.000"), commission);
    }

    @Test
    @DisplayName("CalculateTax: tax is 0 by purchase")
    void calculateTax_Purchase_ReturnsZero(){
        BigDecimal tax = calculator.calculateTax();
        assertEquals(new BigDecimal("0.00"), tax);
    }

    @Test
    @DisplayName("calculateTotal: brutto + tax + ")
    void calculateTotal_validShare_returnsGrossPlusFees(){
        BigDecimal total = calculator.calculateTotal();
        assertEquals(new BigDecimal(1005.000), total);

    }
    
}
