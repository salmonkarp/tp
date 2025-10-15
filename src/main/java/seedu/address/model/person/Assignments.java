package seedu.address.model.person;

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

    /**
     * Returns an array of all assignment types.
     */
    public static Assignments[] getAllAssignments() {
        return Assignments.values();
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
