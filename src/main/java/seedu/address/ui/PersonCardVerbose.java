package seedu.address.ui;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.person.Assignments;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.person.TutorialClass;

/**
 * A UI component that displays more detailed information of a {@code Person} card.
 */
public class PersonCardVerbose extends PersonCard {

    // For Assignment grades and Tutorial attendance
    @FXML
    private FlowPane assignmentTags;
    @FXML
    private FlowPane attendanceTags;

    /**
     * Creates a {@code PersonCardVerbose} with the given {@code Person} and index to display.
     */
    public PersonCardVerbose(Person person, int displayedIndex) {
        super(person, displayedIndex);
        assignmentsTitle.setText("Assignments:");
        tutorialsTitle.setText("Tutorials:");
        populateAssignmentTags();
        populateTutorialTags();
    }

    /**
     * Populates assignment tags with assignment scores of each assignment.
     */
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

    /**
     * Populates tutorial attendance tags with attendance status of each tutorial.
     */
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

    /**
     * Gets assignment score safely, returning Optional.empty() if any exception occurs.
     * @param a the assignment
     * @return an Optional containing the assignment score, or Optional.empty() if not available
     */
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

    /**
     * Checks attendance safely, returning false if any exception occurs.
     * @param tc the tutorial class
     * @return true if attended, false otherwise
     */
    private boolean hasAttendedSafe(TutorialClass tc) {
        try {
            return person.hasAttendedTutorial(tc);
        } catch (Exception e) {
            return false;
        }
    }
}
