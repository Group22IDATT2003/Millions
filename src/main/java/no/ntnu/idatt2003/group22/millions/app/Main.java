package no.ntnu.idatt2003.group22.millions.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt2003.group22.millions.controller.GameController;
import no.ntnu.idatt2003.group22.millions.view.MainView;

/**
 * Main entry point for the Millions application
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application
     * 
     * @param stage the primary application stage
     */
    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        GameController gameController = new GameController(mainView);
        Scene scene = new Scene(mainView.getRoot(), 1200, 800);

        stage.setTitle("Millions");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the application 
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
