package no.ntnu.idatt2003.group22.millions.view.content;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.group22.millions.model.Stock;

import java.util.List;
import java.util.function.Consumer;


public class MarketView {
    private final VBox root;
    private final TextField searchField;
    private final VBox stockRows;
    private final Button advanceButton;


    public MarketView(){
        this.root = new VBox();
        this.searchField = new TextField();
        this.stockRows = new VBox(8);
        this.advanceButton = new Button("Advance to next week →");

        configureLayout();
    }

    private void configureLayout(){
        root.setSpacing(24);
        root.setPadding(new Insets(24,32,24,32));
        root.setStyle("-fx-background-color: #1E2330;");


        Label title = new Label("Market");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 34px;");

        HBox topCards = new HBox(24);
        topCards.getChildren().addAll(createNetWorthCard(), createMoversCard());

        configureSearchField();

        VBox tableCard = createStockTable();

        configureAdvanceButton();

        root.getChildren().addAll(
                title,
                topCards,
                searchField,
                tableCard,
                advanceButton
        );

    }

    private void configureSearchField() {
        searchField.setPromptText("Search stock");
        searchField.setMaxWidth(300);
        searchField.setStyle("""
                -fx-background-color: #343D52;
                -fx-text-fill: white;
                -fx-prompt-text-fill: #CBD5E1;
                -fx-background-radius: 18;
                -fx-font-size: 24px;
                -fx-padding: 12 20 12 20;
                """);
    }

    private VBox createNetWorthCard() {
        VBox card = createCard();
        card.setPrefSize(460, 180);

        Label title = new Label("Net worth:");
        Label value = new Label("78 500 NOK");
        Label change = new Label("+3 200 (+4.2%)");

        title.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");
        value.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
        change.setStyle("-fx-text-fill: #6EE75F; -fx-font-size: 18px;");

        card.getChildren().addAll(title, value, change);
        return card;
    }

    private VBox createStockTable() {
        VBox card = createCard();
        card.setSpacing(12);

        HBox header = new HBox(50);
        header.getChildren().addAll(
                createTableHeader("Symbol:"),
                createTableHeader("Name:"),
                createTableHeader("Price:"),
                createTableHeader("Change:"),
                createTableHeader("Action:")
        );

        card.getChildren().addAll(header, stockRows);
        return card;
    }

    private Label createTableHeader(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");
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
                """);
    }
    public void updateMarket(List<Stock> stocks, Consumer<Stock> onBuy){

    }

    public VBox getRoot(){
        return root;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public String getSearchText(){
        return searchField.getText();
    }

    public Button getAdvanceButton() {
        return advanceButton;
    }

}
