package no.ntnu.idatt2003.group22.millions.view.content;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;

import java.util.List;

public class TransactionView {
    private final VBox root;
    private final Label titleLabel;
    private final ListView<Transaction> transactionListView;

    public TransactionView(){
        this.root = new VBox();
        this.titleLabel = new Label("Transaction");
        this.transactionListView = new ListView<>();

        configureLayout();
    }

    private void configureLayout() {
        root.setSpacing(20);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_LEFT);
        root.getChildren().addAll(titleLabel, transactionListView);
    }

    public VBox getRoot(){
        return root;
    }

    public void updateTransaction(List<Transaction> transactions){
        transactionListView.getItems().clear();
        transactionListView.getItems().addAll(transactions);
    }

    public Transaction getSelectedTransaction() {
        return transactionListView.getSelectionModel().getSelectedItem();
    }
}
