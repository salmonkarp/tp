package seedu.address.model.person;

import java.util.HashMap;

import seedu.address.model.GradeTypes;

/**
 * A list of grades for assignments.
 * Guarantees: immutable
 */
public class GradeMap extends HashMap<GradeTypes, Grade> {

    public static final String MESSAGE_CONSTRAINTS =
            "GradeList should be in the format "
            + "ASSIGNMENT:GRADE;ASSIGNMENT:GRADE;...\n"
            + "Assignments without grades can be represented as ASSIGNMENT:.";

    /**
     * Constructs an empty GradeList.
     */
    public GradeMap() {
        super();
        for (GradeTypes gradeType : GradeTypes.getAllAssignments()) {
            this.put(gradeType, new Grade("0.00"));
        }
    }

    /**
     * Constructs a GradeList with the given map.
     * Used for copying.
     */
    public GradeMap(HashMap<GradeTypes, Grade> map) {
        super(map);
    }

    /**
     * Returns the overall grade as the average of all grades.
     * If there are no grades, returns a Grade with an empty value.
     */
    public Grade getOverallGrade() {
        // can be modified later when we have weights for assignments
        // for later
        return new Grade("0.00");
    }

    @Override
    public Grade put(GradeTypes key, Grade value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value must not be null");
        }
        return super.put(key, value);
    }

    @Override
    public Grade get(Object key) {
        if (!(key instanceof GradeTypes)) {
            throw new IllegalArgumentException("Key must be an Assignment");
        }
        return super.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GradeMap)) {
            return false;
        }
        GradeMap other = (GradeMap) o;
        return super.equals(other);
    }

}
