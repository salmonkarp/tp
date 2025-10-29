package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * A UI component that displays information of a single command.
 */
public class CommandCard extends UiPart<Region> {
    public static final String FXML = "CommandCard.fxml";

    @FXML
    private TextField commandField;

    /**
     * Creates a {@code CommandCard} with the given {@code commandText}.
     */
    public CommandCard(String commandText) {
        super(FXML);
        commandField.setText(commandText);
    }
}
