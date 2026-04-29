package no.ntnu.idatt2003.group22.millions.view.content;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.transaction.Purchase;
import no.ntnu.idatt2003.group22.millions.transaction.Sale;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;

import java.util.List;


public class TransactionView {
    private final VBox root;
    private final Label titleLabel;
    private final VBox transactionRows;
    private final TextField searchField;

    public TransactionView() {
        this.root = new VBox();
        this.titleLabel = new Label("Transaction");
        this.transactionRows = new VBox();
        this.searchField = new TextField();

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(24);
        root.setPadding(new Insets(24, 32, 24, 32));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: #1A2332;");

        titleLabel.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 34px;
                """);

        searchField.setPromptText("Search/filter");
        searchField.setMaxWidth(300);
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

        transactionRows.setSpacing(12);
        transactionRows.setPadding(new Insets(16));
        transactionRows.setStyle("""
                -fx-background-color: #343D52
                -fx-background-radius: 18;
                """);




        root.getChildren().addAll(titleLabel, searchField, createTransactionCard());


    }

    private VBox createTransactionCard() {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16));
        card.setStyle("""
            -fx-background-color: #343D52;
            -fx-background-radius: 18;
            """);

        HBox header = new HBox(80);
        header.getChildren().addAll(
                createHeader("Week"),
                createHeader("Type"),
                createHeader("Symbol"),
                createHeader("Qty"),
                createHeader("Gross"),
                createHeader("Fee"),
                createHeader("Tax"),
                createHeader("Total")
        );

        ScrollPane scrollPane = new ScrollPane(transactionRows);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(360);
        scrollPane.setStyle("""
            -fx-background: transparent;
            -fx-background-color: transparent;
            -fx-border-color: transparent;
            """);

        card.getChildren().addAll(header, scrollPane);
        return card;
    }

    public void updateTransaction(List<Transaction> transactions) {
        transactionRows.getChildren().clear();

        for (Transaction transaction : transactions) {
            transactionRows.getChildren().add(createRow(transaction));
        }
    }

    private HBox createRow(Transaction transaction) {
        HBox row = new HBox(30);
        row.setAlignment(Pos.CENTER_LEFT);

        Share share = transaction.getShare();

        Label week = createCell(String.valueOf(transaction.getWeek()));
        Label type = createCell(transaction.getClass().getSimpleName());
        Label symbol = createCell(share.getSymbol());
        Label quantity = createCell(share.getQuantity().toString());
        Label gross = createCell(transaction.calculateGross() + " kr");
        Label fee = createCell(transaction.calculateCommission() + " kr");
        Label tax = createCell(transaction.calculateTax() + " kr");
        Label total = createCell(transaction.calculateTotal() + " kr");

        if (transaction instanceof Sale) {
            type.setStyle("-fx-text-fill: #4CAF50;");
            total.setStyle("-fx-text-fill: #4CAF50;");
        } else if (transaction instanceof Purchase) {
            type.setStyle("-fx-text-fill: #E57373;");
            total.setStyle("-fx-text-fill: #E57373;");
        }

        week.setPrefWidth(100);
        type.setPrefWidth(100);
        symbol.setPrefWidth(100);
        quantity.setPrefWidth(100);
        gross.setPrefWidth(100);
        fee.setPrefWidth(100);
        tax.setPrefWidth(100);
        total.setPrefWidth(100);

        row.getChildren().addAll(week, type, symbol, quantity, gross, fee, tax, total);
        return row;
    }

    private Label createHeader(String text) {
        Label label = new Label(text);
        label.setStyle("""
                    -fx-text-fill: white;
                    -fx-font-size: 14px;
                    -fx-font-weight: bold;
                """);
        return label;
    }

    private Label createCell(String text) {
        Label label = new Label(text);
        label.setStyle("""
                    -fx-text-fill: #D0D5E0;
                    -fx-font-size: 14px;
                """);
        return label;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public VBox getRoot() {
        return root;
    }
}
