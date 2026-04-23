package no.ntnu.idatt2003.group22.millions.io;

import no.ntnu.idatt2003.group22.millions.model.Stock;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;




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
    public List<Stock> readStocksFromFile(Path path) throws IOException {
        if(path == null){
            throw new IllegalArgumentException("path cannot be null");
        }

        List<Stock> stocks = new ArrayList<>();


        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                stocks.add(parseStockLine(line, lineNumber));
            }
        }
        return stocks;
    }

    public void writeStocksToFile(Path path, List<Stock> stocks) throws IOException {
        if(path == null){
            throw new IllegalArgumentException("path cannot be null");
        }
        if(stocks == null){
                throw new IllegalArgumentException("stocks cannot be null");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)){
            for(Stock stock : stocks){
                if(stock == null){
                    throw new IllegalArgumentException("stocks cannot contain null");
                }
                writer.write(
                    stock.getSymbol() + "," +
                    stock.getCompany() + "," +
                    stock.getSalesPrice()
                );
                
                writer.newLine();
            }

        }
    }

    private Stock parseStockLine(String line, int lineNumber){
        String[] parts = line.split(",");

        if(parts.length !=3){
            throw new IllegalArgumentException(
                "Invalid stock data at line " + lineNumber + ": expected 3 fields, got " +parts.length
            );
        }

        String symbol = parts [0].trim();
        String company = parts [1].trim();
        String priceText = parts [2].trim();

        if(symbol.isEmpty()){
            throw new IllegalArgumentException(
                "Invalid stock data at line " + lineNumber + ": symbol cannot be blank"
            );
        }

        if(company.isEmpty()){
            throw new IllegalArgumentException(
                "Invalid stock data at line " + lineNumber + ": company cannot be blank"
            );
        }

        BigDecimal price;
        try{
            price = new BigDecimal(priceText);
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException(
                "Invalid stock data at line " + lineNumber + ": invalid price '" + priceText + "'"
            );
        }

        try {
            return new Stock(symbol, company, price);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid stock data at line " + lineNumber + ": '" + e.getMessage()

            );
        }
    }
}