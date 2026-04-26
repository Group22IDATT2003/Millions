package no.ntnu.idatt2003.group22.millions.view.content;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.group22.millions.model.Share;

import java.util.List;


public class PortfolioView {
    private final VBox root;
    private final Label titleLabel;
    private final ListView<Share> portfolioListView;
    private final Button sellButton;


    public PortfolioView() {
        this.root = new VBox();
        this.titleLabel = new Label("Portfolio");
        this.portfolioListView = new ListView<>();
        this.sellButton = new Button("Sell Shares");

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(20);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_LEFT);
        root.getChildren().addAll(titleLabel, portfolioListView, sellButton);
    }

    public VBox getRoot(){
        return root;
    }

    public void updatePortfolio(List<Share> shares){
        portfolioListView.getItems().clear();
        portfolioListView.getItems().addAll(shares);
    }

    public Share getSelectedShare(){
        return portfolioListView.getSelectionModel().getSelectedItem();
    }

    public Button getSellButton(){
        return sellButton;
    }
}
