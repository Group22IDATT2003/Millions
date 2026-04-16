package no.ntnu.idatt2003.group22.millions.app;

import javafx.application.Application;

/**
 * Dedicated launcher class so the app can be started as a plain Java main class
 * without triggering JavaFX runtime lookup issues.
 */
public final class Launcher {

    private Launcher() {
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
