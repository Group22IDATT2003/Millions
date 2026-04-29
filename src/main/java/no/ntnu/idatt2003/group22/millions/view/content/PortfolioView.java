package no.ntnu.idatt2003.group22.millions.view.content;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.group22.millions.model.Share;
import javafx.scene.control.ScrollPane;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Consumer;



public class PortfolioView {
    private final VBox root;
    private final Label titleLabel;
    private final Label portfolioValueLabel;
    private final Label totalNetWorthLabel;
    private final Label moneyValueLabel;
    private final Button sellAllButton;

    private final VBox shareRows;


    public PortfolioView() {
        this.root = new VBox();
        this.titleLabel = new Label("Portfolio");
        this.portfolioValueLabel = new Label("Portfolio value: 0 kr ");
        this.totalNetWorthLabel = new Label("Total net worth: 0 kr");
        this.moneyValueLabel = new Label("Money: 0 kr");
        this.sellAllButton = new Button("Sell all");

        shareRows = new VBox();

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(24);
        root.setPadding(new Insets(24, 32, 24, 32));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("""
                -fx-background-color: #1A2332;
                """);

        titleLabel.setStyle("""
               -fx-text-fill: white; 
               -fx-font-size: 34px;
               """);

        portfolioValueLabel.setStyle("""
                -fx-text-fill: white; 
                -fx-font-size: 22px;
                """);

        totalNetWorthLabel.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 28px;
                -fx-font-weight: bold;
                """);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox summaryCard = new VBox(8);
        summaryCard.getChildren().addAll(portfolioValueLabel, totalNetWorthLabel);
        summaryCard.setPadding(new Insets(20));
        summaryCard.setAlignment(Pos.BOTTOM_LEFT);
        summaryCard.setStyle("""
        -fx-background-color: #1A2332;
        -fx-background-radius: 18;
    """);

        VBox moneyCard = new VBox(8);
        moneyCard.getChildren().addAll(moneyValueLabel);
        moneyCard.setPadding(new Insets(20));
        moneyCard.setAlignment(Pos.TOP_RIGHT);
        moneyCard.setStyle("""
        -fx-background-color: #1A2332;
        -fx-background-radius: 18;
        
    """);

        moneyValueLabel.setStyle("""
                -fx-text-fill: #16A34A;
                -fx-font-size: 28px;
                """);

        shareRows.setSpacing(12);
        shareRows.setPadding(new Insets(12));
        shareRows.setAlignment(Pos.TOP_LEFT);
        shareRows.setStyle("""
                -fx-background-color: #343D52;
                -fx-background-radius: 18;
                -fx-padding: 20
                """);

        sellAllButton.setText("Sell all");
        sellAllButton.setStyle("""
        -fx-background-color: #C65A3D;
        -fx-text-fill: white;
        -fx-background-radius: 18;
        -fx-font-size: 16px;
        -fx-padding: 10 22 10 22;
        """);

        root.getChildren().addAll(titleLabel, moneyCard, createHoldingsCard(), sellAllButton, spacer, summaryCard);


    }

    public VBox createHoldingsCard() {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16));
        card.setStyle("""
                -fx-background-color: #343D52;
                -fx-background-radius: 18;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 8, 0, 0, 4);
                """);

        HBox header = new HBox(50);
        header.getChildren().addAll(
                createHeaderLabel("Symbol:"),
                createHeaderLabel("Name:"),
                createHeaderLabel("Qty:"),
                createHeaderLabel("Buy price:"),
                createHeaderLabel("Current price:"),
                createHeaderLabel("Value:"),
                createHeaderLabel("Change:"),
                createHeaderLabel("Action:")
        );

        ScrollPane scrollPane = new ScrollPane(shareRows);
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

    public void updatePortfolio(List<Share> shares, Consumer<Share> onSell) {
        shareRows.getChildren().clear();

        for (Share share : shares) {
            shareRows.getChildren().add(createShareRow(share, onSell));
        }
    }

    public void updateMoney(BigDecimal money) {
        moneyValueLabel.setText("Money: " + money + " kr");
    }

    public void updateSummary(BigDecimal portfolioValue, BigDecimal netWorth) {
        portfolioValueLabel.setText("Portfolio value: " + portfolioValue + " kr");
        totalNetWorthLabel.setText("Total net worth: " + netWorth + " kr");
    }

    private Label createHeaderLabel(String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            """);
        return label;
    }

    private HBox createShareRow(Share share, Consumer<Share> onSell) {
        HBox row = new HBox(35);
        row.setAlignment(Pos.CENTER_LEFT);

        Label symbol = createCellLabel(share.getSymbol());
        Label name = createCellLabel(share.getStock().getCompany());
        Label quantity = createCellLabel(share.getQuantity().toString());
        Label buyPrice = createCellLabel(share.getPurchasePrice() + " kr");
        Label currentPrice = createCellLabel(share.getStock().getSalesPrice() + " kr");
        Label value = createCellLabel(share.getCurrentValue() + " kr");

        BigDecimal changeValue = share.getPriceChangePercentage()
                .setScale(2, RoundingMode.HALF_UP);


        Label change = createCellLabel(formatPercent(changeValue));

        if (changeValue.compareTo(BigDecimal.ZERO) >= 0) {
            change.setStyle("-fx-text-fill: #6EE75F; -fx-font-size: 14px;");
        } else {
            change.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 14px;");
        }

        Button sellButton = new Button("Sell");
        sellButton.setStyle("""
                -fx-background-color: #C65A3D;
                -fx-text-fill: white;
                -fx-background-radius: 14;
                """);

        sellButton.setOnAction(event -> onSell.accept(share));

        symbol.setPrefWidth(80);
        name.setPrefWidth(90);
        quantity.setPrefWidth(40);
        buyPrice.setPrefWidth(100);
        currentPrice.setPrefWidth(90);
        value.setPrefWidth(70);
        change.setPrefWidth(80);
        sellButton.setPrefWidth(80);

        row.getChildren().addAll(symbol, name, quantity, buyPrice, currentPrice, value, change, sellButton);
        return row;

    }

    private String formatPercent(BigDecimal value) {
        BigDecimal rounded = value.setScale(2, RoundingMode.HALF_UP);
        String prefix = rounded.compareTo(BigDecimal.ZERO) > 0 ? "+" : "";
        return prefix + rounded + " %";
    }

    private Label createCellLabel (String text){
        Label label = new Label(text);
        label.setStyle("""
                    -fx-text-fill: white;
                    -fx-font-size: 14px;
                    """);
        return label;
    }

    public Button getSellAllButton() {
        return sellAllButton;
    }

    public VBox getRoot () {
        return root;
    }

}
