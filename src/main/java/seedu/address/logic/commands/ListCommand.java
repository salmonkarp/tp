package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String FUZZY_COMMAND_WORD = "listt";
    public static final String MESSAGE_SUCCESS = "Listed all students.";

    private final boolean isVerbose;

    public ListCommand(boolean isVerbose) {
        this.isVerbose = isVerbose;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, isVerbose);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherListCommand = (ListCommand) other;
        return isVerbose == otherListCommand.isVerbose;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isVerbose", isVerbose)
                .toString();
    }
}
