package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;

public class GradeMapTest {

    public static final String VALID_GRADE_0 = "85.00";
    public static final String VALID_GRADE_1 = "90.00";
    public static final String CORRECT_GRADE_0 = "87.50";
    public static final String INVALID_GRADE_0 = "InvalidGrade";
    public static final String VALID_ASSIGNMENT_STR_0 = "Q1";
    public static final String VALID_ASSIGNMENT_STR_1 = "Q2";
    public static final String INVALID_ASSIGNMENT_STR_0 = "A1";
    public static final Assignments VALID_ASSIGNMENT_0 = Assignments.Q1;
    public static final Assignments VALID_ASSIGNMENT_1 = Assignments.Q2;
    public static final Assignments VALID_ASSIGNMENT_2 = Assignments.Finals;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GradeMap((LinkedHashMap<Assignments, Grade>) null));
    }

    @Test
    public void constructor_success() {
        GradeMap gradeMap = new GradeMap();
        for (Assignments gradeType : Assignments.getAllAssignments()) {
            assertTrue(gradeMap.containsKey(gradeType));
            assertEquals(" ", gradeMap.get(gradeType).value);
        }
    }

    @Test
    public void putAndGet_success() {
        GradeMap gradeMap = new GradeMap();
        Assignments firstAssignment = Assignments.getAllAssignments()[0];
        Grade firstGrade = new Grade(VALID_GRADE_0);
        gradeMap.put(firstAssignment, firstGrade);
        assertEquals(gradeMap.get(firstAssignment), firstGrade);
    }

    @Test
    public void put_nullKey_throwsIllegalArgumentException() {
        GradeMap gradeMap = new GradeMap();
        Grade validGrade = new Grade(VALID_GRADE_0);
        assertThrows(IllegalArgumentException.class, () -> gradeMap.put(null, validGrade));
    }

    @Test
    public void put_nullValue_throwsIllegalArgumentException() {
        GradeMap gradeMap = new GradeMap();
        Assignments firstAssignment = Assignments.getAllAssignments()[0];
        assertThrows(IllegalArgumentException.class, () -> gradeMap.put(firstAssignment, null));
    }

    @Test
    public void get_invalidKey_throwsIllegalArgumentException() {
        GradeMap gradeMap = new GradeMap();
        assertThrows(IllegalArgumentException.class, () -> gradeMap.get(INVALID_ASSIGNMENT_STR_0));
    }

    @Test
    public void equals() {
        GradeMap gradeMap1 = new GradeMap();
        GradeMap gradeMap2 = new GradeMap();
        assertEquals(gradeMap1, gradeMap2);

        Assignments firstAssignment = Assignments.getAllAssignments()[0];
        Grade firstGrade = new Grade(VALID_GRADE_0);
        gradeMap1.put(firstAssignment, firstGrade);
        assertNotEquals(gradeMap1, gradeMap2);

        gradeMap2.put(firstAssignment, firstGrade);
        assertEquals(gradeMap1, gradeMap2);
    }

    @Test
    public void isValidGradeMap_validMap_returnsTrue() {
        LinkedHashMap<String, String> validMap = new LinkedHashMap<>();
        validMap.put(VALID_ASSIGNMENT_STR_0, VALID_GRADE_0);
        validMap.put(VALID_ASSIGNMENT_STR_1, VALID_GRADE_1);
        assertEquals(true, GradeMap.isValidGradeMap(validMap));
    }

    @Test
    public void isValidGradeMap_invalidMap_returnsFalse() {
        LinkedHashMap<String, String> invalidMap = new LinkedHashMap<>();
        invalidMap.put(VALID_ASSIGNMENT_STR_0, VALID_GRADE_0);
        invalidMap.put(INVALID_ASSIGNMENT_STR_0, VALID_GRADE_1);
        assertEquals(false, GradeMap.isValidGradeMap(invalidMap));

        LinkedHashMap<String, String> invalidMap1 = new LinkedHashMap<>();
        invalidMap1.put(VALID_ASSIGNMENT_STR_0, VALID_GRADE_0);
        invalidMap1.put(VALID_ASSIGNMENT_STR_1, INVALID_GRADE_0);
        assertEquals(false, GradeMap.isValidGradeMap(invalidMap1));
    }

    @Test
    public void getOverallGrade_noGrades_returnsEmptyGrade() {
        GradeMap gradeMap = new GradeMap();
        assertEquals(gradeMap.getOverallGrade().value, " ");
    }

    @Test
    public void getOverallGrade_validGrades_returnsCorrectGrade() {
        LinkedHashMap<Assignments, Grade> map = new LinkedHashMap<>();
        map.put(VALID_ASSIGNMENT_0, new Grade(VALID_GRADE_0));
        map.put(VALID_ASSIGNMENT_1, new Grade(VALID_GRADE_1));
        GradeMap gradeMap = new GradeMap(map);
        assertEquals(gradeMap.getOverallGrade().value, CORRECT_GRADE_0);
    }

    @Test
    public void formatGrades_emptyGrades_returnsEmptyString() {
        LinkedHashMap<Assignments, Grade> map = new LinkedHashMap<>();
        map.put(VALID_ASSIGNMENT_0, new Grade(" "));
        map.put(VALID_ASSIGNMENT_1, new Grade(" "));
        GradeMap gradeMap = new GradeMap(map);
        assertEquals(gradeMap.formatGrades(), "");
    }

    @Test
    public void formatGrades_validGrades_returnsFormattedString() {
        LinkedHashMap<Assignments, Grade> map = new LinkedHashMap<>();
        map.put(VALID_ASSIGNMENT_0, new Grade(VALID_GRADE_0));
        map.put(VALID_ASSIGNMENT_1, new Grade(" "));
        map.put(VALID_ASSIGNMENT_2, new Grade(VALID_GRADE_1));
        GradeMap gradeMap = new GradeMap(map);
        String expectedOutput = VALID_ASSIGNMENT_0 + ": " + VALID_GRADE_0 + ", "
                + VALID_ASSIGNMENT_2 + ": " + VALID_GRADE_1;
        assertEquals(gradeMap.formatGrades(), expectedOutput);
    }
}
