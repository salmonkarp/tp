package seedu.address.ui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * A class to open alert dialogs.
 */
public class OpenAlert {
    /**
     * Opens an alert dialog with the specified title, header, and content.
     *
     * @param title   The title of the alert dialog.
     * @param content The content text of the alert dialog.
     */
    public static boolean showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        alert.getDialogPane().setPrefSize(300, 200);

        Optional result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

    }
}
