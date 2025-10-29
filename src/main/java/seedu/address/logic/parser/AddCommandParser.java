package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEHANDLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AttendMap;
import seedu.address.model.person.Email;
import seedu.address.model.person.GradeMap;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TeleHandle;
import seedu.address.model.person.TutorialGroup;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME,
                        PREFIX_PHONE,
                        PREFIX_EMAIL,
                        PREFIX_TELEHANDLE,
                        PREFIX_TUTORIAL_GROUP,
                        PREFIX_TAG);

        Prefix[] compulsoryPrefixes = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_TELEHANDLE, PREFIX_TUTORIAL_GROUP};
        List<Prefix> missingPrefixes = Stream.of(compulsoryPrefixes)
                .filter(prefix -> argMultimap.getValue(prefix).isEmpty())
                .toList();

        if (!missingPrefixes.isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            String errorMessage = "";
            if (!missingPrefixes.isEmpty()) {
                String missingFields = missingPrefixes.stream()
                        .map(this::getPrefixName)
                        .collect(Collectors.joining(", "));
                errorMessage = "Missing compulsory fields: " + missingFields + ".\n\n";
            }
            throw new ParseException(errorMessage
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_TELEHANDLE, PREFIX_TUTORIAL_GROUP);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        TeleHandle teleHandle = ParserUtil.parseTeleHandle(argMultimap.getValue(PREFIX_TELEHANDLE).get());
        String tutorialGroupString = argMultimap.getValue(PREFIX_TUTORIAL_GROUP)
                .orElse(TutorialGroup.DEFAULT_TUTORIAL_GROUP);
        TutorialGroup tutorialGroup = ParserUtil.parseTutorialGroup(tutorialGroupString);
        GradeMap defaultGradeMap = new GradeMap();
        AttendMap defaultAttendMap = new AttendMap();
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name,
                phone,
                email,
                teleHandle,
                tutorialGroup,
                defaultGradeMap,
                defaultAttendMap,
                tagList);

        return new AddCommand(person);
    }

    /**
     * Returns the name of the prefix.
     * @param prefix The prefix to get the name of.
     * @return The name of the prefix.
     */
    private String getPrefixName(Prefix prefix) {
        if (prefix.equals(PREFIX_NAME)) {
            return "Name";
        } else if (prefix.equals(PREFIX_PHONE)) {
            return "Phone";
        } else if (prefix.equals(PREFIX_EMAIL)) {
            return "Email";
        } else if (prefix.equals(PREFIX_TELEHANDLE)) {
            return "Telehandle";
        } else if (prefix.equals(PREFIX_TUTORIAL_GROUP)) {
            return "Tutorial Group";
        }
        // Should not be reached with compulsory prefixes
        return "";
    }
}
