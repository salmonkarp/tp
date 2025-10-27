package seedu.address.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Popup;
import java.util.List;

/**
 * A custom pop-up box that displays a list of items.
 */
public class PopUpBox {
    private Popup popup;

    public PopUpBox(List<String> items, String colour) {
        popup = new Popup();
        popup.setAutoHide(true);
        popup.setAutoFix(true);

        // build content
        Polygon arrow = new Polygon(20, 0, 40, 20, 0, 20);
        arrow.setStyle("-fx-fill: #97979c; "
                + "-fx-effect: dropshadow(gaussian, black, 2, 0.5, 0, 1); "
                + "-fx-border-color: #97979c;"
        );

        VBox content = new VBox();
        for (String item : items) {
            Label label = new Label(item);
            label.setStyle("-fx-font-weight: bold; -fx-padding: 4 8; -fx-text-fill: #d4d4d6;");
            content.getChildren().add(label);
        }

        content.setStyle("-fx-background-color: derive(#1d1d1d, 20%);"
                + " -fx-padding: 15;"
                + " -fx-border-radius: 0;"
                + " -fx-background-radius: 0;"
                + " -fx-border-color: #97979c;"
        );

        VBox popupBox = new VBox(arrow, content);
        popupBox.setStyle("-fx-background-color: transparent;"); // keep background on content

        // Prevent clicking inside the popup from propagating to the scene
        popupBox.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> e.consume());

        popup.getContent().add(popupBox);

        // When popup is hidden, release reference so we can create/show cleanly next time
        popup.setOnHidden(e -> popup = null);
    }

    /**
     * Shows the popup at the specified location relative to the owner node.
     *
     * @param owner the owner node
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void show(Node owner, double x, double y) {
        if (popup != null && !popup.isShowing()) {
            popup.show(owner, x, y);
        }
    }

    /**
     * Hides the popup if it is currently showing.
     */
    public void hide() {
        if (popup != null && popup.isShowing()) {
            popup.hide();
        }
    }

    /**
     * Returns true if the popup is currently showing.
     */
    public boolean isShowing() {
        return popup != null && popup.isShowing();
    }
}
