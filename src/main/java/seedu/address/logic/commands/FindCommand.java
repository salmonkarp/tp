package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEHANDLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds students by name, email, telegram handle, "
            + "or tutorial group (tag).\n"
            + "Parameters:\n  "
            + PREFIX_NAME + "KEYWORD [MORE_KEYWORDS]...    Name keywords\n  "
            + PREFIX_EMAIL + "KEYWORD [MORE_KEYWORDS]...    Email keywords\n  "
            + PREFIX_TELEHANDLE + "KEYWORD [MORE_KEYWORDS]...    Telegram handle keywords\n  "
            + PREFIX_TUTORIAL_GROUP + "TUTORIAL_GROUP [...repeat]   Tutorial group tag, e.g. tg/TG01\n"
            + "Note: At least 1 prefix + keyword must be provided.\n"
            + "Examples:\n  "
            + COMMAND_WORD + " " + PREFIX_NAME + "alice bob\n  "
            + COMMAND_WORD + " " + PREFIX_EMAIL + "gmail.com\n  "
            + COMMAND_WORD + " " + PREFIX_TELEHANDLE + "@alice " + PREFIX_TUTORIAL_GROUP + "TG01";

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
