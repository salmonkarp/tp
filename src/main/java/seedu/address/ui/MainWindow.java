package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.GradeTypes;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private Popup assignmentPopup = null;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private Label assignmentMenuLabel;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the assignment types popup or focuses on it if it's already opened.
     */
    @FXML
    public void handleAssignmentLabelClick(MouseEvent event) {
        Node source = (Node) event.getSource();

        // If popup is showing -> hide and consume the press so it won't reopen
        if (assignmentPopup != null && assignmentPopup.isShowing()) {
            assignmentPopup.hide();
            event.consume(); // important: prevent the event from causing a show again
            return;
        }

        // Create popup if needed
        if (assignmentPopup == null) {
            assignmentPopup = new Popup();
            // Make it auto-hide when clicking outside the popup (inside the same window)
            assignmentPopup.setAutoHide(true);
            assignmentPopup.setAutoFix(true);

            // Build content
            Polygon arrow = new Polygon(20, 0, 40, 20, 0, 20);
            arrow.setStyle("-fx-fill: #97979c; "
                    + "-fx-effect: dropshadow(gaussian, black, 2, 0.5, 0, 1); "
                    + "-fx-border-color: #97979c;"
            );

            VBox content = assignmentPopupContent();
            content.setStyle("-fx-background-color: derive(#1d1d1d, 20%);"
                    + " -fx-padding: 15;"
                    + " -fx-border-radius: 5;"
                    + " -fx-border-color: #97979c;"
            );

            VBox popupBox = new VBox(arrow, content);
            popupBox.setStyle("-fx-background-color: transparent;"); // keep background on content

            // Prevent clicking inside the popup from propagating to the scene (optional)
            popupBox.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> e.consume());

            assignmentPopup.getContent().add(popupBox);

            // When popup is hidden, release reference so we can create/show cleanly next time
            assignmentPopup.setOnHidden(e -> assignmentPopup = null);
        }

        // Show below the label
        double x = source.localToScreen(0, 0).getX();
        double y = source.localToScreen(0, ((Region) source).getBoundsInLocal().getHeight()).getY();
        assignmentPopup.show(source, x, y);

        // Hide popup when the window loses focus (covers clicks outside the app / alt-tab)
        Window window = source.getScene().getWindow();
        if (window != null) {
            // Listen once - use a change listener that hides popup when focus lost
            window.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean wasFocused, Boolean isNowFocused) {
                    if (!isNowFocused && assignmentPopup != null && assignmentPopup.isShowing()) {
                        assignmentPopup.hide();
                    }
                    // Unregister this listener once popup is hidden or window loses focus
                    // We don't keep track of the listener reference here; the simple approach:
                    // when popup hides it is nulled by setOnHidden above.
                }
            });
        }

    }

    private VBox assignmentPopupContent() {
        VBox content = new VBox();
        GradeTypes[] assignments = GradeTypes.values();
        for (GradeTypes assignment : assignments) {
            Label label = new Label(assignment.getDescription());
            label.setStyle("-fx-font-weight: bold; -fx-padding: 4 8; -fx-text-fill: #d4d4d6;");
            content.getChildren().add(label);
        }
        return content;
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
