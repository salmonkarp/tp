package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Assigns a grade to an existing person in the address book.
 */
public class GradeCommand extends Command {

    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the grade to the person identified "
            + "by the index number used in the last person listing. "
            + "Existing grade will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "g/[GRADE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "g/87.50";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Grade: %2$s";

    private final Index index;
    private final String grade;

    /**
     * Creates a GradeCommand to grade the person at the specified {@code index}
     * with the specified {@code grade}.
     * This constructor is used for testing only.
     */
    public GradeCommand() {
        index = null;
        grade = null;
    }

    /**
     * @param index of the person in the filtered person list to assign the grade to
     * @param grade of the person to be assigned to
     */
    public GradeCommand(Index index, String grade) {
        requireAllNonNull(index, grade);

        this.index = index;
        this.grade = grade;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from grade");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradeCommand)) {
            return false;
        }

        GradeCommand e = (GradeCommand) other;
        return index.equals(e.index)
                && grade.equals(e.grade);
    }
}
