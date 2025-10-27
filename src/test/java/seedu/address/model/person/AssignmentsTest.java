package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AssignmentsTest {

    public static final String INVALID_ASSIGNMENT = "InvalidAssignment";
    public static final String VALID_ASSIGNMENT_Q1_CASE_INSENSITIVE = "q1";
    public static final String VALID_ASSIGNMENT_FINALS_CASE_INSENSITIVE = "fInAlS";

    @Test
    public void fromString_validInput_success() {
        for (Assignments gradeType : Assignments.values()) {
            assertEquals(Assignments.fromString(gradeType.name()), gradeType);
        }
    }

    @Test
    public void fromString_caseInsensitiveInput_success() {
        assertEquals(Assignments.Q1, Assignments.fromString(VALID_ASSIGNMENT_Q1_CASE_INSENSITIVE));
        assertEquals(Assignments.Finals, Assignments.fromString(VALID_ASSIGNMENT_FINALS_CASE_INSENSITIVE));
    }

    @Test
    public void fromString_invalidInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Assignments.fromString(INVALID_ASSIGNMENT));
    }

    @Test
    public void isValidAssignment() {
        // valid assignments
        for (Assignments assignment : Assignments.values()) {
            assertTrue(Assignments.isValidAssignment(assignment.name()));
            assertTrue(Assignments.isValidAssignment(assignment.name().toLowerCase()));
            assertTrue(Assignments.isValidAssignment(assignment.name().toUpperCase()));
        }

        // invalid assignments
        assertFalse(Assignments.isValidAssignment(INVALID_ASSIGNMENT));
        assertFalse(Assignments.isValidAssignment(""));
    }

    @Test
    public void getAllAssignments_returnsAllAssignments() {
        Assignments[] allAssignments = Assignments.getAllAssignments();
        assertEquals(allAssignments.length, Assignments.values().length);
        for (int i = 0; i < allAssignments.length; i++) {
            assertEquals(allAssignments[i], Assignments.values()[i]);
        }
    }
}
