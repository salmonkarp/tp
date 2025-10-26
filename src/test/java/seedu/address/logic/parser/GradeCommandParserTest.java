package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.model.person.Assignments;
import seedu.address.model.person.Grade;

public class GradeCommandParserTest {
    private GradeCommandParser parser = new GradeCommandParser();
    private final String nonEmptyGrade = "73.25";
    private final String validAssignment = "Q1";

    @Test
    public void parse_indexSpecified_success() {
        // have grade
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_GRADE
                + nonEmptyGrade
                + " " + PREFIX_ASSIGNMENT
                + validAssignment;
        GradeCommand expectedCommand = new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(nonEmptyGrade),
                Assignments.fromString(validAssignment));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMissingIndexSection = MESSAGE_EMPTY_INDEX;
        String expectedFormatSection = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE);
        String expectedMissingGrade = String.format(
                MESSAGE_MISSING_PREFIXES,
                PREFIX_GRADE + " "
        ) + "\n\n" + expectedFormatSection;
        String expectedMissingAssignment = String.format(
                MESSAGE_MISSING_PREFIXES,
                PREFIX_ASSIGNMENT + " "
        ) + "\n\n" + expectedFormatSection;

        // no parameters at all
        assertParseFailure(parser,
                "",
                expectedMissingIndexSection + "\n\n" + expectedFormatSection);

        // no grade
        assertParseFailure(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_ASSIGNMENT + validAssignment,
                expectedMissingGrade);

        // no assignment
        assertParseFailure(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_GRADE + nonEmptyGrade,
                expectedMissingAssignment);
    }
}
