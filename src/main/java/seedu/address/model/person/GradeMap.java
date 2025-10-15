package seedu.address.model.person;

import java.util.HashMap;

/**
 * A map from assignments to grades for each student.
 */
public class GradeMap extends HashMap<Assignments, Grade> {
    /**
     * Constructs a default GradeList with uninitialized scores.
     */
    public GradeMap() {
        super();
        for (Assignments gradeType : Assignments.getAllAssignments()) {
            this.put(gradeType, new Grade(" "));
        }
    }

    /**
     * Constructs a GradeList with the given map.
     * Used for copying.
     */
    public GradeMap(HashMap<Assignments, Grade> map) {
        super(map);
    }

    /**
     * Returns the overall grade as the average of all grades.
     * If there are no grades, returns a Grade with an empty value.
     */
    public Grade getOverallGrade() {
        // for modification later
        return new Grade(" ");
    }

    @Override
    public Grade put(Assignments key, Grade value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value must not be null");
        }
        return super.put(key, value);
    }

    @Override
    public Grade get(Object key) {
        if (!(key instanceof Assignments)) {
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
