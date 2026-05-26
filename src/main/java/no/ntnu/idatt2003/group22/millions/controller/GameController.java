package no.ntnu.idatt2003.group22.millions.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

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
import no.ntnu.idatt2003.group22.millions.view.dialog.SellPopupView;
import no.ntnu.idatt2003.group22.millions.view.dialog.TransactionReceiptView;

/**
 * Controller for handling game logic and UI interaction
 */
public class GameController {
    private Player player;
    private Exchange exchange;
    private final MainView mainView;

    /**
     * Creates a game controller
     * 
     * @param mainView the main application view
     */
    public GameController(MainView mainView) {

        this.mainView = mainView;
        configureActions();
    }

    /**
     * Configures UI event handlers
     * 
     */
    private void configureActions() {
        mainView.getMarketView().getSearchField().textProperty().addListener(
                (obs, oldValue, newValue) -> handleSearch(newValue)
        );

        mainView.getTransactionView().getSearchField().textProperty().addListener(
                (obs, oldValue, newValue) -> handleTransactionSearch(newValue)
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

        mainView.getPortfolioView().getSellAllButton().setOnAction(
                e -> handleSellAll()
        );

    }

    /**
     * Advances the game to the next week
     */
    private void handleAdvanceWeek() {
        if (exchange == null || player == null) {
            showMessage("Advance", "Start a new game first.");
            return;
        }

        player.setPreviousNetWorth(player.getNetWorth());
        exchange.advance();
    }

    /**
     * Starts a new game
     * 
     * @param name the player name
     * @param startingMoney the starting balance
     * @param path the stock file path
     */
    public void startNewGame(String name, BigDecimal startingMoney, Path path) {
        StockFileHandler handler = new StockFileHandler();

        try {
            List<Stock> stocks = handler.readStocksFromFile(path);

            this.player = new Player(name, startingMoney);
            
            this.exchange = new Exchange("NASDAQ", stocks);
            exchange.addObserver(this::refreshAllViews);

            refreshAllViews();
            showMessage("Game started", "Welcome " + player.getName());

        } catch (IOException e) {
            showMessage("Error", "Could not load stock file: " + path);
        }

    }

    /**
     * Handles stock purchases
     * 
     * @param stock the stock to buy
     */
    private void handleBuy(Stock stock) {
        if (exchange == null || player == null) {
            showMessage("Buy", "Start a new game before buying.");
            return;
        }

        BuyPopupView popup = new BuyPopupView(stock, (selectedStock, quantity) -> {
            try {
                player.setPreviousNetWorth(player.getNetWorth());

                Transaction transaction = exchange.buy(
                        selectedStock.getSymbol(),
                        BigDecimal.valueOf(quantity),
                        player
                );

                showTransactionReceipt(transaction);

            } catch (Exception e) {
                showMessage("Buy", e.getMessage());
            }
        });

        popup.show();

    }

    /**
     * Handles selling shares
     * 
     * @param share the share to sell
     */
    private void handleSell(Share share) {
        if (exchange == null || player == null) {
            showMessage("Sell", "Start a new game before selling.");
            return;
        }

        SellPopupView popup = new SellPopupView(share, (selectedShare, quantityToSell) -> {
            try {
                player.setPreviousNetWorth(player.getNetWorth());

                Transaction transaction = exchange.sell(selectedShare, BigDecimal.valueOf(quantityToSell), player);

                showTransactionReceipt(transaction);

            } catch (Exception e) {
                showMessage("Sell", e.getMessage());
            }
        });
        popup.show();
    }

    /**
     * Handles market search input
     * 
     * @param searchText the search text
     */
    private void handleSearch(String searchText) {
        if (exchange == null) {
            return;
        }

        List<Stock> results = exchange.findStocks(searchText);
        showMarket(results);
    }

    /**
     * Handle transaction search input
     * 
     * @param searchText the search text
     */
    private void handleTransactionSearch(String searchText) {
        if (player == null) {
            return;
        }

        List<Transaction> results = player.getTransactionArchive().getAllTransactions().stream()
                .filter(t -> t.getShare().getSymbol().toLowerCase().contains(searchText.toLowerCase()))
                .toList();

        showTransaction(results);
    }

    /**
     * Opens dialogs and starts a new game.
     */
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
        fileChooser.setTitle("Choose stock CSV file");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV files", "*.csv")
        );

        File selectedFile = fileChooser.showOpenDialog(
                mainView.getRoot().getScene().getWindow()
        );

        if (selectedFile == null) {
            showMessage("New Game", "You must choose a stock file.");
            return;
        }

        startNewGame(name, startingMoney, selectedFile.toPath());

    }

    /**
     * Refreshes all UI views with updated data.
     */
    private void refreshAllViews() {
        if(player == null || exchange == null){
            return;
        }

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

        mainView.getMarketView().updateNetWorthGraph(
                exchange.getWeek(),
                player.getNetWorth()
        );

        mainView.getPortfolioView().updateSummary(
                player.getPortfolio().getNetWorth(),
                player.getNetWorth()
        );

        mainView.getPortfolioView().updateMoney(player.getMoney());
    }

    /**
     * Dislpays the dashboard views
     */
    public void showDashboard() {
        mainView.showDashboard();

        mainView.getDashboardView().updateDashboardView(
                player.getMoney(),
                player.getPortfolio().getNetWorth(),
                player.getNetWorth()
        );
    }

    /**
     * Displays stocks in the market view
     * 
     * @param stocks the stocks to display
     */
    public void showMarket(List<Stock> stocks) {
        mainView.getMarketView().updateMarket(stocks, this::handleBuy);
    }

    /**
     * Displays shares in the portfolio view 
     * 
     * @param shares the shares to display
     */
    public void showPortfolio(List<Share> shares) {
        mainView.getPortfolioView().updatePortfolio(shares, this::handleSell);
    }

    /**
     * Displays transactions in the transaction view
     * 
     * @param transactions the transactions to display
     */
    public void showTransaction(List<Transaction> transactions) {
        mainView.getTransactionView().updateTransaction(transactions);
    }

    /**
     * Updates the top bar with player information
     * 
     * @param playerName the player name
     * @param week the current week
     * @param money the players balance
     * @param netWorth the players net worth
     * @param status the players status
     */
    public void showTopBar(String playerName, int week, BigDecimal money, BigDecimal netWorth, String status) {
        mainView.getTopBarView().updatePlayerInfo(playerName, week, money, netWorth, status);
    }

    /**
     * Shows an information message dialog
     * 
     * @param title the dialog title
     * @param message the dialog message
     */
    public void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a transaction receipt dialog
     * 
     * @param transaction the completed transaction
     */
    private void showTransactionReceipt(Transaction transaction) {
        TransactionReceiptView receipt = new TransactionReceiptView(transaction);
        receipt.show();
    }

    /**
     * Sells all shares in the players portfolio
     */
    private void handleSellAll() {
        if (exchange == null || player == null) {
            showMessage("Sell all", "Start a new game first.");
            return;
        }

    try {
        player.setPreviousNetWorth(player.getNetWorth());

        List<Share> shares = List.copyOf(player.getPortfolio().getShares());

        for (Share share : shares) {
            Transaction transaction = exchange.sell(share, player);

            showTransactionReceipt(transaction); 
        }

    } catch (Exception e) {
        showMessage("Sell all", e.getMessage());
    }

    }

}











