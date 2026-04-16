package no.ntnu.idatt2003.group22.millions.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;

import java.math.BigDecimal;

public class TopBarView {
    private final HBox root;
    private final Label playerNameLabel;
    private final Label weekLabel;
    private final Label moneyLabel;
    private final Label netWorthLabel;
    private final Label statusLabel;
    private final Button newGameButton;

    public TopBarView(){
        this.root = new HBox();

        this.playerNameLabel = new Label("Player: ");
        this.weekLabel = new Label("Week: ");
        this.moneyLabel = new Label("Money: ");
        this.netWorthLabel = new Label("Net worth: ");
        this.statusLabel = new Label("Status: ");
        this.newGameButton = new Button("New Game");

        configureLayout();
    }

    private void configureLayout(){
        root.setSpacing(20);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().addAll(
                playerNameLabel,
                weekLabel,
                moneyLabel,
                netWorthLabel,
                statusLabel,
                newGameButton
        );
    }

    public HBox getRoot(){
        return root;
    }

    public void updatePlayerInfo(String playerName, int week, BigDecimal money, BigDecimal netWorth, String status){
        playerNameLabel.setText("Player: " + playerName);
        weekLabel.setText("Week: " + week);
        moneyLabel.setText("Money: " + money);
        netWorthLabel.setText("Net worth: " + netWorth);
        statusLabel.setText("Status: " + status);
    }

    public Button getNewGameButton(){
        return newGameButton;
    }

}
