package no.ntnu.idatt2003.group22.millions;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the reading and writing of stock data from and to a file.
 * This class provides methods to read stock information from a CSV file
 * and to write stock information back to a CSV file.
 * It uses the Java File API to read and write files.
 */
public class StockFileHandler {

    /**
     * Reads stock information from a CSV file and returns a list of Stock objects.
     * @param "src/main/resources/sp500.csv" the name of the CSV file to read from.
     * @return a list of Stock objects read from the file.
     * @throws IOException if an I/O error occurs while reading the file.
     * @throws IllegalArgumentException if the file is not found.
     */
    public List<Stock> readStocksFromFile(String filename) throws IOException {
        List<Stock> stocks = new ArrayList<>();

        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);

        if (is == null) {
            throw new IllegalArgumentException("File not found: " + filename);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(",");

                String symbol = parts[0].trim();
                String company = parts[1].trim();
                BigDecimal price = new BigDecimal(parts[2].trim());

                stocks.add(new Stock(symbol, company, price));
            }
        }

        return stocks;
    }

    /**
     * Writes a list of Stock objects to a CSV file.
     * @param filename the name of the CSV file to write to.
     * @param stocks the list of Stock objects to write.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public void writeStocksToFile(String filename, List<Stock> stocks) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename))) {

            writer.write("# Ticker,Name,Price");
            writer.newLine();

            for (Stock stock : stocks) {
                writer.write(
                        stock.getSymbol() + "," +
                                stock.getCompany() + "," +
                                stock.getSalesPrice()
                );
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}