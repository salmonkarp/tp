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
        float totalGrade = 0f;
        int count = 0;
        for (Assignments gradeType : Assignments.getAllAssignments()) {
            Grade grade = this.get(gradeType);
            if (grade != null && !grade.value.equals(" ")) {
                totalGrade += grade.valueFloat;
                count++;
            }
        }
        if (count != 0) {
            return new Grade(totalGrade / count);
        } else {
            return new Grade(" ");
        }
    }

    /**
     * Formats the grades in the GradeMap for display.
     * Excludes uninitialized grades.
     */
    public String formatGrades() {
        StringBuilder sb = new StringBuilder();
        for (Assignments assignment : Assignments.getAllAssignments()) {
            Grade grade = this.get(assignment);
            if (grade != null && !grade.value.equals(" ")) {
                sb.append(assignment.name())
                        .append(": ")
                        .append(grade)
                        .append(", ");
            }
        }
        // Remove the last ", " if exists
        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
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
