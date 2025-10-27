package seedu.address.model.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CommandHistoryManager {
    private final ObservableList<String> history = FXCollections.observableArrayList();

    public CommandHistoryManager() {

    }

    public ObservableList<String> getObservableHistory() {
        return history;
    }

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

    public void clear() {
        history.clear();
    }
}
