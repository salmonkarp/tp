package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.SUFFIX_VERBOSE;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        boolean isVerbose = trimmedArgs.endsWith(SUFFIX_VERBOSE); // if trimmedArgs ends with "/v", treat as verbose
        return new ListCommand(isVerbose);
    }
}
