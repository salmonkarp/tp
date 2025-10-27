package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_ENTERED_MULTIPLE_TUTORIAL_CLASS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.person.TutorialClass.MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDICES_SIZE_3;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttendCommand;
import seedu.address.model.person.TutorialClass;

public class AttendCommandParserTest {

    private AttendCommandParser parser = new AttendCommandParser();

    @Test
    public void parse_validArgs_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        TutorialClass validTutClass = TutorialClass.t1;

        String userInput = targetIndex.getOneBased() + " " + PREFIX_TUTORIALCLASS + validTutClass.name();

        AttendCommand expectedCommand = new AttendCommand(targetIndex, validTutClass);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validIndices_success() {
        List<Index> indices = INDICES_SIZE_3;
        TutorialClass validTutClass = TutorialClass.t1;
        String userInput = indices.get(0).getOneBased() + " " + indices.get(1).getOneBased() + " "
            + indices.get(2).getOneBased() + " " + PREFIX_TUTORIALCLASS + validTutClass.name();
        AttendCommand expectedCommand = new AttendCommand(indices, validTutClass);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingTutorialClass_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " ";

        String expectedMessage = String.format(MESSAGE_MISSING_PREFIXES, PREFIX_TUTORIALCLASS)
                + "\n\n" + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String userInput = "a " + PREFIX_TUTORIALCLASS + "t1";
        String expectedMessage = MESSAGE_INVALID_INDEX;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidTutorialClass_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_TUTORIALCLASS + "t12";

        String expectedMessage = MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_extraArguments_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_TUTORIALCLASS + "t1 t2";
        String expectedMessage = MESSAGE_ENTERED_MULTIPLE_TUTORIAL_CLASS;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
