package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Telegram Handle in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTeleHandle(String)}
 */
public class TeleHandle {

    public static final String MESSAGE_CONSTRAINTS = "TeleHandle starts with @ and does not have any spacing, "
        + "and it should not be blank";

    /*
     * The first character of the TeleHandle must be a '@' and the TeleHandle must contain at least one character.,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "( |@\\S+)";

    public final String value;

    /**
     * Constructs an {@code TeleHandle}.
     *
     * @param teleHandle A valid address.
     */
    public TeleHandle(String teleHandle) {
        requireNonNull(teleHandle);
        checkArgument(isValidTeleHandle(teleHandle), MESSAGE_CONSTRAINTS);
        value = teleHandle;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidTeleHandle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TeleHandle)) {
            return false;
        }

        TeleHandle otherTeleHandle = (TeleHandle) other;
        return value.equals(otherTeleHandle.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
