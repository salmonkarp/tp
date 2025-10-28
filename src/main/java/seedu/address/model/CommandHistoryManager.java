package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages the history of commands entered by the user.
 */
public class CommandHistoryManager {
    private final ObservableList<String> history = FXCollections.observableArrayList();

    /**
     * Creates a CommandHistoryManager.
     */
    public CommandHistoryManager() {
    }

    /**
     * Returns the observable list of command history.
     */
    public ObservableList<String> getObservableHistory() {
        return history;
    }

    /**
     * Adds a command to the history.
     * Ignores null or empty commands.
     */
    public void add(String commandText) {
        if (commandText == null) {
            return;
        }
        String trimmed = commandText.trim();
        if (trimmed.isEmpty()) {
            return;
        }
        history.add(0, trimmed);
    }

    /**
     * Clears the command history.
     */
    public void clear() {
        history.clear();
    }
}
