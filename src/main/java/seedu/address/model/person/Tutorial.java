package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Tutorial Group in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutorial(String)}
 */
public class Tutorial {

    public static final String MESSAGE_CONSTRAINTS = "Tutorial name should not be blank and must be alphanumeric.";

    public static final String VALIDATION_REGEX = "[A-Za-z0-9 ]+";

    public final String value;

    /**
     * Constructs a {@code Tutorial}.
     *
     * @param tutorial A valid tutorial name.
     */
    public Tutorial(String tutorial) {
        requireNonNull(tutorial);
        checkArgument(isValidTutorial(tutorial), MESSAGE_CONSTRAINTS);
        value = tutorial;
    }

    private boolean isValidTutorial(String test) {
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
        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return value.equals(otherTutorial.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
