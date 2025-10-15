package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.GradeTypes;

public class GradeTypesTest {

    public static final String INVALID_ASSIGNMENT = "InvalidAssignment";

    @Test
    public void fromString_validInput_success() {
        for (GradeTypes gradeType : GradeTypes.values()) {
            assert(GradeTypes.fromString(gradeType.name()) == gradeType);
        }
    }

    @Test
    public void fromString_invalidInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> GradeTypes.fromString(INVALID_ASSIGNMENT));
    }
}
