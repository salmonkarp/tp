package seedu.address.model.person;

/**
 * Represents an assignment type in the address book.
 */
public enum GradeTypes {
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
