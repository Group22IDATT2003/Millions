package no.ntnu.idatt2003.group22.millions.view.content;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.group22.millions.model.Stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;


public class MarketView {
    private final VBox root;
    private final TextField searchField;
    private final VBox stockRows;
    private final Button advanceButton;
    private final Label netWorthLabel;
    private final Label netWorthValueLabel;
    private final Label netWorthChangeLabel;
    private LineChart<Number, Number> netWorthChart;
    private XYChart.Series<Number, Number> netWorthSeries;
    private final Label w1Label;
    private final Label w2Label;
    private final Label l1Label;
    private final Label l2Label;
    private final Label stockStatsTitleLabel;
    private final Label stockSymbolLabel;
    private final Label highestPriceLabel;
    private final Label lowestPriceLabel;
    private final Label currentPriceLabel;
    private final Label stockChangeLabel;


    public MarketView() {
        this.root = new VBox();
        this.searchField = new TextField();
        this.stockRows = new VBox(8);
        this.advanceButton = new Button("Advance to next week →");
        this.netWorthLabel = new Label();
        this.netWorthValueLabel = new Label();
        this.netWorthChangeLabel = new Label();
        this.w1Label = new Label();
        this.w2Label = new Label();
        this.l1Label = new Label();
        this.l2Label = new Label();
        this.stockStatsTitleLabel = new Label("Stock statistics");
        this.stockSymbolLabel = new Label("Symbol: ");
        this.highestPriceLabel = new Label("Highest price: ");
        this.lowestPriceLabel = new Label("Lowest price: ");
        this.currentPriceLabel = new Label("Current price: ");
        this.stockChangeLabel = new Label("Change: ");

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(24);
        root.setPadding(new Insets(24, 32, 24, 32));
        root.setStyle("""
                -fx-background-color: #1A2332;
                """);


        Label title = new Label("Market");
        title.setStyle("""
                -fx-text-fill: white; 
                -fx-font-size: 34px;
                """);

        HBox topCards = new HBox(24);
        topCards.getChildren().addAll(createNetWorthCard(), createStockStatsCard());

        configureSearchField();

        VBox tableCard = createStockTable();

        configureAdvanceButton();

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        HBox bottom = new HBox(advanceButton);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(20, 0, 0, 0));

        root.getChildren().addAll(
                title,
                topCards,
                searchField,
                tableCard,
                spacer,
                advanceButton
        );

    }

    private void configureSearchField() {
        searchField.setPromptText("Search stock");
        searchField.setPrefHeight(64);
        searchField.setMaxWidth(360);
        searchField.setStyle("""
                -fx-background-color: #343D52;
                -fx-text-fill: white;
                -fx-prompt-text-fill: #AAB2C5;
                -fx-background-radius: 22;
                -fx-font-size: 20px;
                -fx-padding: 14 20 14 20;
                -fx-border-color: #1A2332;
                    -fx-border-width: 2;
                    -fx-border-radius: 22;
                """);
    }

    private HBox createNetWorthCard() {
        HBox card = new HBox();
        VBox textBox = new VBox();
        card.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(textBox, Priority.ALWAYS);
        card.setPadding(new Insets(20));
        card.setSpacing(20);
        card.setStyle("""
                -fx-background-color: #343D52;
                -fx-background-radius: 22;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 4);
                """);
        card.setPrefSize(460, 180);

        Label title = new Label("Net worth:");
        Label value = new Label("78 500 NOK");
        Label change = new Label("+3 200 (+4.2%)");

        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
        value.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        change.setStyle("-fx-text-fill: #6EE75F; -fx-font-size: 16px;");


        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Weeks");
        yAxis.setLabel("Net Worth");

        xAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");
        yAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");


        netWorthChart = new LineChart<>(xAxis, yAxis);
        netWorthSeries = new XYChart.Series<>();
        netWorthChart.getData().add(netWorthSeries);


        netWorthChart.setPrefSize(150, 160);
        netWorthChart.setMaxSize(150, 160);
        netWorthChart.setLegendVisible(false);
        netWorthChart.setCreateSymbols(false);
        netWorthChart.setHorizontalGridLinesVisible(false);
        netWorthChart.setVerticalGridLinesVisible(false);

        netWorthChart.setStyle("""
                -fx-background-color: #2C394F;
                -fx-padding: 0;
                """);


        textBox.getChildren().addAll(title, netWorthValueLabel, netWorthChangeLabel);
        netWorthChart.getData().add(netWorthSeries);

        card.getChildren().addAll(textBox, netWorthChart);

        return card;
    }


    public void updateNetWorth(BigDecimal netWorth, BigDecimal change) {
        netWorthValueLabel.setText(netWorth + " NOK");
        netWorthValueLabel.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 18px;
                """);
        netWorthChangeLabel.setText(change + " NOK");

        if (change.compareTo(BigDecimal.ZERO) >= 0) {
            netWorthChangeLabel.setStyle("""
                    -fx-text-fill: #6EE75F;
                    -fx-font-size: 18px;""");
        } else {
            netWorthChangeLabel.setStyle("""
                    -fx-text-fill: #EF4444; 
                    -fx-font-size: 18px;
                    """);
        }
    }

    public void updateNetWorthGraph(int week, BigDecimal netWorth) {
        netWorthSeries.getData().add(
                new XYChart.Data<>(week, netWorth.doubleValue())
        );
    }

    private VBox createStockStatsCard() {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setStyle("""
                -fx-background-color: #343D52;
                -fx-background-radius: 22;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 4);
                """);

        stockStatsTitleLabel.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 18px;
                """);

        stockSymbolLabel.setStyle("-fx-text-fill: white;" +
                "-fx-font-size: 16px;");
        highestPriceLabel.setStyle("-fx-text-fill: white;" +
                "-fx-font-size: 16px;");
        lowestPriceLabel.setStyle("-fx-text-fill: white;" +
                "-fx-font-size: 16px;");
        currentPriceLabel.setStyle("-fx-text-fill: white;" +
                "-fx-font-size: 16px;");
        stockChangeLabel.setStyle("-fx-text-fill: white;" +
                "-fx-font-size: 16px;");



        card.getChildren().addAll(
                stockStatsTitleLabel,
                stockSymbolLabel,
                highestPriceLabel,
                lowestPriceLabel,
                currentPriceLabel,
                stockChangeLabel
        );

        return card;
    }

    public void updateStockStats(Stock stock) {

        stockSymbolLabel.setText("Symbol: " + stock.getSymbol());
        highestPriceLabel.setText("Highest price: " + stock.getHighestPrice() + " kr");
        lowestPriceLabel.setText("Lowest price: " + stock.getLowestPrice() + " kr");
        currentPriceLabel.setText("Current price: " + stock.getSalesPrice() + " kr");
        stockChangeLabel.setText("Change: " + stock.getLatestPriceChange() + " %");

        if (stock.getLatestPriceChange().compareTo(BigDecimal.ZERO) >= 0) {
            stockChangeLabel.setStyle("-fx-text-fill: #6EE75F; -fx-font-size: 14px;");
        } else {
            stockChangeLabel.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 14px;");
        }

    }

    private VBox createStockTable() {
        VBox card = createCard();
        card.setSpacing(12);
        card.setPrefWidth(850);
        card.setMinWidth(850);

        HBox header = new HBox(125);
        header.getChildren().addAll(
                createTableHeader("Symbol:"),
                createTableHeader("Name:"),
                createTableHeader("Price:"),
                createTableHeader("Change:"),
                createTableHeader("Action:")
        );

        ScrollPane scrollPane = new ScrollPane(stockRows);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(360);
        scrollPane.setStyle("""
                -fx-background: #2C394F;
                -fx-text-color: white;
                -fx-font-size: 14px;
                """);

        card.getChildren().addAll(header, scrollPane);
        return card;
    }

    private Label createTableHeader(String text) {
        Label label = new Label(text);
        label.setStyle("""
                -fx-text-fill: white; 
                -fx-font-size: 22px;
                """);
        return label;
    }

    private Label createCellLabel(String text) {
        Label label = new Label(text);
        label.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 16px;
                """);
        return label;
    }

    private VBox createCard() {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setStyle("""
                -fx-background-color: #343D52;
                -fx-background-radius: 22;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 4);
                """);
        return card;
    }

    private void configureAdvanceButton() {
        advanceButton.setStyle("""
                -fx-background-color: #6FAFE7;
                -fx-text-fill: white;
                -fx-background-radius: 24;
                -fx-font-size: 24px;
                -fx-padding: 12 28 12 28;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.45), 18, 0.3, 0, 8);
                """);
    }


    public void updateMarket(List<Stock> stocks, Consumer<Stock> onBuy) {
        stockRows.getChildren().clear();


        for (Stock stock : stocks) {
            HBox row = new HBox(90);
            row.setAlignment(Pos.CENTER_LEFT);

            Label symbol = createCellLabel(stock.getSymbol());
            Label name = createCellLabel(stock.getCompany());
            Label price = createCellLabel(stock.getSalesPrice() + " kr");
            Label change = createCellLabel(stock.getLatestPriceChange() + " %");

            Button buyButton = new Button("Buy");

            symbol.setPrefWidth(100);
            name.setPrefWidth(120);
            price.setPrefWidth(90);
            change.setPrefWidth(100);
            buyButton.setPrefWidth(80);

            buyButton.setStyle("""
                    -fx-background-color: #36BDF2;
                    -fx-text-fill: white;
                    -fx-background-radius: 14;
                    """);

            buyButton.setOnAction(event -> onBuy.accept(stock));

            row.getChildren().addAll(symbol, name, price, change, buyButton);
            row.setOnMouseClicked(e -> updateStockStats(stock));

            stockRows.getChildren().add(row);

            if (!stocks.isEmpty()) {
                updateStockStats(stocks.get(0));
            }
        }
    }


    public VBox getRoot() {
        return root;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public Button getAdvanceButton() {
        return advanceButton;
    }

}
