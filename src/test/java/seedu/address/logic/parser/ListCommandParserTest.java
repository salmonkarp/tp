package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.SUFFIX_VERBOSE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(false);
        ListCommand expectedListCommand1 = new ListCommand(true);

        assertParseSuccess(parser, " ", expectedListCommand);
        assertParseSuccess(parser, "redundant", expectedListCommand);
        assertParseSuccess(parser, SUFFIX_VERBOSE, expectedListCommand1);
    }
}
