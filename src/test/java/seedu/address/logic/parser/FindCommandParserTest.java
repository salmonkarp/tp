package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_VERBOSE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywords;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Fallback: no prefixes -> treat as name keywords
        List<String> names = List.of("Alice", "Bob");
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywords(names, List.of(), List.of(), List.of()), false);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsWithPrefixes_returnsFindCommandMultiField() {
        List<String> names = List.of("Alice");
        List<String> emails = List.of("gmail.com");
        List<String> tele = List.of("@alice");
        List<String> tgs = List.of("Tutorial 1");

        FindCommand expected = new FindCommand(
                new PersonContainsKeywords(names, emails, tele, tgs), false);

        // Note: tele handle prefix is u/
        String input = " n/Alice e/gmail.com u/@alice tg/Tutorial 1";
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_validVerboseArgs_returnsVerboseFindCommand() {
        // no leading and trailing whitespaces
        List<String> names = List.of("Alice", "Bob");
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywords(names, List.of(), List.of(), List.of()), true);
        assertParseSuccess(parser, "Alice Bob" + SUFFIX_VERBOSE, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t" + SUFFIX_VERBOSE, expectedFindCommand);
    }

}
