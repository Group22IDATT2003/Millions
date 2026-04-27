package no.ntnu.idatt2003.group22.millions.view.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import no.ntnu.idatt2003.group22.millions.model.Stock;

import java.math.BigDecimal;
import java.util.Optional;

public class BuyDialog extends TextInputDialog {


    public BuyDialog(Stock stock) {
        setTitle("Buy Stock");
        setHeaderText("Buy " + stock.getSymbol() + " at " + stock.getCompany());
        setContentText("Quantity:");
    }


    public Optional<BigDecimal> showQuantityDialog() {
        Optional<String> result = super.showAndWait();

        if (result.isEmpty()) {
            return Optional.empty();
        }

        try {
            BigDecimal quantity = new BigDecimal(result.get().trim());

            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                showError("Quantity must be greater than 0.");
                return Optional.empty();
            }

            return Optional.of(quantity);

        } catch (NumberFormatException e) {
            showError("Quantity must be a valid number.");
            return Optional.empty();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid quantity");
        alert.setHeaderText("Invalid quantity");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
