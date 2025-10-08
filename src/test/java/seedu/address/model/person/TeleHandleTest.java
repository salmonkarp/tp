package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TeleHandleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TeleHandle(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new TeleHandle(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> TeleHandle.isValidTeleHandle(null));

        // invalid addresses
        assertFalse(TeleHandle.isValidTeleHandle("")); // empty string
        assertFalse(TeleHandle.isValidTeleHandle(" ")); // spaces only

        // valid teleHandles
        assertTrue(TeleHandle.isValidTeleHandle("@david"));
        assertTrue(TeleHandle.isValidTeleHandle("@-")); // one character
        assertTrue(TeleHandle.isValidTeleHandle("@d4vd420")); // long address
    }

    @Test
    public void equals() {
        TeleHandle teleHandle = new TeleHandle("Valid TeleHandle");

        // same values -> returns true
        assertTrue(teleHandle.equals(new TeleHandle("Valid TeleHandle")));

        // same object -> returns true
        assertTrue(teleHandle.equals(teleHandle));

        // null -> returns false
        assertFalse(teleHandle.equals(null));

        // different types -> returns false
        assertFalse(teleHandle.equals(5.0f));

        // different values -> returns false
        assertFalse(teleHandle.equals(new TeleHandle("Other Valid TeleHandle")));
    }
}
