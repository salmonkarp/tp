package seedu.address.ui;

import java.util.Comparator;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Assignments;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.person.TutorialClass;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label teleHandle;
    @FXML
    private Label email;
    @FXML
    private Label grade;
    @FXML
    private FlowPane tutorialGroupAndTags;
    @FXML
    private Label attend;

    // For Assignments and Tutorial attendance
    @FXML
    private FlowPane assignmentTags;
    @FXML
    private FlowPane attendanceTags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        teleHandle.setText(person.getTeleHandle().value);
        String tutorialGroup = person.getTutorialGroup().value;
        if (!tutorialGroup.isEmpty()) {
            Label tutorialLabel = new Label(tutorialGroup);
            tutorialLabel.getStyleClass().add("tutorial-label");
            tutorialGroupAndTags.getChildren().add(tutorialLabel);
        }
        email.setText(person.getEmail().value);
        grade.setText(getGradeText());
        attend.setText(getAttendText());
        person.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tutorialGroupAndTags.getChildren().add(new Label(tag.tagName)));

        populateAssignmentTags();
        populateTutorialTags();
    }

    private void populateAssignmentTags() {
        assignmentTags.getChildren().clear();
        for (Assignments a : Assignments.values()) {
            // Requires model API: Optional<Integer> Person#getAssignmentScore(Assignments)
            Optional<Float> scoreOpt = getAssignmentScoreSafe(a);
            String labelText = a.getDescription() + " " + scoreOpt.map(s -> s + "/100").orElse("—/100");
            Label chip = new Label(labelText);
            chip.getStyleClass().add(scoreOpt.isPresent() ? "assignment-tag-graded" : "assignment-tag-ungraded");
            assignmentTags.getChildren().add(chip);
        }
    }

    private void populateTutorialTags() {
        attendanceTags.getChildren().clear();
        for (TutorialClass tc : TutorialClass.values()) {
            // Requires model API: boolean Person#hasAttended(TutorialClass)
            boolean attendedVal = hasAttendedSafe(tc);
            Label chip = new Label(tc.getDescription());
            chip.getStyleClass().add(attendedVal ? "tutorial-tag-attended" : "tutorial-tag-unattended");
            attendanceTags.getChildren().add(chip);
        }
    }

    // Temporary safe wrappers until model methods exist.
    private Optional<Float> getAssignmentScoreSafe(Assignments a) {
        try {
            Optional<Float> scoreOpt = Optional.of(person.getAssignmentScore(a));
            Grade emptyGrade = new Grade(" ");
            if (scoreOpt.get() == emptyGrade.valueFloat) {
                return Optional.empty();
            }
            return scoreOpt;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private boolean hasAttendedSafe(TutorialClass tc) {
        try {
            return person.hasAttendedTutorial(tc);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the average grade text to be displayed.
     * Verbose subclass can override this method to provide different grade text.
     */
    protected String getGradeText() {
        String overallGrade = person.getOverallGrade().value;
        return overallGrade.isBlank() ? "No grades yet." : "Overall grade: " + overallGrade;
    }

    /**
     * Returns the overall attendance text to be displayed.
     * Verbose subclass can override this method to provide different attendance text.
     */
    protected String getAttendText() {
        return "Overall attendance: " + person.getOverallAttendance();
    }
}
