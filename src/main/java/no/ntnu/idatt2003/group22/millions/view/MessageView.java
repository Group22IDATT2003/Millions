package no.ntnu.idatt2003.group22.millions.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class MessageView {
    private final VBox root;
    private final Label titleLabel;
    private final TextArea messageArea;

    public MessageView(){
        this.root = new VBox();

        this.titleLabel = new Label("Messages");
        this.messageArea = new TextArea("");

        configureLayout();
    }

    private void configureLayout(){
        root.setSpacing(20);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER_LEFT);
        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        root.getChildren().addAll(titleLabel, messageArea);
    }

    public VBox getRoot(){
        return root;
    }

    public void updateMessageInfo(String title, String message){
        titleLabel.setText("Messages: " + title);
        messageArea.setText(message);
    }

}
