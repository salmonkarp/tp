package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_AMBIGUOUS_COMMAND = "Ambiguous command";
    public static final String MESSAGE_MISSING_INDEX = "Missing index (must be a non-zero positive integer)";
    public static final String MESSAGE_NAME = "Name.";
    public static final String MESSAGE_PHONE = "Phone.";
    public static final String MESSAGE_EMAIL = "Email.";
    public static final String MESSAGE_TELEHANDLE = "Telehandle.";
    public static final String MESSAGE_TUTORIAL_GROUP = "Tutorial Group.";
    public static final String MESSAGE_MISSING_COMPULSORY_FIELDS = "Missing compulsory fields: ";
    public static final String MESSAGE_COMPULSORY_FIELDS = "Name, Phone, Email, Telehandle, Tutorial Group.";
    public static final String MESSAGE_MISSING_PREFIXES =
        "The following required field(s) are missing prefixes: %1$s";
    public static final String MESSAGE_MISSING_INDEX_AND_PREFIXES =
            "Missing index and required prefix(es): %1$s";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
        "The following prefix(es) can only be used once: ";
    public static final String MESSAGE_ENTERED_MULTIPLE_INDEXES =
            "Only one index should be specified.";
    public static final String MESSAGE_ENTERED_MULTIPLE_TUTORIAL_CLASS =
            "Only one tutorial class should be specified.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
            Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
            .append("; Phone: ")
            .append(person.getPhone())
            .append("; Email: ")
            .append(person.getEmail())
            .append("; TeleHandle: ")
            .append(person.getTeleHandle())
            .append("; Tutorial Group: ")
            .append(person.getTutorialGroup())
            .append("; GradeMap: ")
            .append(person.getGradeMap())
            .append("; AttendMap: ")
            .append(person.getAttendMapAsStringMap())
            .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
