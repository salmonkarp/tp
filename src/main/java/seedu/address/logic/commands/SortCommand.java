package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Locale;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the students in the address book by a specified field.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String FUZZY_COMMAND_WORD = "sortt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort students in the address book by a field\n"
            + "Parameters: FIELD [ORDER]\n"
            + "Fields: name | tutorial | grade | attendance\n"
            + "Order (optional): asc | desc (default is asc)\n"
            + "Example: " + COMMAND_WORD + " grade desc\n"
            + "         " + COMMAND_WORD + " name";
    private static final String MESSAGE_SUCCESS = "Students sorted by %s in %s order.";

    /**
     * The field to sort by: Name, Tutorial, Grade, or Attendance
     */
    public enum Field {
        NAME, TUTORIAL, GRADE, ATTENDANCE
    }

    /**
     * The order in which to sort: Ascending or Descending
     */
    public enum Order {
        ASC, DESC;
        @Override
        public String toString() {
            return this == ASC ? "Ascending" : "Descending";
        }
    }

    private final Field field;
    private final Order order;
    private final boolean isVerbose;

    /**
     * Creates a SortCommand to sort the students by the specified field and order.
     */
    public SortCommand(Field field, Order order, boolean isVerbose) {
        this.field = requireNonNull(field);
        this.order = requireNonNull(order);
        this.isVerbose = isVerbose;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Comparator<Person> comparator = buildComparator(field);
        assert comparator != null : "Comparator should not be null";
        model.sortPersonList(comparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                field.toString().toLowerCase(Locale.ROOT),
                order.toString().toLowerCase(Locale.ROOT)),
                false, false, isVerbose);
    }

    // Builds a comparator based on the specified field and order
    private Comparator<Person> buildComparator(Field compareField) {
        Comparator<Person> comparator;
        switch (compareField) {
        case NAME:
            comparator = Comparator.comparing(p -> p.getName().toString().toLowerCase(Locale.ROOT));
            break;
        case TUTORIAL:
            comparator = Comparator.comparing(p -> p.getTutorialGroup().getTutorialNumber());
            break;
        case GRADE:
            comparator = Comparator.comparing(p -> p.getOverallGrade().valueFloat);
            break;
        case ATTENDANCE:
            comparator = Comparator.comparing(p -> p.getAttendMap().getAttendanceRate());
            break;
        default:
            // default to sorting by name if field is not specified
            // (if an unmatched field is given, parser should have caught it)
            return Comparator.comparing(p -> p.getName().toString().toLowerCase(Locale.ROOT));
        }

        if (order == Order.DESC) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SortCommand)) {
            return false;
        }
        SortCommand otherCommand = (SortCommand) other;
        return field == otherCommand.field && order == otherCommand.order && isVerbose == otherCommand.isVerbose;
    }

}
