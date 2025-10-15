package seedu.address.model;

/**
 * Represents an assignment type in the address book.
 */
public enum GradeTypes {
    Q1,
    Q2,
    Q3,
    Q4,
    Q5,
    Midterm,
    Final;

    /**
     * Returns an array of all assignment types.
     */
    public static GradeTypes[] getAllAssignments() {
        return GradeTypes.values();
    }

    /**
     * Returns the GradeType corresponding to the given string.
     * Throws IllegalArgumentException if no such GradeType exists.
     */
    public static GradeTypes fromString(String part) {
        for (GradeTypes gradeType : GradeTypes.values()) {
            if (gradeType.name().equals(part)) {
                return gradeType;
            }
        }
        throw new IllegalArgumentException("No assignment with name: " + part);
    }
}
