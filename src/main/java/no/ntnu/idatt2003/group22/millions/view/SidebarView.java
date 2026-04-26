package no.ntnu.idatt2003.group22.millions.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SidebarView {
    private final VBox root;

    private final Button dashboardButton;
    private final Button marketButton;
    private final Button portfolioButton;
    private final Button transactionButton;
    private final Button exitGameButton;

    public SidebarView(){
        root = new VBox();

        dashboardButton = new Button(" Dashboard");
        marketButton = new Button(" Market");
        portfolioButton = new Button(" Portfolio");
        transactionButton = new Button(" Transaction");
        exitGameButton = new Button(" Exit Game");

        configureLayout();
    }

    private void configureLayout(){
        root.setSpacing(18);
        root.setPadding(new Insets(32,16,32,16));
        root.setAlignment(Pos.TOP_LEFT);
        root.setPrefWidth(200);
        root.setStyle("""
                -fx-background-color: #2C394F;
                """);

        styleNavButton(dashboardButton);
        styleNavButton(marketButton);
        styleNavButton(portfolioButton);
        styleNavButton(transactionButton);
        styleNavButton(exitGameButton);

        root.getChildren().addAll(
                dashboardButton,
                marketButton,
                portfolioButton,
                transactionButton,
                exitGameButton
        );
    }

    public void setActive(Button activeButton) {
        styleNavButton(dashboardButton);
        styleNavButton(marketButton);
        styleNavButton(portfolioButton);
        styleNavButton(transactionButton);
        styleNavButton(exitGameButton);

        activeButton.setStyle("""
                -fx-background-color: #2C3648;
                -fx-text-fill: #E2E8F0;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-background-radius: 14;
                -fx-padding: 10 14 10 14;
                """);
    }

    public void styleNavButton(Button button){
        button.setPrefWidth(180);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-background-radius: 14;
                -fx-padding: 10 14 10 14;
                """);
    }

    public Button getDashboardButton() {
        return dashboardButton;
    }

    public VBox getRoot() {
        return root;
    }
}
