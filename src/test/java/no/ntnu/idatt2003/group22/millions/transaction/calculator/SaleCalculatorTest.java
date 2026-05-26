package no.ntnu.idatt2003.group22.millions.transaction.calculator;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SaleCalculatorTest {
    
    private SaleCalculator calculator;

    @BeforeEach
    void setUp() {
        Stock stock = new Stock("AAPL", "Apple", new BigDecimal("100.00"));
        Share share = new Share(stock, new BigDecimal("2"), new BigDecimal("90.00"));
        calculator = new SaleCalculator(share);
    }

    @Test
    @DisplayName(" Constructor throws exception for null share. ")
    void constructor_nullShare_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new SaleCalculator(null));
    }

    @Test
    @DisplayName(" Calculate gross returns correct value. ")
    void calculateGross_returnsCorrectGross() {
        assertEquals(new BigDecimal("200.00"), calculator.calculateGross());
    }

    @Test
    @DisplayName(" Calculate commission returns correct value. ")
    void calculateCommission_returnsCorrectCommission() {
        assertEquals(new BigDecimal("2.00"), calculator.calculateCommission());
    }

    @Test
    @DisplayName(" Verifies that tax is calculated correctly when profit exists. ")
    void calculateTax_returnsCorrectTaxWhenProfitExists() {
        assertEquals(new BigDecimal("5.40"), calculator.calculateTax());
    }

    @Test
    @DisplayName(" Calculate total payout is calculated correctly. ")
    void calculateTotal_returnsCorrectTotal() {
        assertEquals(new BigDecimal("192.60"), calculator.calculateTotal());
    }

    @Test
    @DisplayName(" Verifies that tax is zero when there is no profit. ")
    void calculateTax_returnsZeroWhenNoProfit() {
        Stock stock = new Stock("AAPL", "Apple", new BigDecimal("80.00"));
        Share share = new Share(stock, new BigDecimal("2"), new BigDecimal("90.00"));
        SaleCalculator lowPriceCalculator = new SaleCalculator(share);

        assertEquals(new BigDecimal("0.00"), lowPriceCalculator.calculateTax());
    }
}
