package no.ntnu.idatt2003.group22.millions;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import no.ntnu.idatt2003.group22.millions.io.StockFileHandler;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StockFileHandlerTest {
    @TempDir
    Path tempDir;

    private final StockFileHandler handler = new StockFileHandler();

    @Test
    void readStocksFromFile_validFile_returnsStocks() throws IOException {
        Path file = tempDir.resolve("stocks.csv");
        Files.writeString(file,
                "# Comment\n" +
                        "\n" +
                        "AAPL,Apple,100.50\n" +
                        "TSLA,Tesla,200.00\n");

        List<Stock> stocks = handler.readStocksFromFile(file);

        assertEquals(2, stocks.size());
        assertEquals("AAPL", stocks.get(0).getSymbol());
        assertEquals("Apple", stocks.get(0).getCompany());
        assertEquals(new BigDecimal("100.50"), stocks.get(0).getSalesPrice());
    }

    @Test
    void readStocksFromFile_nullPath_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.readStocksFromFile(null));
    }

    @Test
    void readStocksFromFile_invalidFieldCount_throwsException() throws IOException {
        Path file = tempDir.resolve("invalid.csv");
        Files.writeString(file, "AAPL,Apple\n");

        assertThrows(IllegalArgumentException.class,
                () -> handler.readStocksFromFile(file));
    }

    @Test
    void readStocksFromFile_blankSymbol_throwsException() throws IOException {
        Path file = tempDir.resolve("invalid.csv");
        Files.writeString(file, ",Apple,100.00\n");

        assertThrows(IllegalArgumentException.class,
                () -> handler.readStocksFromFile(file));
    }

    @Test
    void readStocksFromFile_blankCompany_throwsException() throws IOException {
        Path file = tempDir.resolve("invalid.csv");
        Files.writeString(file, "AAPL,,100.00\n");

        assertThrows(IllegalArgumentException.class,
                () -> handler.readStocksFromFile(file));
    }

    @Test
    void readStocksFromFile_invalidPrice_throwsException() throws IOException {
        Path file = tempDir.resolve("invalid.csv");
        Files.writeString(file, "AAPL,Apple,abc\n");

        assertThrows(IllegalArgumentException.class,
                () -> handler.readStocksFromFile(file));
    }

    @Test
    void writeStocksToFile_validInput_writesFile() throws IOException {
        Path file = tempDir.resolve("out.csv");
        List<Stock> stocks = List.of(
                new Stock("AAPL", "Apple", new BigDecimal("100.00")),
                new Stock("TSLA", "Tesla", new BigDecimal("200.00"))
        );

        handler.writeStocksToFile(file, stocks);

        String content = Files.readString(file);
        assertTrue(content.contains("AAPL,Apple,100.00"));
        assertTrue(content.contains("TSLA,Tesla,200.00"));
    }

    @Test
    void writeStocksToFile_nullPath_throwsException() {
        List<Stock> stocks = List.of(new Stock("AAPL", "Apple", new BigDecimal("100.00")));

        assertThrows(IllegalArgumentException.class,
                () -> handler.writeStocksToFile(null, stocks));
    }

    @Test
    void writeStocksToFile_nullStocks_throwsException() {
        Path file = tempDir.resolve("out.csv");

        assertThrows(IllegalArgumentException.class,
                () -> handler.writeStocksToFile(file, null));
    }

    @Test
    void writeStocksToFile_listContainingNull_throwsException() {
        Path file = tempDir.resolve("out.csv");
        List<Stock> stocks = java.util.Arrays.asList(
            new Stock("AAPL", "Apple", new BigDecimal("100.00")), null
    );

    assertThrows(IllegalArgumentException.class, 
        () -> handler.writeStocksToFile(file, stocks));


    }
}
