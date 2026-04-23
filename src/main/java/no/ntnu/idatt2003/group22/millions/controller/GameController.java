package no.ntnu.idatt2003.group22.millions.controller;


import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import no.ntnu.idatt2003.group22.millions.io.StockFileHandler;
import no.ntnu.idatt2003.group22.millions.market.Exchange;
import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;
import no.ntnu.idatt2003.group22.millions.view.MainView;

import java.nio.file.Path;
import java.io.File;
import java.io.IOException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class GameController {
    private Player player;
    private Exchange exchange;
    private final MainView mainView;

    public GameController(MainView mainView) {

        this.mainView = mainView;
        configureActions();
    }

    public void startNewGame(String name, BigDecimal startingMoney, Path path) {
        StockFileHandler handler = new StockFileHandler();

        try {
            List<Stock> stocks = handler.readStocksFromFile(path);

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
            showMessage("Error", "Could not load stock file: " + path);
        }

    }

    private void configureActions() {
        mainView.getMarketView().getSearchButton().setOnAction(event -> handleSearch());

        mainView.getMarketView().getBuyButton().setOnAction(event -> handleBuy());

        mainView.getPortfolioView().getSellButton().setOnAction(event -> handleSell());

        mainView.getTopBarView().getNewGameButton().setOnAction(event -> handleStartNewGame());

    }

    private void handleBuy() {
        if (exchange == null || player == null) {
            showMessage("Buy", "Start a new game before buy.");
            return;
        }

        Stock selectedStock = mainView.getMarketView().getSelectedStock();
        if (selectedStock == null) {
            showMessage("Buy", "Please select a stock first.");
            return;
        }

        String quantityText = mainView.getMarketView().getQuantityText();
        if (quantityText == null || quantityText.isBlank()) {
            showMessage("Buy", "Please enter a quantity.");
            return;
        }

        try {
            BigDecimal quantity = new BigDecimal(quantityText);

            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                showMessage("Buy", "Quantity must be greater than zero.");
                return;
            }

            exchange.buy(selectedStock.getSymbol(), quantity, player);

            showTopBar(
                    player.getName(),
                    exchange.getWeek(),
                    player.getMoney(),
                    player.getNetWorth(),
                    player.getStatus()
            );
            showPortfolio(player.getPortfolio().getShares());
            showTransaction(player.getTransactionArchive().getAllTransactions());
            showMessage(
                    "Buy",
                    "Purchased " + quantity + " of " + selectedStock.getSymbol()
            );

        } catch (Exception e) {
            showMessage("Buy", e.getMessage());
        }
    }

    private void handleSearch() {
        if (exchange == null) {
            showMessage("Search", "Start a game before search.");
            return;
        }

        String searchText = mainView.getMarketView().getSearchText();
        List<Stock> results = exchange.findStocks(searchText);

        showMarket(results);
        showMessage("Search", "You searched for: " + searchText);

    }

    private void handleSell() {
        if (exchange == null || player == null) {
            showMessage("Sell", "Start a new game before sell.");
            return;
        }

        Share selectedShare = mainView.getPortfolioView().getSelectedShare();
        if (selectedShare == null) {
            showMessage("Sell", "Please select a share first.");
            return;
        }

        try {
            exchange.sell(selectedShare, player);

            showTopBar(
                    player.getName(),
                    exchange.getWeek(),
                    player.getMoney(),
                    player.getNetWorth(),
                    player.getStatus()
            );
            showPortfolio(player.getPortfolio().getShares());
            showTransaction(player.getTransactionArchive().getAllTransactions());
            showMessage("Sell", "Sold share: " + selectedShare.getSymbol());
        } catch (Exception e) {
            showMessage("Sell", e.getMessage());
        }
    }

    private void handleStartNewGame() {

        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("New Game");
        nameDialog.setHeaderText("Start a new game");
        nameDialog.setContentText("Player name: ");

        Optional<String> nameResult = nameDialog.showAndWait();

        if (nameResult.isEmpty() || nameResult.get().isBlank()) {
            showMessage("New Game", "Please enter a player name.");
            return;
        }

        String name = nameResult.get().trim();

        TextInputDialog moneyDialog = new TextInputDialog();
        moneyDialog.setTitle("New Game");
        moneyDialog.setHeaderText("Start a new game");
        moneyDialog.setContentText("Starting money: ");

        Optional<String> moneyResult = moneyDialog.showAndWait();

        if (moneyResult.isEmpty() || moneyResult.get().isBlank()) {
            showMessage("New Game", "Please enter starting money.");
            return;
        }

        BigDecimal startingMoney;

        try {
            startingMoney = new BigDecimal(moneyResult.get().trim());
        } catch (NumberFormatException e) {
            showMessage("New Game", "Starting money must be a valid number.");
            return;
        }

        if (startingMoney.compareTo(BigDecimal.ZERO) <= 0) {
            showMessage("New Game", "Starting money must be greater than zero.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose stock file");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) {
            showMessage("New Game", "You must choose a stock file.");
            return;
        }

        startNewGame(name, startingMoney, selectedFile.toPath());

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

}
