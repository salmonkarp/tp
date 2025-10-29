package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_NO_INPUT;
import static seedu.address.logic.Messages.MESSAGE_NO_KEYWORD;
import static seedu.address.logic.Messages.MESSAGE_NO_PREFIX;
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
        assertParseFailure(parser, "     ", String.format(MESSAGE_NO_INPUT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " Alice", String.format(MESSAGE_NO_PREFIX, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n\bob", String.format(MESSAGE_NO_PREFIX, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " tgg/01", String.format(MESSAGE_NO_PREFIX, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/", String.format(MESSAGE_NO_KEYWORD, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNames_returnsFindCommand() {
        List<String> names = List.of("Alice", "Bob");
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywords(names, List.of(), List.of(), List.of()), false);
        FindCommand expectedFindCommand1 =
                new FindCommand(new PersonContainsKeywords(names, List.of(), List.of(), List.of()), true);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, " n/Alice Bob " + SUFFIX_VERBOSE, expectedFindCommand1);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t  " + SUFFIX_VERBOSE, expectedFindCommand1);
    }

    @Test
    public void parse_validMultiArgs_returnsFindCommandMultiField() {
        List<String> names = List.of("Alice");
        List<String> emails = List.of("gmail.com");
        List<String> tele = List.of("@alice");
        List<String> tgs = List.of("TG01");

        FindCommand expected = new FindCommand(
                new PersonContainsKeywords(names, emails, tele, tgs), false);
        FindCommand expected1 = new FindCommand(
                new PersonContainsKeywords(names, emails, tele, tgs), true);

        // Note: tele handle prefix is u/
        String input = " n/Alice e/gmail.com u/@alice tg/TG01";
        String input1 = " n/Alice e/gmail.com u/@alice tg/TG01 " + SUFFIX_VERBOSE;

        assertParseSuccess(parser, input, expected);
        assertParseSuccess(parser, input1, expected1);
    }

}
