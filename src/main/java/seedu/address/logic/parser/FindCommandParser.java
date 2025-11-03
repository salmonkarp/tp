package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_NO_INPUT;
import static seedu.address.logic.Messages.MESSAGE_NO_KEYWORD;
import static seedu.address.logic.Messages.MESSAGE_NO_PREFIX;
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
                String.format(MESSAGE_NO_INPUT, FindCommand.MESSAGE_USAGE));
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

        List<String> nameKeywords = splitToWords(argMultimap.getAllValues(CliSyntax.PREFIX_NAME));
        List<String> emailKeywords = splitToWords(argMultimap.getAllValues(CliSyntax.PREFIX_EMAIL));
        List<String> teleKeywords = splitToWords(argMultimap.getAllValues(CliSyntax.PREFIX_TELEHANDLE));
        List<String> tutorialGroups = filterAndTrim(argMultimap.getAllValues(CliSyntax.PREFIX_TUTORIAL_GROUP));

        boolean noPrefixedValues = nameKeywords.isEmpty()
                && emailKeywords.isEmpty()
                && teleKeywords.isEmpty()
                && tutorialGroups.isEmpty();

        if (noPrefixedValues) {
            // This handles the case where there are prefixes but no value written after them
            if (argMultimap.getSize() > 1) {
                throw new ParseException(
                    String.format(MESSAGE_NO_KEYWORD, FindCommand.MESSAGE_USAGE));
            }
            // This handles the case where there are no prefixes at all
            throw new ParseException(String.format(MESSAGE_NO_PREFIX, FindCommand.MESSAGE_USAGE));
        }
        List<String> allTutorialGroups = new ArrayList<>(tutorialGroups);
        List<String> tutorialGroupAdditions = getTutorialGroupAdditions(tutorialGroups);
        allTutorialGroups.addAll(tutorialGroupAdditions);
        return new FindCommand(new PersonContainsKeywords(nameKeywords, emailKeywords, teleKeywords, allTutorialGroups),
                isVerbose);
    }

    private static List<String> getTutorialGroupAdditions(List<String> tutorialGroups) {
        List<String> tutorialGroupAdditions = new ArrayList<>();
        for (String tutorialGroup : tutorialGroups) {
            if (tutorialGroup.length() == 3 && tutorialGroup.toUpperCase().startsWith("TG")) {
                try {
                    String numberPart = tutorialGroup.substring(2);
                    int parsedNumber = Integer.parseInt(numberPart);
                    tutorialGroupAdditions.add("TG" + String.format("%02d", parsedNumber));
                } catch (Exception e) {
                    continue; // invalid value, thus just skip
                }
            }
        }
        return tutorialGroupAdditions;
    }

    // Splits each segment into words based on whitespace and flattens the result into a single list
    private static List<String> splitToWords(List<String> segments) {
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
    private static List<String> filterAndTrim(List<String> segments) {
        if (segments == null || segments.isEmpty()) {
            return List.of();
        }
        // Trim each segment and filter out empty strings
        return segments.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
