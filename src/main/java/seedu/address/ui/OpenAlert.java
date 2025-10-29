package seedu.address.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class OpenAlert {
    /**
     * Opens an alert dialog with the specified title, header, and content.
     *
     * @param title   The title of the alert dialog.
     * @param header  The header text of the alert dialog.
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
