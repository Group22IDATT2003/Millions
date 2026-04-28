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
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;


public class MarketView {
    private final VBox root;
    private final TextField searchField;
    private final VBox stockRows;
    private final Button advanceButton;
    private final Label netWorthLabel = new Label();
    private final Label netWorthValueLabel = new Label();
    private final Label netWorthChangeLabel = new Label();
    private LineChart<Number, Number> netWorthChart;
    private XYChart.Series<Number, Number> netWorthSeries;
    private final Label w1Label = new Label();
    private final Label w2Label = new Label();
    private final Label l1Label = new Label();
    private final Label l2Label = new Label();


    public MarketView() {
        this.root = new VBox();
        this.searchField = new TextField();
        this.stockRows = new VBox(8);
        this.advanceButton = new Button("Advance to next week →");

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(24);
        root.setPadding(new Insets(24, 32, 24, 32));
        root.setStyle("""
                -fx-background-color: #1E2330;
                """);


        Label title = new Label("Market");
        title.setStyle("""
                -fx-text-fill: white; 
                -fx-font-size: 34px;
                """);

        HBox topCards = new HBox(24);
        topCards.getChildren().addAll(createNetWorthCard());

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
        searchField.setMaxWidth(300);
        searchField.setStyle("""
                -fx-background-color: #343D52;
                -fx-text-fill: white;
                -fx-prompt-text-fill: white;
                -fx-background-radius: 18;
                -fx-font-size: 24px;
                -fx-padding: 12 20 12 20;
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
        netWorthLabel.setText(netWorth + " kr");
        netWorthChangeLabel.setText(change + " kr");

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
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 4);
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
            stockRows.getChildren().add(row);
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
