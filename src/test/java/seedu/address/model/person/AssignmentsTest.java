package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AssignmentsTest {

    public static final String INVALID_ASSIGNMENT = "InvalidAssignment";

    @Test
    public void fromString_validInput_success() {
        for (Assignments gradeType : Assignments.values()) {
            assert(Assignments.fromString(gradeType.name()) == gradeType);
        }
    }

    @Test
    public void fromString_invalidInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Assignments.fromString(INVALID_ASSIGNMENT));
    }
}
