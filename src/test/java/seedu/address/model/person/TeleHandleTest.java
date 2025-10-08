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
    public void constructor_invalidTeleHandle_throwsIllegalArgumentException() {
        String invalidTeleHandle = "";
        assertThrows(IllegalArgumentException.class, () -> new TeleHandle(invalidTeleHandle));
    }

    @Test
    public void isValidTeleHandle() {
        // null teleHandle
        assertThrows(NullPointerException.class, () -> TeleHandle.isValidTeleHandle(null));

        // invalid teleHandles
        assertFalse(TeleHandle.isValidTeleHandle("david")); // missing '@' prefix
        assertFalse(TeleHandle.isValidTeleHandle("")); // empty string

        // valid teleHandles
        assertTrue(TeleHandle.isValidTeleHandle("@david"));
        assertTrue(TeleHandle.isValidTeleHandle("@-")); // one character
        assertTrue(TeleHandle.isValidTeleHandle("@d4vd420")); // long address
    }

    @Test
    public void equals() {
        TeleHandle teleHandle = new TeleHandle("@valid");

        // same values -> returns true
        assertTrue(teleHandle.equals(new TeleHandle("@valid")));

        // same object -> returns true
        assertTrue(teleHandle.equals(teleHandle));

        // null -> returns false
        assertFalse(teleHandle.equals(null));

        // different types -> returns false
        assertFalse(teleHandle.equals(5.0f));

        // different values -> returns false
        assertFalse(teleHandle.equals(new TeleHandle("@otherValid")));
    }
}
