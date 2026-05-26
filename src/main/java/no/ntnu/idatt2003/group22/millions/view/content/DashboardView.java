package no.ntnu.idatt2003.group22.millions.view.content;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.model.Stock;


public class DashboardView {
    private final VBox root;

    private final Label totalNetWorthLabel;
    private final Label moneyLabel;
    private final Label portfolioValueLabel;
    private final VBox stockRows;
    private final Label netWorthValueLabel;
    private final Label netWorthChangeLabel;
    private LineChart<Number, Number> netWorthChart;
    private XYChart.Series<Number, Number> netWorthSeries;
    private final Label w1Label;
    private final Label w2Label;
    private final Label l1Label;
    private final Label l2Label;


    public DashboardView() {
        this.root = new VBox();
        this.netWorthValueLabel = new Label();
        this.netWorthChangeLabel = new Label();
        this.totalNetWorthLabel = new Label("Total net worth: 0kr");
        this.netWorthSeries = new XYChart.Series<>();
        this.moneyLabel = new Label("Money: 0kr");
        this.portfolioValueLabel = new Label("Portfolio value: 0kr");
        this.w1Label = new Label();
        this.w2Label = new Label();
        this.l1Label = new Label();
        this.l2Label = new Label();

        this.stockRows = new VBox(3);

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(24);
        root.setPadding(new Insets(24, 32, 24, 32));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: #1A2332;");


        Label title = new Label("Dashboard");
        title.setStyle("""
                -fx-text-fill: #FFFFFF; 
                -fx-font-size: 34px;
                """);

        HBox summaryCards = new HBox(24);
        summaryCards.getChildren().addAll(
                createSummaryCard("Total net worth", totalNetWorthLabel),
                createSummaryCard("Money", moneyLabel),
                createSummaryCard("Portfolio value", portfolioValueLabel)
        );

        HBox mainCards = new HBox(24);
        mainCards.getChildren().addAll(
                createNetWorthCard(),
                createMoversCard()
        );

        VBox miniSummaryCard = createMiniSummaryCard();

        root.getChildren().addAll(title, summaryCards, mainCards, miniSummaryCard);
    }

    private VBox createCard() {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16));
        card.setStyle("""
                -fx-background-color: #343D52;
                -fx-background-radius: 18;
                    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 4);
                """);
        return card;
    }


    private VBox createSummaryCard(String title, Label valueLabel) {
        VBox card = createCard();
        card.setPrefWidth(520);
        moneyLabel.setStyle("""
                -fx-text-fill: #4CAF50;
                    -fx-font-size: 24px;
                    -fx-font-weight: bold;
                """);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 14px;
                """);
        valueLabel.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 22px;
                -fx-font-weight: bold;
                """);

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;


    }

    private HBox createNetWorthCard() {
        HBox card = new HBox();
        VBox textBox = new VBox();
        card.setAlignment(Pos.TOP_RIGHT);
        card.setPadding(new Insets(20));
        card.setSpacing(20);
        card.setStyle("""
        -fx-background-color: #343D52;
        -fx-background-radius: 22;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 4);
        """);
        card.setPrefSize(460, 280);

        Label title = new Label("Net worth:");
        Label value = new Label("78 500 NOK");
        Label change = new Label("+3 200 (+4.2%)");

        title.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 24px;");
        value.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 18px;");
        change.setStyle("-fx-text-fill: #6EE75F; -fx-font-size: 18px;");

        card.setAlignment(Pos.BOTTOM_LEFT);

        final NumberAxis xAxis =new NumberAxis();
        final NumberAxis yAxis =new NumberAxis();
        xAxis.setLabel("Weeks");
        yAxis.setLabel("Net Worth");

        xAxis.lookup(".axis-label").setStyle("-fx-text-fill: #FFFFFF;");
        yAxis.lookup(".axis-label").setStyle("-fx-text-fill: #FFFFFF;");


        netWorthChart = new LineChart<>(xAxis, yAxis);
        netWorthChart.getData().add(netWorthSeries);


        netWorthChart.setPrefSize(250, 260);
        netWorthChart.setMaxSize(250, 260);
        netWorthChart.setLegendVisible(false);
        netWorthChart.setCreateSymbols(false);
        netWorthChart.setHorizontalGridLinesVisible(false);
        netWorthChart.setVerticalGridLinesVisible(false);

        netWorthChart.setStyle("""
        -fx-background-color: #2C394F;
        -fx-padding: 0;
        """);


        textBox.getChildren().addAll(title, netWorthValueLabel, netWorthChangeLabel);

        card.getChildren().addAll(netWorthChart, textBox);

        return card;
    }

    public void updateNetWorth(BigDecimal netWorth, BigDecimal change) {
        netWorthValueLabel.setText(netWorth + " NOK");
        netWorthValueLabel.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 18px;
                """);
        String prefix = change.compareTo(BigDecimal.ZERO) > 0 ? "+" : "";

        netWorthChangeLabel.setText(prefix + change + " NOK (this week)");

        if (change.compareTo(BigDecimal.ZERO) >= 0) {
            netWorthChangeLabel.setStyle("-fx-text-fill: #6EE75F;");
        } else {
            netWorthChangeLabel.setStyle("-fx-text-fill: #EF4444;");
        }
    }

    public void updateNetWorthGraph(int week, BigDecimal netWorth) {
        netWorthSeries.getData().add(
                new XYChart.Data<>(week, netWorth.doubleValue())
        );
    }

    private VBox createMoversCard() {
        VBox card = createCard();
        card.setPrefSize(300, 220);


        Label winners = new Label("Weekly winners:");
        Label losers = new Label("Weekly losers:");

        winners.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 22px;");
        losers.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 22px;");

        w1Label.setStyle("-fx-text-fill: #6EE75F;");
        l1Label.setStyle("-fx-text-fill: #EF4444;");
        w2Label.setStyle("-fx-text-fill: #6EE75F;");
        l2Label.setStyle("-fx-text-fill: #EF4444;");

        card.getChildren().addAll(winners, w1Label, w2Label, losers, l1Label, l2Label);
        return card;

    }

    public void updateMovers(List<Stock> winners, List<Stock> losers){
        if (winners.size() > 0) {
            w1Label.setText("+" + winners.get(0).getSymbol() + "  " + winners.get(0).getLatestPriceChange() + "%");
        }

        if (winners.size() > 1) {
            w2Label.setText("+" + winners.get(1).getSymbol() + "  " + winners.get(1).getLatestPriceChange() + "%");
        }

        if (losers.size() > 0) {
            l1Label.setText(losers.get(0).getSymbol() + "  " + losers.get(0).getLatestPriceChange() + "%");
        }

        if (losers.size() > 1) {
            l2Label.setText(losers.get(1).getSymbol() + "  " + losers.get(1).getLatestPriceChange() + "%");
        }
    }


    public VBox createMiniSummaryCard(){
        VBox card = createCard();
        card.setSpacing(12);
        card.setPrefWidth(850);
        card.setMinWidth(850);


        HBox header = new HBox(100);
        header.getChildren().addAll(
                createTableHeader("Mini portofolio summary:"),
                createTableHeader("Symbol:"),
                createTableHeader("Change:"),
                createTableHeader("Value:")
        );

        card.getChildren().addAll(header, stockRows);
        return card;
    }

    private Label createTableHeader(String text) {
        Label label = new Label(text);
        label.setStyle("""
                -fx-text-fill: #FFFFFF; 
                -fx-font-size: 22px;
                -fx-font-weight: bold;
                """);
        return label;
    }

    public void updateDashboardView(BigDecimal money, BigDecimal portfolioValue, BigDecimal netWorth) {
        moneyLabel.setText(money + " kr");
        portfolioValueLabel.setText(portfolioValue + " kr");
        totalNetWorthLabel.setText(netWorth + " kr");
    }

    public void updateMiniSummary(List<Share> shares){
        stockRows.getChildren().clear();


        for (Share share : shares.stream().sorted((s1, s2) -> s2.getCurrentValue().compareTo(s1.getCurrentValue()))
                .limit(3)
                .toList()){
            HBox row = createRow();

            BigDecimal changeValue = share.getPriceChangePercentage()
                    .setScale(2, RoundingMode.HALF_UP);

            String prefix = changeValue.compareTo(BigDecimal.ZERO) > 0 ? "+" : "";

            Label empty = createLabel("");
            Label symbol = createLabel(share.getSymbol());
            Label change = createLabel(prefix + changeValue + " %");
            Label value = createLabel(share.getCurrentValue() + " kr");

            empty.setPrefWidth(360);
            symbol.setPrefWidth(180);
            change.setPrefWidth(180);
            value.setPrefWidth(180);


            if (changeValue.compareTo(BigDecimal.ZERO) >= 0) {
                change.setStyle("-fx-text-fill: #6EE75F; -fx-font-size: 14px;");
            } else {
                change.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 14px;");
            }

            row.getChildren().addAll(empty, symbol, change, value);
            stockRows.getChildren().add(row);
        }
    }

    private Label createLabel(String text){
        Label label = new Label(text);
        label.setStyle("""
                -fx-text-fill: #FFFFFF;
                -fx-font-size: 14px;
                """);
        return label;

    }

    private HBox createRow(){
        HBox card = new HBox(12);
        card.setPadding(new Insets(16));
        card.setStyle(""" 
                -fx-background-color: #343D52; 
                -fx-background-radius: 18; 
                """);
        return card; }

    public VBox getRoot() {
        return root;
    }
}
