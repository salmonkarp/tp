package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Assignments;
import seedu.address.model.person.Grade;

/**
 * Parses input arguments and creates a new GradeCommand object
 */
public class GradeCommandParser implements Parser<GradeCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the GradeCommand
     * and returns a GradeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_GRADE, PREFIX_ASSIGNMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRADE, PREFIX_ASSIGNMENT) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE));
        }

        Index index;
        Grade grade;
        Assignments assignment;

        // removed try-catch block here as by right, all the parsing errors will be handled in the respective
        // ParserUtil methods called below and throw ParseException with relevant messages

        index = ParserUtil.parseIndex(argMultimap.getPreamble());

        String gradeString = argMultimap.getValue(PREFIX_GRADE).orElse("");
        grade = ParserUtil.parseGrade(gradeString);

        String assignmentName = argMultimap.getValue(PREFIX_ASSIGNMENT).orElse("");
        assignment = ParserUtil.parseAssignment(assignmentName);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_GRADE, PREFIX_ASSIGNMENT);

        return new GradeCommand(index, grade, assignment);
    }

}
