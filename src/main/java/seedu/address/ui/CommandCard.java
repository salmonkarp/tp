package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class CommandCard extends UiPart<Region> {
    public static final String FXML = "CommandCard.fxml";

    @FXML
    private Label commandLabel;

    public CommandCard(String commandText) {
        super(FXML);
        commandLabel.setText(commandText);
    }
}