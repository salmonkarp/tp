package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_VERBOSE;

import java.util.Locale;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String userInput) throws ParseException {
        boolean isVerbose = false;

        String trimmed = userInput == null ? "" : userInput.trim();

        // if trimmed ends with "/v", treat as verbose and remove it
        if (trimmed.endsWith(SUFFIX_VERBOSE)) {
            isVerbose = true;
            trimmed = trimmed.substring(0, trimmed.length() - SUFFIX_VERBOSE.length());
        }

        // if no arguments, default to sorting by name ascending
        if (trimmed.isEmpty()) {
            return new SortCommand(SortCommand.Field.NAME, SortCommand.Order.ASC, isVerbose);
        }

        String[] tokens = trimmed.split("\\s+");
        // either 1 or 2 "words" are expected: field and optional order
        if (tokens.length < 1 || tokens.length > 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        SortCommand.Field field = parseField(tokens[0]);
        SortCommand.Order order = tokens.length == 2 ? parseOrder(tokens[1]) : SortCommand.Order.ASC;

        assert field != null : "Field should not be null";
        assert order != null : "Order should not be null";

        return new SortCommand(field, order, isVerbose);
    }

    // checks and parses the field to sort by
    // if invalid field, throws ParseException
    private SortCommand.Field parseField(String fieldStr) throws ParseException {
        // we can consider expanding the number of ways to specify fields later
        switch (fieldStr.toLowerCase(Locale.ROOT)) {
        case "name":
            return SortCommand.Field.NAME;
        case "tutorial":
            return SortCommand.Field.TUTORIAL;
        case "grade":
            return SortCommand.Field.GRADE;
        case "attendance":
            return SortCommand.Field.ATTENDANCE;
        default:
            throw new ParseException(String.format("Invalid Field!\n" + SortCommand.MESSAGE_USAGE));
        }
    }

    // checks and parses the order to sort by
    // if invalid order, throws ParseException
    private SortCommand.Order parseOrder(String orderStr) throws ParseException {
        if (orderStr == null) {
            return SortCommand.Order.ASC; // default order
        }
        switch (orderStr.toLowerCase(Locale.ROOT)) {
        case "asc":
            return SortCommand.Order.ASC;
        case "desc":
            return SortCommand.Order.DESC;
        default:
            throw new ParseException(String.format("Invalid Order!\n" + SortCommand.MESSAGE_USAGE));
        }
    }
}
