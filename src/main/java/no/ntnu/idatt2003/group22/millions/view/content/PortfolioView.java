package no.ntnu.idatt2003.group22.millions.view.content;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.group22.millions.model.Share;

import java.util.List;
import java.util.function.Consumer;

import static java.awt.SystemColor.text;


public class PortfolioView {
    private final VBox root;
    private final Label titleLabel;
    private final Label portfolioValueLabel;
    private final Label totalNetWorthLabel;

    private final VBox shareRows;


    public PortfolioView() {
        this.root = new VBox();
        this.titleLabel = new Label("Portfolio");
        this.portfolioValueLabel = new Label("Portfolio value: 0 kr ");
        this.totalNetWorthLabel = new Label("Total net worth: 0 kr");

        shareRows = new VBox();

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(24);
        root.setPadding(new Insets(24, 32, 24, 32));
        root.setAlignment(Pos.TOP_LEFT);

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

        VBox summaryCard = new VBox(8);
        summaryCard.getChildren().addAll(portfolioValueLabel, totalNetWorthLabel);
        summaryCard.setPadding(new Insets(20));
        summaryCard.setStyle("""
        -fx-background-color: #343D52;
        -fx-background-radius: 18;
    """);

        shareRows.setSpacing(12);
        shareRows.setPadding(new Insets(12));
        shareRows.setAlignment(Pos.TOP_LEFT);
        shareRows.setStyle("""
                -fx-border-color: #343D52;
                -fx-background-radius: 18;
                -fx-padding: 20
                """);

        root.getChildren().addAll(titleLabel, summaryCard, shareRows);


    }

    public void updatePortfolio(List<Share> shares, Consumer<Share> onSell) {
        shareRows.getChildren().clear();

        HBox header = new HBox(35);
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

        shareRows.getChildren().add(header);

        for (Share share : shares) {
            shareRows.getChildren().add(createShareRow(share, onSell));
        }

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

        Button sellButton = new Button("Sell");
        sellButton.setStyle("""
                -fx-background-color: #C65A3D;
                -fx-text-fill: white;
                -fx-background-radius: 14;
                """);

      sellButton.setOnAction(event -> {
        SellPopupView popup = new SellPopupView(share, (selectedShare, quantityToSell) -> {
            System.out.println("SELL: " + selectedShare.getSymbol() + " qty: " + quantityToSell);
            onSell.accept(selectedShare);
        });
        popup.show();
        });

        row.getChildren().addAll(
                symbol,
                name,
                quantity,
                buyPrice,
                currentPrice,
                sellButton
        );

        return row;
    }
    private Label createCellLabel(String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 14px;
            """);
        return label;
    }

    public VBox getRoot() {
        return root;
    }

}
