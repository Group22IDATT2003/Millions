import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester PurchaseCalculator.
 * Fokus: korrekt beregning av bruttoverdi, kurtasje og total kostnad ved kjøp.
 * Dette er forretningskritisk fordi feil her gir feil saldo og ødelegger spillets logikk.
 */
class PurchaseCalculatorTest {

    private PurchaseCalculator calculator;

    @BeforeEach
    void setUp() {
        // Arrange (felles)
        Stock stock = new Stock("AAPL", "Apple Inc", new BigDecimal("100"));
        Share share = new Share(stock, new BigDecimal("10"), new BigDecimal("100"));
        calculator = new PurchaseCalculator(share);
    }

    @Test
    @DisplayName("calculateGross: kjøpspris * kvantitet")
    void calculateGross_validShare_returnsCorrectGross() {
        // Act
        BigDecimal gross = calculator.calculateGross();

        // Assert
        assertEquals(new BigDecimal("1000"), gross);
    }

    @Test
    @DisplayName("calculateCommission: 0.5% av brutto ved kjøp")
    void calculateCommission_validShare_returnsCorrectCommission() {
        // Act
        BigDecimal commission = calculator.calculateCommission();

        // Assert
        assertEquals(new BigDecimal("5.000"), commission);
    }

    @Test
    @DisplayName("calculateTax: skatt er 0 ved kjøp")
    void calculateTax_purchase_returnsZero() {
        // Act
        BigDecimal tax = calculator.calculateTax();

        // Assert
        assertEquals(BigDecimal.ZERO, tax);
    }

    @Test
    @DisplayName("calculateTotal: brutto + kurtasje + skatt")
    void calculateTotal_validShare_returnsGrossPlusFees() {
        // Act
        BigDecimal total = calculator.calculateTotal();

        // Assert
        assertEquals(new BigDecimal("1005.000"), total);
    }
}
