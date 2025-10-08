package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's grade in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGradeConst(String)}
 */
public class Grade {


    public static final String MESSAGE_CONSTRAINTS_CONSTRUCT =
            "Grade should be a number between 0.00 and 100.00, with exactly 2 decimal places.";

    /*
     * Strict validation for constructor, exactly 2 decimal places for standardization.
     * Matches 0.00 to 99.99 or exactly 100.00
     */
    public static final String VALIDATION_REGEX_CONSTRUCT = "^(100\\.00|\\d{1,2}\\.\\d{2})$";

    public final String grade;

    /**
     * Constructs a {@code Grade}.
     *
     * @param grade A valid grade number with exactly 2 decimal places.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGradeConst(grade), MESSAGE_CONSTRAINTS_CONSTRUCT);
        this.grade = grade;
    }

    /**
     * Returns true if a given string is a valid Grade for the constructor (strict 2 d.p.).
     */
    public static boolean isValidGradeConst(String test) {
        return test.matches(VALIDATION_REGEX_CONSTRUCT);
    }

    @Override
    public String toString() {
        return this.grade;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Grade)) {
            return false;
        }

        Grade otherGrade = (Grade) other;
        return grade.equals(otherGrade.grade);
    }

    @Override
    public int hashCode() {
        return grade.hashCode();
    }

}
