package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_ENTERED_MULTIPLE_INDEXES;
import static seedu.address.logic.Messages.MESSAGE_ENTERED_MULTIPLE_TUTORIAL_CLASS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_INDEX;
import static seedu.address.logic.Messages.MESSAGE_MISSING_INDEX_AND_PREFIXES;
import static seedu.address.logic.Messages.MESSAGE_MISSING_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnattendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TutorialClass;

/**
 * Parses input arguments and creates a new UnattendCommand object
 */
public class UnattendCommandParser implements Parser<UnattendCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix)
                .isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the UnattendCommand
     * and returns an UnattendCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnattendCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        TutorialClass tutClass;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TUTORIALCLASS);
        boolean arePrefixesMissing = !arePrefixesPresent(argMultimap, PREFIX_TUTORIALCLASS);
        String preamble = argMultimap.getPreamble();

        if (argMultimap.getPreamble().isEmpty()) {
            String commandFormatSection = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnattendCommand.MESSAGE_USAGE);
            String checkIndexAndPrefixIfEmptySection;
            if (arePrefixesMissing) {
                // No index and no prefix.
                checkIndexAndPrefixIfEmptySection = String.format(MESSAGE_MISSING_INDEX_AND_PREFIXES,
                        PREFIX_TUTORIALCLASS.getPrefix());
            } else {
                // No index but prefix is present.
                checkIndexAndPrefixIfEmptySection = MESSAGE_MISSING_INDEX;
            }
            throw new ParseException(checkIndexAndPrefixIfEmptySection + "\n\n" + commandFormatSection);
        }

        // Index present but prefix missing
        if (arePrefixesMissing) {
            String missingPrefixesSection = String.format(
                    MESSAGE_MISSING_PREFIXES,
                    PREFIX_TUTORIALCLASS.getPrefix()
            );
            String commandFormatSection = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnattendCommand.MESSAGE_USAGE);
            throw new ParseException(missingPrefixesSection + "\n\n" + commandFormatSection);
        }

        String[] parts = preamble.split("\\s+");

        // Removed try-catch block here as by right, all the parsing errors will be handled in the respective.
        // ParserUtil methods called below and throw ParseException with relevant messages.
        index = ParserUtil.parseIndex(parts[0]);

        if (parts.length > 1) {
            throw new ParseException(MESSAGE_ENTERED_MULTIPLE_INDEXES);
        }

        assert index.getOneBased() > 0;

        String tutClassName = argMultimap.getValue(PREFIX_TUTORIALCLASS).orElse("");

        // If user entered multiple tutorial class after the /c.
        if (tutClassName.split("\\s+").length > 1) {
            throw new ParseException(MESSAGE_ENTERED_MULTIPLE_TUTORIAL_CLASS);
        }

        String tutClassNameLower = tutClassName.toLowerCase();
        tutClass = ParserUtil.parseTutorialClass(tutClassNameLower);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIALCLASS);

        return new UnattendCommand(index, tutClass);
    }

}
