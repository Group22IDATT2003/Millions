package no.ntnu.idatt2003.group22.millions.view;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class MainView {
    private BorderPane root;
    private MarketView marketView;
    private PortfolioView portfolioView;
    private TopBarView topBarView;
    private TransactionView transactionView;
    private MessageView messageView;

    public MainView() {
        root = new BorderPane();

        marketView = new MarketView();
        portfolioView = new PortfolioView();
        topBarView = new TopBarView();
        transactionView = new TransactionView();
        messageView = new MessageView();

        configureLayout();
    }

    private void configureLayout() {
        root.setTop(topBarView.getRoot());
        root.setLeft(marketView.getRoot());
        root.setRight(portfolioView.getRoot());
        root.setCenter(messageView.getRoot());
        root.setBottom(transactionView.getRoot());

    }

    public Parent getRoot() {
        return root;
    }


}
