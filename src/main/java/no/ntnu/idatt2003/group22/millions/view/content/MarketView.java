package no.ntnu.idatt2003.group22.millions.view.content;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.group22.millions.model.Stock;
import java.util.List;

public class MarketView {
    private final VBox root;
    private final Label titleLabel;
    private final TextField searchField;
    private final Button searchButton;
    private final ListView<Stock> marketListView;
    private final TextField quantityText;
    private final Button buyButton;


    public MarketView(){
        this.root = new VBox();
        this.titleLabel = new Label("Market");
        this.searchField = new TextField();
        this.searchButton = new Button("Search");
        this.marketListView = new ListView<>();
        this.quantityText = new TextField();
        this.buyButton = new Button("Buy");


        configureLayout();
    }

    private void configureLayout(){
        root.setSpacing(20);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_LEFT);
        root.getChildren().addAll(titleLabel, searchField, searchButton, marketListView, buyButton, quantityText);

    }

    public VBox getRoot(){
        return root;
    }

    public void updateMarket(List<Stock> stocks){
        marketListView.getItems().clear();
        marketListView.getItems().addAll(stocks);
    }

    public Stock getSelectedStock() {
        return marketListView.getSelectionModel().getSelectedItem();
    }

    public String getSearchText(){
        return searchField.getText();
    }

    public Button getSearchButton(){
        return searchButton;
    }

    public String getQuantityText(){
        return quantityText.getText();
    }

    public Button getBuyButton(){
        return buyButton;
    }

}
