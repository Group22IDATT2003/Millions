package no.ntnu.idatt2003.group22.millions.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt2003.group22.millions.controller.GameController;
import no.ntnu.idatt2003.group22.millions.view.MainView;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        GameController gameController = new GameController(mainView);
        Scene scene = new Scene(mainView.getRoot(), 1200, 800);

        stage.setTitle("Millions");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
