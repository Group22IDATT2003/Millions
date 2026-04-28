package no.ntnu.idatt2003.group22.millions.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.math.BigDecimal;
import java.util.Objects;

public class TopBarView {
    private final HBox root;
    private final Label playerNameLabel;
    private final Label weekLabel;
    private final Label moneyLabel;
    private final Label netWorthLabel;
    private final Label statusLabel;
    private final Button newGameButton;

    public TopBarView() {
        this.root = new HBox();

        this.playerNameLabel = new Label("Player: ");
        this.weekLabel = new Label("Week: ");
        this.moneyLabel = new Label("Money: ");
        this.netWorthLabel = new Label("Net worth: ");
        this.statusLabel = new Label("Status: ");
        this.newGameButton = new Button("New Game");

        configureLayout();

    }

    private void configureLayout() {
        root.setSpacing(24);
        root.setPadding(new Insets(14, 28, 14, 28));
        root.setAlignment(Pos.CENTER_LEFT);
        root.setStyle("""
                -fx-background-color: #1A2332;
                """);

        ImageView logo = new ImageView(
                new Image(Objects.requireNonNull(
                        getClass().getResource("/Images/MillionLogo.png")
                ).toExternalForm()));
        logo.setFitHeight(40);
        logo.setPreserveRatio(true);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        styleInfoLabel(playerNameLabel);
        styleInfoLabel(weekLabel);
        styleInfoLabel(netWorthLabel);
        styleMoneyLabel(moneyLabel);
        styleStatusLabel(statusLabel);

        newGameButton.setStyle("""
                -fx-background-color: #38BDF8;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 16;
                -fx-padding: 8 18 8 18;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 8, 0, 0, 4);
                """);

        root.getChildren().addAll(
                logo,
                spacer,
                playerNameLabel,
                weekLabel,
                moneyLabel,
                netWorthLabel,
                statusLabel,
                newGameButton
        );
    }

    private void styleInfoLabel(Label label) {
        label.setStyle("""
                -fx-text-fill: white;
                 -fx-font-size: 12px;
                 -fx-font-weight: bold;
                """);
    }


    private void styleMoneyLabel(Label label) {
        label.setStyle("""
                -fx-text-fill: #16A34A;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                """);
    }


    private void styleStatusLabel(Label label) {
        label.setStyle("""
                -fx-text-fill: white;
                -fx-padding: 4 12 4 12;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                """);
    }


    public HBox getRoot() {
        return root;
    }

    public void updatePlayerInfo(String playerName, int week, BigDecimal money, BigDecimal netWorth, String status) {
        playerNameLabel.setText("Player: " + playerName);
        weekLabel.setText("Week: " + week);
        moneyLabel.setText("Money: " + money);
        netWorthLabel.setText("Net worth: " + netWorth);
        statusLabel.setText("Status: " + status);
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

}
