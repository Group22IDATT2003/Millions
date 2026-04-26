package no.ntnu.idatt2003.group22.millions.view.content;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;


public class DashboardView {
    private final VBox root;

    private final Label totalNetWorthLabel;
    private final Label moneyLabel;
    private final Label portfolioValueLabel;

    public DashboardView() {
        this.root = new VBox();

        this.totalNetWorthLabel = new Label("Total net worth: 0kr");
        this.moneyLabel = new Label("Money: 0kr");
        this.portfolioValueLabel = new Label("Portfolio value: 0kr");

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(24);
        root.setPadding(new Insets(24, 32, 24, 32));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: #1E2330;");


        Label title = new Label("Dashboard");
        title.setStyle("""
                -fx-text-fill: white; 
                -fx-font-size: 34px;
                """);

        HBox summaryCards = new HBox(24);
        summaryCards.getChildren().addAll(
                createSummaryCards("Total net worth", totalNetWorthLabel),
                createSummaryCards("Money", moneyLabel),
                createSummaryCards("Portfolio value", portfolioValueLabel)
        );

        HBox mainCards = new HBox(24);
        mainCards.getChildren().addAll(
                createNetWorthCard(),
                createMoversCard()
        );

        root.getChildren().addAll(title, summaryCards, mainCards);
    }





        private VBox createSummaryCard (String title, Label valueLabel){
            VBox card = createCard();
            card.setPrefWidth(220);

            Label titleLabel = new Label(title);
            titleLabel.setStyle("""
                    -fx-text-fill: white;
                    -fx-font-size: 14px;
                    """);
            valueLabel.setStyle("""
                    -fx-text-fill: white;
                    -fx-font-size: 22px;
                    -fx-font-weight: bold;
                    """);


            card.getChildren().addAll(titleLabel, valueLabel);
            return card;
        }

        private VBox createNetWorthCard () {
            VBox card = createCard();
            card.setPrefSize(460, 280);

            Label title = new Label("Net worth:");
            Label value = new Label("78 500 NOK");
            Label change = new Label("+3 200 (+4.2%)");

            title.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");
            value.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
            change.setStyle("-fx-text-fill: #6EE75F; -fx-font-size: 18px;");

            card.getChildren().addAll(title, value, change);
            return card;
        }

    private VBox createMoversCard() {
        VBox card = createCard();
        card.setPrefSize(300, 220);

        Label winners = new Label("Weekly winners:");
        Label w1 = new Label("+NVDA  +8.2%");
        Label w2 = new Label("+AAPL  +4.1%");
        Label losers = new Label("Weekly losers:");
        Label l1 = new Label("-TSLA  -3.1%");
        Label l2 = new Label("-META  -2.4%");

        winners.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");
        losers.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");
        w1.setStyle("-fx-text-fill: #6EE75F; -fx-font-size: 16px;");
        w2.setStyle("-fx-text-fill: #6EE75F; -fx-font-size: 16px;");
        l1.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 16px;");
        l2.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 16px;");

        card.getChildren().addAll(winners, w1, w2, losers, l1, l2);
        return card;

    }

        public void updateDashboardView (BigDecimal money, BigDecimal portfolioValue, BigDecimal netWorth){
            moneyLabel.setText(money + " kr");
            portfolioValueLabel.setText(portfolioValue + " kr");
            totalNetWorthLabel.setText(netWorth + " kr");
        }

        public VBox getRoot () {
            return root;
        }
    }
