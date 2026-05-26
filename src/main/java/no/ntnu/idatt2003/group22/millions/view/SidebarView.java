package no.ntnu.idatt2003.group22.millions.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SidebarView {
    private final VBox root;

    private final Button dashboardButton;
    private final Button marketButton;
    private final Button portfolioButton;
    private final Button transactionButton;
    private final Button exitGameButton;

    public SidebarView() {
        root = new VBox();

        dashboardButton = new Button(" Dashboard");
        marketButton = new Button(" Market");
        portfolioButton = new Button(" Portfolio");
        transactionButton = new Button(" Transaction");
        exitGameButton = new Button(" Exit Game");

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(18);
        root.setPadding(new Insets(32, 16, 32, 16));
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

        Region spacerTop = new Region();
        Region spacerBottom = new Region();

        VBox.setVgrow(spacerTop, Priority.ALWAYS);
        VBox.setVgrow(spacerBottom, Priority.ALWAYS);

        root.getChildren().addAll(
                dashboardButton,
                spacerTop,

                portfolioButton,
                transactionButton,
                marketButton,

                spacerBottom,
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

    public void styleNavButton(Button button) {
        button.setPrefWidth(180);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 16px;
                -fx-background-radius: 14;
                -fx-padding: 10 14 10 14;
                """);
    }

    private ImageView createIcon(String path) {
        ImageView icon = new ImageView(
                new Image(getClass().getResourceAsStream(path))
        );
        icon.setFitWidth(24);
        icon.setFitHeight(24);
        icon.setPreserveRatio(true);
        return icon;
    }


    public Button getDashboardButton() {
        dashboardButton.setGraphicTextGap(12);
        dashboardButton.setGraphic(createIcon("/images/dashboardLogo.png"));
        dashboardButton.setPrefWidth(220);
        dashboardButton.setMinWidth(220);
        dashboardButton.setMaxWidth(220);
        dashboardButton.setAlignment(Pos.CENTER_LEFT);
        dashboardButton.setGraphicTextGap(18);
        return dashboardButton;

    }

    public Button getMarketButton() {
        marketButton.setGraphicTextGap(12);
        marketButton.setGraphic(createIcon("/Images/marketLogo.png"));
        marketButton.setPrefWidth(220);
        marketButton.setMinWidth(220);
        marketButton.setMaxWidth(220);
        marketButton.setAlignment(Pos.CENTER_LEFT);
        marketButton.setGraphicTextGap(18);
        return marketButton;
    }

    public Button getPortfolioButton() {
        portfolioButton.setGraphicTextGap(12);
        portfolioButton.setGraphic(createIcon("/Images/portfolioLogo.png"));
        portfolioButton.setPrefWidth(220);
        portfolioButton.setMinWidth(220);
        portfolioButton.setMaxWidth(220);
        portfolioButton.setAlignment(Pos.CENTER_LEFT);
        portfolioButton.setGraphicTextGap(18);
        return portfolioButton;
    }

    public Button getExitGameButton() {
        exitGameButton.setGraphicTextGap(12);
        exitGameButton.setGraphic(createIcon("/Images/ExitGameLogo.png"));
        exitGameButton.setPrefWidth(220);
        exitGameButton.setMinWidth(220);
        exitGameButton.setMaxWidth(220);
        exitGameButton.setAlignment(Pos.CENTER_LEFT);
        exitGameButton.setGraphicTextGap(18);
        exitGameButton.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #E2E8F0;
                -fx-font-size: 16px;
                -fx-padding: 10 14 10 14;
                """);
        return exitGameButton;
    }

    public Button getTransactionButton() {
        transactionButton.setGraphicTextGap(12);

        ImageView icon = createIcon("/Images/transactionLogo.png");
        icon.setFitWidth(38);
        icon.setFitHeight(38);

        transactionButton.setGraphic(icon);
        transactionButton.setPrefWidth(220);
        transactionButton.setMinWidth(220);
        transactionButton.setMaxWidth(220);
        transactionButton.setAlignment(Pos.CENTER_LEFT);
        transactionButton.setGraphicTextGap(18);
        return transactionButton;
    }

    public VBox getRoot() {
        return root;
    }
}
