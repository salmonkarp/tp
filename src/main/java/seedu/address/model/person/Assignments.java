package seedu.address.model.person;

import java.util.Arrays;

/**
 * Represents an assignment type in the address book.
 */
public enum Assignments {
    Q1,
    Q2,
    Q3,
    Q4,
    Q5,
    Q6,
    Q7,
    Finals;

    public static final String MESSAGE_CONSTRAINTS =
            "Assignment types should be one of the following: "
            + Arrays.toString(Assignments.getAllAssignments());
    /**
     * Returns an array of all assignment types.
     */
    public static Assignments[] getAllAssignments() {
        return Assignments.values();
    }

    /**
     * Returns true if a given string is a valid assignment type.
     */
    public static boolean isValidAssignment(String test) {
        for (Assignments assignment : Assignments.values()) {
            if (assignment.name().equals(test)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the GradeType corresponding to the given string.
     * Throws IllegalArgumentException if no such GradeType exists.
     */
    public static Assignments fromString(String part) {
        for (Assignments gradeType : Assignments.values()) {
            if (gradeType.name().equals(part)) {
                return gradeType;
            }
        }
        throw new IllegalArgumentException("No assignment with name: " + part);
    }
}
