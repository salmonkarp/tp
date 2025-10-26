package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AssignmentsTest {

    public static final String INVALID_ASSIGNMENT = "InvalidAssignment";
    public static final String VALID_ASSIGNMENT_Q1_CASE_INSENSITIVE = "q1";
    public static final String VALID_ASSIGNMENT_FINALS_CASE_INSENSITIVE = "fInAlS";

    @Test
    public void fromString_validInput_success() {
        for (Assignments gradeType : Assignments.values()) {
            assert(Assignments.fromString(gradeType.name()) == gradeType);
        }
    }

    @Test
    public void fromString_caseInsensitiveInput_success() {
        assert(Assignments.fromString(VALID_ASSIGNMENT_Q1_CASE_INSENSITIVE) == Assignments.Q1);
        assert(Assignments.fromString(VALID_ASSIGNMENT_FINALS_CASE_INSENSITIVE) == Assignments.Finals);
    }

    @Test
    public void fromString_invalidInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Assignments.fromString(INVALID_ASSIGNMENT));
    }

    @Test
    public void isValidAssignment() {
        // valid assignments
        for (Assignments assignment : Assignments.values()) {
            assert(Assignments.isValidAssignment(assignment.name()));
            assert(Assignments.isValidAssignment(assignment.name().toLowerCase()));
            assert(Assignments.isValidAssignment(assignment.name().toUpperCase()));
        }

        // invalid assignments
        assert(!Assignments.isValidAssignment(INVALID_ASSIGNMENT));
        assert(!Assignments.isValidAssignment(""));
    }

    @Test
    public void getAllAssignments_returnsAllAssignments() {
        Assignments[] allAssignments = Assignments.getAllAssignments();
        assert(allAssignments.length == Assignments.values().length);
        for (int i = 0; i < allAssignments.length; i++) {
            assert(allAssignments[i] == Assignments.values()[i]);
        }
    }
}
