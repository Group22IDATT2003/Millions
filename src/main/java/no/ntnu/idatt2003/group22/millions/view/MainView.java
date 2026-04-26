package no.ntnu.idatt2003.group22.millions.view;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import no.ntnu.idatt2003.group22.millions.view.content.MarketView;
import no.ntnu.idatt2003.group22.millions.view.content.PortfolioView;
import no.ntnu.idatt2003.group22.millions.view.content.TransactionView;

public class MainView {
    private BorderPane root;
    private MarketView marketView;
    private PortfolioView portfolioView;
    private TopBarView topBarView;
    private SidebarView sidebarView;
    private TransactionView transactionView;

    public MainView() {
        root = new BorderPane();
        marketView = new MarketView();
        portfolioView = new PortfolioView();
        topBarView = new TopBarView();
        sidebarView = new SidebarView();
        transactionView = new TransactionView();

        configureLayout();
    }

    private void configureLayout() {
        root.setTop(topBarView.getRoot());
        root.setLeft(sidebarView.getRoot());

        showDashboard();

    }

    public void showMarket() {
        root.setCenter(marketView.getRoot());
        sidebarView.setActive(sidebarView.getMarketButton());
    }

    public void showPortfolio() {
        root.setCenter(portfolioView.getRoot());
        sidebarView.setActive(sidebarView.getPortfolioButton());
    }

    public void showTransactions() {
        root.setCenter(transactionView.getRoot());
        sidebarView.setActive(sidebarView.getTransactionButton());
    }

    public Parent getRoot() {
        return root;
    }

    public TopBarView getTopBarView() {
        return topBarView;
    }

    public MarketView getMarketView() {
        return marketView;
    }

    public PortfolioView getPortfolioView() {
        return portfolioView;
    }

    public TransactionView getTransactionView() {
        return transactionView;
    }

    public SidebarView getSidebarView(){return sidebarView;}


}
