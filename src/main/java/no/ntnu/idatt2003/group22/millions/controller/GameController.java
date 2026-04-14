package no.ntnu.idatt2003.group22.millions.controller;


import javafx.scene.control.Button;
import no.ntnu.idatt2003.group22.millions.io.StockFileHandler;
import no.ntnu.idatt2003.group22.millions.market.Exchange;
import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;
import no.ntnu.idatt2003.group22.millions.view.MainView;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class GameController {
    private Player player;
    private Exchange exchange;
    private final MainView mainView;

    public GameController(MainView mainView) {

        this.mainView = mainView;
        configureActions();
    }

    public void startNewGame(String name, BigDecimal startingMoney, String filename) {
        StockFileHandler handler = new StockFileHandler();

        try {
            List<Stock> stocks = handler.readStocksFromFile(filename);

            this.player = new Player(name, startingMoney);
            this.exchange = new Exchange("NASDAQ", stocks);

            showTopBar(player.getName(),
                    1,
                    player.getMoney(),
                    player.getNetWorth(),
                    player.getStatus());
            showMarket(stocks);
            showPortfolio(player.getPortfolio().getShares());
            showTransaction(player.getTransactionArchive().getAllTransactions());
            showMessage("Game started", "Welcome " + player.getName());

        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error", "Could not load stock file: " + filename);
        }

    }

    private void configureActions() {
        mainView.getMarketView().getSearchButton().setOnAction(event -> {
            String searchText = mainView.getMarketView().getSearchText();
            showMessage("Search", "You searched for: " + searchText);
        });

        mainView.getMarketView().getBuyButton().setOnAction(event -> {
            Stock selectedStock = mainView.getMarketView().getSelectedStock();
            if (selectedStock == null) {
                showMessage("Buy", "Please select a stock first.");
            } else {
                showMessage("Buy", "Selected stock: " + selectedStock.getSymbol());
            }
        });

        mainView.getPortfolioView().getSellButton().setOnAction(event -> {
            Share selectedShare = mainView.getPortfolioView().getSelectedShare();
            if (selectedShare == null) {
                showMessage("Sell", "Please select a share first.");
            } else {
                showMessage("Sell", "Selected share: " + selectedShare.getSymbol());
            }
        });
    }

    public void showMessage(String title, String message) {
        mainView.getMessageView().updateMessageInfo(title, message);
    }

    public void showMarket(List<Stock> stocks) {
        mainView.getMarketView().updateMarket(stocks);
    }

    public void showPortfolio(List<Share> shares) {
        mainView.getPortfolioView().updatePortfolio(shares);
    }

    public void showTransaction(List<Transaction> transactions) {
        mainView.getTransactionView().updateTransaction(transactions);
    }

    public void showTopBar(String playerName, int week, BigDecimal money, BigDecimal netWorth, String status) {
        mainView.getTopBarView().updatePlayerInfo(playerName, week, money, netWorth, status);
    }

    public String buy() {
//finner aksjen
// oppretter andel/transaksjon
// gjennomfører kjøp
//returnerer eller lagrer en melding til GUI
    }

    public String sell() {
//finner andelen
//oppretter salg
//gjennomfører salget
    }

    public String searchStock() {
//kaller exchange.findStocks(...)
    }

    public String sellAllAndExit() {

    }
}
