package no.ntnu.idatt2003.group22.millions.view.dialog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiConsumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatt2003.group22.millions.model.Stock;


public class BuyPopupView {

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.005");

    private final Stage stage;
    private final Stock stock;
    private final BiConsumer<Stock, Integer> onConfirmBuy;

    private int quantity = 1;

    private final Label quantityLabel;
    private final Label grossValueLabel;
    private final Label commisionLabel;
    private final Label taxLabel;
    private final Label totalCostLabel;

    public BuyPopupView(Stock stock, BiConsumer<Stock, Integer> onConfirmBuy) {
        this.stock = stock;
        this.onConfirmBuy = onConfirmBuy;
        this.stage = new Stage();

        this.quantityLabel = new Label();
        this.grossValueLabel = new Label();
        grossValueLabel.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px;
                -fx-font-weight: bold;
                """);
        this.commisionLabel = new Label();
        commisionLabel.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px;
                -fx-font-weight: bold;
                """);
        this.taxLabel = new Label();
        taxLabel.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px;
                -fx-font-weight: bold;
                """);
        this.totalCostLabel = new Label();

        configureStage();
    }

    private void configureStage() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Buy " + stock.getSymbol());

        VBox root = new VBox();
        root.setPrefSize(460, 540);
        root.setStyle("-fx-background-color: #343D52;");

        Label title = new Label("Buy " + stock.getSymbol() + " - " + stock.getCompany());
        title.setPadding(new Insets(14, 24, 14, 24));
        title.setMaxWidth(Double.MAX_VALUE);
        title.setStyle("""
                -fx-background-color: #1A2332;
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px
                """);

        VBox content = new VBox(22);
        content.setPadding(new Insets(28, 34, 24, 34));

        Label PriceLabel = createValueLabel(formatMoney(stock.getSalesPrice()));

        content.getChildren().addAll(
                createInfoRow("CurrentPrice;", PriceLabel),
                createQuantityRow(),
                createSpacer(28),
                createInfoRow("Gross value:", grossValueLabel),
                createInfoRow("Commision 0.5%:", commisionLabel),
                createInfoRow("Tax:", taxLabel),
                createTotalRow("Total cost:", totalCostLabel),
                createSpacer(8),
                createButtonRow()
        );

        root.getChildren().addAll(title, content);
        updateValues();

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private HBox createQuantityRow() {
        Label label = createTextLabel("Quantity");

        Button minusButton = createSmallButton("-");
        Button plusButton = createSmallButton("+");

        quantityLabel.setMinWidth(42);
        quantityLabel.setAlignment(Pos.CENTER);
        quantityLabel.setStyle("""
                -fx-background-color: #566075;
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px;
                """);

        minusButton.setOnAction(event -> {
            if (quantity > 1) {
                quantity--;
                updateValues();
            }
        });

        plusButton.setOnAction(event -> {
            quantity++;
            updateValues();
        });

        HBox counter = new HBox(minusButton, quantityLabel, plusButton);
        counter.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox row = new HBox(label, spacer, counter);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private HBox createInfoRow(String labelText, Label valueLabel) {
        Label label = createTextLabel(labelText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox row = new HBox(label, spacer, valueLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private HBox createTotalRow(String labelText, Label valueLabel) {
        Label label = createTextLabel(labelText);
        label.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                """);

        valueLabel.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                """);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox row = new HBox(label, spacer, valueLabel);
        row.setPadding(new Insets(10, 0, 10, 0));
        row.setStyle("-fx-text-fill: #40506E");
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private HBox createButtonRow() {
        Button cancelButton = new Button("Cancel");
        Button buyButton = new Button("Buy");

        cancelButton.setPrefWidth(150);
        buyButton.setPrefWidth(150);

        cancelButton.setStyle("""
                -fx-background-color: #7C8595;
                -fx-text-fill: #FFFFFF;
                -fx-background-radius: 18;
                -fx-font-size: 18px;
                """);

        buyButton.setStyle("""
                -fx-background-color: #36BDF2;
                -fx-text-fill: #FFFFFF;
                -fx-background-radius: 18;
                -fx-font-size: 18px;
                """);

        cancelButton.setOnAction(event -> stage.close());

        buyButton.setOnAction(event -> {
            onConfirmBuy.accept(stock, quantity);
            stage.close();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox row = new HBox(cancelButton, spacer, buyButton);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private Button createSmallButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(34, 24);
        button.setStyle("""
                -fx-background-color: #566075;
                -fx-text-fill: #FFFFFF;
                -fx-border-color: #2A3142;
                -fx-font-size: 14px;
                """);

        return button;
    }

    private Label createTextLabel(String text) {
        Label label = new Label(text);
        label.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px;
                """);
        return label;
    }

    private Label createValueLabel(String text) {
        Label label = new Label(text);
        label.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px;
                """);
        return label;
    }

    private Region createSpacer(double height) {
        Region spacer = new Region();
        spacer.setPrefHeight(height);
        return spacer;
    }

    private void updateValues() {
        BigDecimal gross = stock.getSalesPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal commision = gross.multiply(COMMISSION_RATE);
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal total = gross.add(commision).add(tax);

        quantityLabel.setText(String.valueOf(quantity));
        grossValueLabel.setText(formatMoney(gross));
        commisionLabel.setText(formatMoney(commision));
        taxLabel.setText(formatMoney(tax));
        totalCostLabel.setText(formatMoney(total));
    }

    private String formatMoney(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP) + " kr";
    }

    public void show() {
        stage.showAndWait();
    }


}
