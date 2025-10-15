package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class GradeMapTest {

    public static final String VALID_GRADE_0 = "85.00";
    public static final String VALID_GRADE_1 = "90.00";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GradeMap((HashMap<GradeTypes, Grade>) null));
    }

    @Test
    public void constructor_success() {
        GradeMap gradeMap = new GradeMap();
        for (GradeTypes gradeType : GradeTypes.getAllAssignments()) {
            assertTrue(gradeMap.containsKey(gradeType));
            assertEquals(" ", gradeMap.get(gradeType).value);
        }
    }

    @Test
    public void putAndGet_success() {
        GradeMap gradeMap = new GradeMap();
        GradeTypes firstAssignment = GradeTypes.getAllAssignments()[0];
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
        GradeTypes firstAssignment = GradeTypes.getAllAssignments()[0];
        assertThrows(IllegalArgumentException.class, () -> gradeMap.put(firstAssignment, null));
    }

    @Test
    public void get_invalidKey_throwsIllegalArgumentException() {
        GradeMap gradeMap = new GradeMap();
        assertThrows(IllegalArgumentException.class, () -> gradeMap.get("InvalidKey"));
    }

    @Test
    public void equals() {
        GradeMap gradeMap1 = new GradeMap();
        GradeMap gradeMap2 = new GradeMap();
        assertEquals(gradeMap1, gradeMap2);

        GradeTypes firstAssignment = GradeTypes.getAllAssignments()[0];
        Grade firstGrade = new Grade(VALID_GRADE_0);
        gradeMap1.put(firstAssignment, firstGrade);
        assertNotEquals(gradeMap1, gradeMap2);

        gradeMap2.put(firstAssignment, firstGrade);
        assertEquals(gradeMap1, gradeMap2);
    }

    @Test
    public void getOverallGrade_noGrades_returnsEmptyGrade() {
        GradeMap gradeMap = new GradeMap();
        assert(gradeMap.getOverallGrade().value.equals(" "));
    }
}
