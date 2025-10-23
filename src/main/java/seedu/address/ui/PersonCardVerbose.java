package seedu.address.ui;

import seedu.address.model.person.Person;

/**
 * A UI component that displays more detailed information of a {@code Person} card.
 */
public class PersonCardVerbose extends PersonCard {

    public PersonCardVerbose(Person person, int displayedIndex) {
        super(person, displayedIndex);
    }

    /**
     * Returns a string representation of all grades of the person.
     */
    @Override
    protected String getGradeText() {
        return person.getFormattedGrades();
    }

    /**
     * Returns a string representation of all attended tutorials of the person.
     */
    @Override
    protected String getAttendText() {
        return person.getFormattedAttendance();
    }
}
