package no.ntnu.idatt2003.group22.millions.view.content;

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
import no.ntnu.idatt2003.group22.millions.model.Share;


public class SellPopupView {

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.01");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.30");

    private final Stage stage;
    private final Share share;
    private final BiConsumer<Share, Integer> onConfirmSell;

    private int quantity;

    private final Label quantityLabel;
    private final Label grossValueLabel;
    private final Label commisionLabel;
    private final Label taxLabel;
    private final Label totalReceivedLabel;

    public SellPopupView(Share share, BiConsumer<Share, Integer> onConfirmSell){
        this.share = share;
        this.onConfirmSell = onConfirmSell;
        this.stage = new Stage();
        this.quantity = share.getQuantity().intValue();

        this.quantityLabel = new Label();
        this.grossValueLabel = new Label();
        this.commisionLabel = new Label();
        this.taxLabel = new Label();
        this.totalReceivedLabel = new Label();

        configureStage();
    }

    private void configureStage(){
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Sell " + share.getSymbol());

        VBox root = new VBox();
        root.setPrefSize(460, 540);
        root.setStyle("-fx-background-color: #343D52;");

        Label title = new Label("Sell " + share.getSymbol() + " - " + share.getStock().getCompany());
        title.setPadding(new Insets(14, 24, 14, 24));
        title.setMaxWidth(Double.MAX_VALUE);
        title.setStyle("""
                -fx-background-color: #1A2332;
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px
                """);

        VBox content = new VBox(22);
        content.setPadding(new Insets(28, 34, 24, 34));

        content.getChildren().addAll(
            createInfoRow("Current price:", createValueLabel(formatMoney(share.getStock().getSalesPrice()))),
            createInfoRow("Owned quantity:", createValueLabel(String.valueOf(share.getQuantity()))),
            createQuantityRow(),
            createSpacer(26),
            createInfoRow("Gross value:", grossValueLabel),
            createInfoRow("Commision 0.5%:", commisionLabel),
            createInfoRow("Tax:", taxLabel),
            createTotalRow("Total received:", totalReceivedLabel),
            createSpacer(8),
            createButtonRow()
        );

        root.getChildren().addAll(title, content);
        updateValues();

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private HBox createQuantityRow(){
        Label label = createTextLabel("Quantity:");

        Button minusButton = createSmallButton("-");
        Button plusButton = createSmallButton("+");

        quantityLabel.setMinWidth(42);
        quantityLabel.setAlignment(Pos.CENTER);
        quantityLabel.setStyle("""
                -fx-background-color: #566075;
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px;
                """);
    
        minusButton.setOnAction( event -> {
            if(quantity > 1) {
                quantity--;
                updateValues();
            }
        });

        plusButton.setOnAction( event -> {
            if(quantity < share.getQuantity().intValue()) {
                quantity++;
                updateValues();
            }
        });

        HBox counter = new HBox(minusButton, quantityLabel, plusButton);
        counter.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox row = new HBox(label, spacer, counter);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private HBox createInfoRow(String labelText, Label valueLabel){
        Label label = createTextLabel(labelText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox row = new HBox(label, spacer, valueLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private HBox createTotalRow(String labelText, Label valueLabel){
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

    private HBox createButtonRow(){
        Button cancelButton = new Button("Cancel");
        Button sellButton = new Button("Sell");

        cancelButton.setPrefWidth(150);
        sellButton.setPrefWidth(150);

        cancelButton.setStyle("""
                -fx-background-color: #7C8595;
                -fx-text-fill: #FFFFFF;
                -fx-background-radius: 18;
                -fx-font-size: 18px;
                """);

        sellButton.setStyle("""
                -fx-background-color: #D7653F;
                -fx-text-fill: #FFFFFF;
                -fx-background-radius: 18;
                -fx-font-size: 18px;
                """);

        cancelButton.setOnAction(event -> stage.close());

        sellButton.setOnAction(event -> {
            onConfirmSell.accept(share, quantity);
            stage.close();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox row = new HBox(cancelButton, spacer, sellButton);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private Button createSmallButton(String text){
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

    private Label createTextLabel(String text){
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: #FFFFFF;
            -fx-font-size: 18px;
                """);
        return label; 
    }

    private Label createValueLabel(String text){
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: #FFFFFF;
            -fx-font-size: 18px;
                """);
        return label; 
    }

    private Region createSpacer(double height){
        Region spacer = new Region();
        spacer.setPrefHeight(height);
        return spacer;
    }

    private void updateValues(){
        BigDecimal gross = share.getStock().getSalesPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal commision = gross.multiply(COMMISSION_RATE);
        
        BigDecimal purchaseCost = share.getPurchasePrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal profit = gross.subtract(commision).subtract(purchaseCost);
        BigDecimal tax = profit.compareTo(BigDecimal.ZERO) > 0
        ? profit.multiply(TAX_RATE)
        : BigDecimal.ZERO;

        BigDecimal total = gross.subtract(commision).subtract(tax);

        quantityLabel.setText(String.valueOf(quantity));
        grossValueLabel.setText(formatMoney(gross));
        commisionLabel.setText(formatMoney(commision));
        taxLabel.setText(formatMoney(tax));
        totalReceivedLabel.setText(formatMoney(total));
    }

    private String formatMoney(BigDecimal value){
        return value.setScale(2, RoundingMode.HALF_UP) + " kr";
    }

    public void show(){
        stage.showAndWait();
    }


    
}

