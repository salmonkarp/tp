package seedu.address.model.person;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A map from assignments to grades for each student.
 */
public class GradeMap extends LinkedHashMap<Assignments, Grade> {
    public static final String MESSAGE_CONSTRAINTS = Grade.MESSAGE_CONSTRAINTS + "\n" + Assignments.MESSAGE_CONSTRAINTS;

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
    public GradeMap(LinkedHashMap<Assignments, Grade> map) {
        super(map);
    }

    /**
     * Returns true if a given map is a valid grade map.
     * A valid grade map has valid assignment keys and valid grade values.
     */
    public static boolean isValidGradeMap(LinkedHashMap<String, String> gradeMap) {
        for (Map.Entry<String, String> entry : gradeMap.entrySet()) {
            String assignmentStr = entry.getKey();
            String gradeStr = entry.getValue();
            if (!Assignments.isValidAssignment(assignmentStr) || !Grade.isValidGrade(gradeStr)) {
                return false;
            }
        }
        return true;
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

    /**
     * Converts the GradeMap to a Map with String keys and values.
     * Used for serialization.
     */
    public Map<String, String> toStringMap() {
        Map<String, String> stringMap = new LinkedHashMap<>();
        for (Map.Entry<Assignments, Grade> entry : this.entrySet()) {
            stringMap.put(entry.getKey().name(), entry.getValue().toString());
        }
        return stringMap;
    }
}
