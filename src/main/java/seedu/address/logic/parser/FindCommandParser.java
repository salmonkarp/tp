package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_VERBOSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywords;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        boolean isVerbose = false;

        // only trim trailing spaces to preserve leading spaces for prefix detection in ArgumentMultimap
        String trimmedArgs = args.stripTrailing();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // if trimmedArgs ends with "/v", treat as verbose and remove it
        if (trimmedArgs.endsWith(SUFFIX_VERBOSE)) {
            isVerbose = true;
            trimmedArgs = trimmedArgs.substring(0, trimmedArgs.length() - SUFFIX_VERBOSE.length());
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(trimmedArgs,
                        CliSyntax.PREFIX_NAME,
                        CliSyntax.PREFIX_EMAIL,
                        CliSyntax.PREFIX_TELEHANDLE,
                        CliSyntax.PREFIX_TUTORIAL_GROUP
                );

        List<String> nameKeywords = words(argMultimap.getAllValues(CliSyntax.PREFIX_NAME));
        List<String> emailKeywords = words(argMultimap.getAllValues(CliSyntax.PREFIX_EMAIL));
        List<String> teleKeywords = words(argMultimap.getAllValues(CliSyntax.PREFIX_TELEHANDLE));
        List<String> tutorialGroups = values(argMultimap.getAllValues(CliSyntax.PREFIX_TUTORIAL_GROUP));

        boolean noPrefixedValues = nameKeywords.isEmpty()
                && emailKeywords.isEmpty()
                && teleKeywords.isEmpty()
                && tutorialGroups.isEmpty();

        if (noPrefixedValues) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywords(nameKeywords, emailKeywords, teleKeywords, tutorialGroups),
                isVerbose);
    }

    // Splits each segment into words based on whitespace and flattens the result into a single list
    private static List<String> words(List<String> segments) {
        if (segments == null || segments.isEmpty()) {
            return List.of();
        }
        List<String> out = new ArrayList<>();
        for (String seg : segments) {
            if (seg == null) {
                continue;
            }
            out.addAll(Arrays.stream(seg.trim().split("\\s+"))
                    .filter(s -> !s.isEmpty())
                    .toList());
        }
        return out;
    }

    // Trims each segment and collects non-empty ones into a list
    private static List<String> values(List<String> segments) {
        if (segments == null || segments.isEmpty()) {
            return List.of();
        }
        return segments.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
