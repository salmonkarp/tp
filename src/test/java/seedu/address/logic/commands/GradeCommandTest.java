package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_Q1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Assignments;
import seedu.address.model.person.Grade;

public class GradeCommandTest {

    @Test
    public void equals() {
        final GradeCommand standardCommand = new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(VALID_GRADE_AMY),
                Assignments.fromString(VALID_ASSIGNMENT_Q1));

        // same values -> returns true
        GradeCommand commandWithSameValues = new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(VALID_GRADE_AMY),
                Assignments.fromString(VALID_ASSIGNMENT_Q1));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new GradeCommand(INDEX_SECOND_PERSON,
                new Grade(VALID_GRADE_AMY),
                Assignments.fromString(VALID_ASSIGNMENT_Q1))));

        // different grade -> returns false
        assertFalse(standardCommand.equals(new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(VALID_GRADE_BOB),
                Assignments.fromString(VALID_ASSIGNMENT_Q1))));
    }
}
