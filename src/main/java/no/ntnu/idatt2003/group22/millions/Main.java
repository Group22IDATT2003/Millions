package no.ntnu.idatt2003.group22.millions;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String [] args) throws IOException {
        StockFileHandler handler = new StockFileHandler();
        List<Stock> stocks = handler.readStocksFromFile("sp500.csv");

        Exchange exchange = new Exchange("NASDAQ", stocks);

        System.out.println("Loaded stocks: " + stocks.size());
    }
}
