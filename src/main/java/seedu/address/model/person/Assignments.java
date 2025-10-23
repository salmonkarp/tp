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

    /**
     * Returns a user-friendly description of the assignment type.
     */
    public String getDescription() {
        switch (this) {
        case Q1: return "Quiz 1";
        case Q2: return "Quiz 2";
        case Q3: return "Quiz 3";
        case Q4: return "Quiz 4";
        case Q5: return "Quiz 5";
        case Q6: return "Quiz 6";
        case Q7: return "Quiz 7";
        case Finals: return "Finals";
        default: return "";
        }
    }
}
