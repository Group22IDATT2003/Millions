package no.ntnu.idatt2003.group22.millions.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import no.ntnu.idatt2003.group22.millions.io.StockFileHandler;
import no.ntnu.idatt2003.group22.millions.market.Exchange;
import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;
import no.ntnu.idatt2003.group22.millions.view.MainView;
import no.ntnu.idatt2003.group22.millions.view.dialog.BuyPopupView;

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

    private void configureActions() {
        mainView.getMarketView().getSearchField().textProperty().addListener(
                (obs, oldValue, newValue) -> handleSearch(newValue)
        );

        mainView.getTopBarView().getNewGameButton().setOnAction(e -> handleStartNewGame());

        mainView.getSidebarView().getDashboardButton().setOnAction(
                e -> showDashboard()
        );

        mainView.getSidebarView().getMarketButton().setOnAction(
                e -> mainView.showMarket()
        );

        mainView.getSidebarView().getPortfolioButton().setOnAction(
                e -> mainView.showPortfolio()
        );

        mainView.getSidebarView().getTransactionButton().setOnAction(
                e -> mainView.showTransactions()
        );

        mainView.getSidebarView().getExitGameButton().setOnAction(
                e -> System.exit(0)
        );

        mainView.getMarketView().getAdvanceButton().setOnAction(
                e -> handleAdvanceWeek());

    }

    private void handleAdvanceWeek() {
        if (exchange == null || player == null) {
            showMessage("Advance", "Start a new game first.");
            return;
        }

        player.setPreviousNetWorth(player.getNetWorth());
        exchange.advance();
        refreshAllViews();
    }

    public void startNewGame(String name, BigDecimal startingMoney, Path path) {
        StockFileHandler handler = new StockFileHandler();

        try {
            List<Stock> stocks = handler.readStocksFromFile(path);

            this.player = new Player(name, startingMoney);
            this.exchange = new Exchange("NASDAQ", stocks);

            refreshAllViews();
            showMessage("Game started", "Welcome " + player.getName());

        } catch (IOException e) {
            showMessage("Error", "Could not load stock file: " + path);
        }

    }

    private void handleBuy(Stock stock) {
        if (exchange == null || player == null) {
            showMessage("Buy", "Start a new game before buying.");
            return;
        }

        BuyPopupView popup = new BuyPopupView(stock, (selectedStock, quantity) -> {
            try {
                player.setPreviousNetWorth(player.getNetWorth());

                exchange.buy(
                        selectedStock.getSymbol(),
                        BigDecimal.valueOf(quantity),
                        player
                );

                refreshAllViews();
                showMessage("Buy", "Purchased " + quantity + " of " + selectedStock.getSymbol());

            } catch (Exception e) {
                showMessage("Buy", e.getMessage());
            }
        });

        popup.show();

    }

    private void handleSell(Share share) {
        if (exchange == null || player == null) {
            showMessage("Sell", "Start a new game before sell.");
            return;
        }

        try {
            exchange.sell(share, player);

            refreshAllViews();
            showMessage("Sell", "Sold share: " + share.getSymbol());

        } catch (Exception e) {
            showMessage("Sell", e.getMessage());
        }
    }

    private void handleSearch(String searchText) {
        if (exchange == null) {
            return;
        }

        List<Stock> results = exchange.findStocks(searchText);
        showMarket(results);
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Filters", "*.csv")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user home")));

        File selectedFile = fileChooser.showOpenDialog(
                mainView.getRoot().getScene().getWindow()
        );

        if (selectedFile == null) {
            showMessage("New Game", "You must choose a stock file.");
            return;
        }

        startNewGame(name, startingMoney, selectedFile.toPath());

    }

    private void refreshAllViews() {
        player.setPreviousNetWorth(player.getNetWorth());

        showTopBar(
                player.getName(),
                exchange.getWeek(),
                player.getMoney(),
                player.getNetWorth(),
                player.getStatus()
        );

        showMarket(exchange.getStocks());
        showPortfolio(player.getPortfolio().getShares());
        showTransaction(player.getTransactionArchive().getAllTransactions());

        mainView.getDashboardView().updateMiniSummary(
                player.getPortfolio().getShares()
        );

        mainView.getDashboardView().updateNetWorthGraph(
                exchange.getWeek(),
                player.getNetWorth()
        );

        mainView.getDashboardView().updateMovers(
                exchange.getGainers(2),
                exchange.getLosers(2)
        );

        mainView.getDashboardView().updateNetWorth(
                player.getNetWorth(),
                player.getChange()
        );

        mainView.getMarketView().updateNetWorth(
                player.getNetWorth(),
                player.getChange()
        );


    }

    public void showDashboard() {
        mainView.showDashboard();

        mainView.getDashboardView().updateDashboardView(
                player.getMoney(),
                player.getPortfolio().getNetWorth(),
                player.getNetWorth()
        );


    }

    public void showMarket(List<Stock> stocks) {
        mainView.getMarketView().updateMarket(stocks, this::handleBuy);
    }

    public void showPortfolio(List<Share> shares) {
        mainView.getPortfolioView().updatePortfolio(shares, this::handleSell);
    }

    public void showTransaction(List<Transaction> transactions) {
        mainView.getTransactionView().updateTransaction(transactions);
    }

    public void showTopBar(String playerName, int week, BigDecimal money,
                           BigDecimal netWorth, String status) {
        mainView.getTopBarView().updatePlayerInfo(playerName, week, money, netWorth, status);
    }

    public void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}











