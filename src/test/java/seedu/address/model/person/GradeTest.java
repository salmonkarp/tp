package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        String invalidGrade = "";
        assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGrade));
    }

    @Test
    public void isValidGrade() {
        // null Grade number
        assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // invalid Grade numbers
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only
        assertFalse(Grade.isValidGrade("91")); // does not have 2 decimal place (d.p.)
        assertFalse(Grade.isValidGrade("91.1")); // does not have 2 d.p.
        assertFalse(Grade.isValidGrade("100.23")); // above 100.00
        assertFalse(Grade.isValidGrade("grade")); // non-numeric
        assertFalse(Grade.isValidGrade("9A.12")); // alphabets within digits
        assertFalse(Grade.isValidGrade("7 2.23")); // spaces within digits

        // valid Grade numbers
        assertTrue(Grade.isValidGrade("67.12")); // within 0 to 100 and has 2 d.p.
        assertTrue(Grade.isValidGrade("80.86"));
        assertTrue(Grade.isValidGrade("100.00")); // accepts 100.00 exact
        assertTrue(Grade.isValidGrade("0.00")); // accepts 0.00 exact
    }

    @Test
    public void equals() {
        Grade grade = new Grade("81.23");

        // same values -> returns true
        assertTrue(grade.equals(new Grade("81.23")));

        // same object -> returns true
        assertTrue(grade.equals(grade));

        // null -> returns false
        assertFalse(grade.equals(null));

        // different types -> returns false
        assertFalse(grade.equals(11.2f));

        // different values -> returns false
        assertFalse(grade.equals(new Grade("50.53")));
    }
}
