package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of previously entered commands.
 */
public class CommandHistoryPanel extends UiPart<Region> {
    public static final String FXML = "CommandHistoryPanel.fxml";

    @FXML
    private ListView<String> commandListView;

    /**
     * Creates a {@code CommandHistoryPanel} with the given {@code ObservableList}.
     */
    public CommandHistoryPanel(ObservableList<String> history) {
        super(FXML);
        setConnections(history);
    }

    // Sets up the connections between the list view and the observable list.
    private void setConnections(ObservableList<String> history) {
        commandListView.setItems(history);
        commandListView.setCellFactory(listView -> new CommandHistoryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CommandCard}.
     */
    private static class CommandHistoryListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                CommandCard card = new CommandCard(item);
                // Fill full row width (subtract a bit for scrollbar/padding)
                card.getRoot().prefWidthProperty().bind(getListView().widthProperty().subtract(16));
                setGraphic(card.getRoot());
            }
        }
    }
}