package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class CommandHistoryPanel extends UiPart<Region> {
    public static final String FXML = "CommandHistoryPanel.fxml";

    @FXML
    private ListView<String> commandListView;

    public CommandHistoryPanel(ObservableList<String> history) {
        super(FXML);
        setConnections(history);
    }

    private void setConnections(ObservableList<String> history) {
        commandListView.setItems(history);
        commandListView.setCellFactory(listView -> new CommandHistoryListViewCell());
    }

    private static class CommandHistoryListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CommandCard(item).getRoot());
            }
        }
    }
}