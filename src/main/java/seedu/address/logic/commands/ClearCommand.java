package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.OpenAlert;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String FUZZY_COMMAND_WORD = "clearr";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    /**
     * Functional interface for confirmation dialog. Tests can implement this to simulate user confirmation.
     */
    public interface ConfirmationProvider {
        boolean confirm(String title, String content);
    }

    private final ConfirmationProvider confirmationProvider;

    public ClearCommand() {
        this(OpenAlert::showAlert);
    }

    public ClearCommand(ConfirmationProvider confirmationProvider) {
        this.confirmationProvider = confirmationProvider;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        boolean isConfirmed = confirmationProvider.confirm(
                "Confirm Clear",
                "All data will be lost. Are you sure you want to proceed?"
        );
        if (isConfirmed) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult("Clear command cancelled.");
    }
}
