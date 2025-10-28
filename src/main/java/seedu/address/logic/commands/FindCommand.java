package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywords;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String FUZZY_COMMAND_WORD = "findd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons by name, email, telegram handle, "
            + "or tutorial group (tag).\n"
            + "Parameters:\n"
            + "  n/KEYWORD [MORE_KEYWORDS]...    Name keywords\n"
            + "  e/KEYWORD [MORE_KEYWORDS]...    Email keywords\n"
            + "  u/KEYWORD [MORE_KEYWORDS]...    Telegram handle keywords\n"
            + "  tg/TUTORIAL_GROUP [...repeat]   Tutorial group tag, e.g. tg/TG01\n"
            + "At least 1 prefix must be provided.\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " n/alice bob\n"
            + "  " + COMMAND_WORD + " e/gmail.com\n"
            + "  " + COMMAND_WORD + " u/@alice tg/TG01";

    private final PersonContainsKeywords predicate;
    private final boolean isVerbose;

    /**
     * Creates a FindCommand to find persons matching the given predicate.
     */
    public FindCommand(PersonContainsKeywords predicate, boolean isVerbose) {
        this.predicate = predicate;
        this.isVerbose = isVerbose;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                false, false, isVerbose);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate) && (isVerbose == otherFindCommand.isVerbose);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("predicate", predicate)
            .add("isVerbose", isVerbose)
            .toString();
    }
}
