package no.ntnu.idatt2003.group22.millions.view.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;

public class TransactionReceiptView {
    private final Stage stage;
    private final Transaction transaction;


    public TransactionReceiptView(Transaction transaction) {
        this.stage = new Stage();
        this.transaction = transaction;

        configureStage();
    }


    private void configureStage() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Transaction Receipt");

        VBox root = new VBox();
        root.setPrefSize(460, 460);
        root.setStyle("-fx-background-color: #343D52;");

        Label title = new Label("Transaction Receipt");
        title.setPadding(new Insets(14, 24, 14, 24));
        title.setMaxWidth(Double.MAX_VALUE);
        title.setStyle("""
                -fx-background-color: #1A2332;
                -fx-text-fill: white;
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                """);

        VBox content = new VBox(22);
        content.setPadding(new Insets(28, 34, 24, 34));

        content.getChildren().addAll(
                createInfoRow("Type:", transaction.getClass().getSimpleName()),
                createInfoRow("Week:", String.valueOf(transaction.getWeek())),
                createInfoRow("Symbol:", transaction.getShare().getSymbol()),
                createInfoRow("Quantity:", transaction.getShare().getQuantity().toString()),
                createInfoRow("Gross:", transaction.calculateGross() + " kr"),
                createInfoRow("Fee:", transaction.calculateCommission() + " kr"),
                createInfoRow("Tax:", transaction.calculateTax() + " kr"),
                createTotalRow("Total:", transaction.calculateTotal() + " kr"),
                createButtonRow()
        );

        root.getChildren().addAll(title, content);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private HBox createInfoRow(String labelText, String valueText) {
        Label label = createTextLabel(labelText);
        Label value = createValueLabel(valueText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox row = new HBox(label, spacer, value);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private HBox createTotalRow(String labelText, String valueText) {
        Label label = createTextLabel(labelText);
        Label value = createValueLabel(valueText);

        label.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 20px; -fx-font-weight: bold;");
        value.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 20px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox row = new HBox(label, spacer, value);
        row.setPadding(new Insets(10, 0, 0, 0));
        return row;
    }

    private HBox createButtonRow() {
        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(150);
        closeButton.setStyle("""
                -fx-background-color: #36BDF2;
                -fx-text-fill: white;
                -fx-background-radius: 18;
                -fx-font-size: 18px;
                """);

        closeButton.setOnAction(event -> stage.close());

        HBox row = new HBox(closeButton);
        row.setAlignment(Pos.CENTER_RIGHT);
        return row;
    }

    private Label createTextLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 16px;");
        return label;
    }

    private Label createValueLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 16px;");
        return label;
    }

    public void show() {
        stage.showAndWait();
    }
}
