package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsTest {

    @Test
    public void equals() {
        PersonContainsKeywords nameOnlyA =
                new PersonContainsKeywords(Collections.singletonList("first"), List.of(), List.of(), List.of());
        PersonContainsKeywords nameOnlyB =
                new PersonContainsKeywords(Collections.singletonList("second"), List.of(), List.of(), List.of());

        PersonContainsKeywords withEmail =
                new PersonContainsKeywords(List.of(), Collections.singletonList("gmail.com"), List.of(), List.of());
        PersonContainsKeywords withTele =
                new PersonContainsKeywords(List.of(), List.of(), Collections.singletonList("@handle"), List.of());
        PersonContainsKeywords withTg =
                new PersonContainsKeywords(List.of(), List.of(), List.of(), Collections.singletonList("TG1"));

        // same object -> true
        assertTrue(nameOnlyA.equals(nameOnlyA));

        // same values -> true
        PersonContainsKeywords nameOnlyACopy =
                new PersonContainsKeywords(Collections.singletonList("first"), List.of(), List.of(), List.of());
        assertTrue(nameOnlyA.equals(nameOnlyACopy));

        // different types -> false
        assertFalse(nameOnlyA.equals(1));

        // null -> false
        assertFalse(nameOnlyA.equals(null));

        // different predicate (different name list) -> false
        assertFalse(nameOnlyA.equals(nameOnlyB));

        // different fields populated -> false
        assertFalse(nameOnlyA.equals(withEmail));
        assertFalse(nameOnlyA.equals(withTele));
        assertFalse(nameOnlyA.equals(withTg));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // Single keyword
        PersonContainsKeywords predicate =
                new PersonContainsKeywords(Arrays.asList("Alice"), List.of(), List.of(), List.of());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywords(Arrays.asList("Alice", "Bob"), List.of(), List.of(), List.of());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywords(Arrays.asList("Bob", "Carol"), List.of(), List.of(), List.of());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords (substring case-insensitive)
        predicate = new PersonContainsKeywords(Arrays.asList("aLI", "bOB"), List.of(), List.of(), List.of());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PersonContainsKeywords predicate =
                new PersonContainsKeywords(Arrays.asList("Carol"), List.of(), List.of(), List.of());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Empty all fields -> false
        predicate = new PersonContainsKeywords(List.of(), List.of(), List.of(), List.of());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        PersonContainsKeywords predicate =
                new PersonContainsKeywords(List.of(), Arrays.asList("gmail.com"), List.of(), List.of());
        assertTrue(predicate.test(new PersonBuilder().withEmail("bob@gmail.com").build()));

        // Mixed case substring
        predicate = new PersonContainsKeywords(List.of(), Arrays.asList("GmAiL"), List.of(), List.of());
        assertTrue(predicate.test(new PersonBuilder().withEmail("bob@gmail.com").build()));
    }

    @Test
    public void test_teleHandleContainsKeywords_returnsTrue() {
        PersonContainsKeywords predicate =
                new PersonContainsKeywords(List.of(), List.of(), Arrays.asList("@car"), List.of());
        assertTrue(predicate.test(new PersonBuilder().withTeleHandle("@carol").build()));

        predicate = new PersonContainsKeywords(List.of(), List.of(), Arrays.asList("@ALICE"), List.of());
        assertTrue(predicate.test(new PersonBuilder().withTeleHandle("@alice").build()));
    }

    @Test
    public void test_tutorialGroupContainsKeywords_returnsTrue() {
        Person person = new PersonBuilder()
                .withName("X")
                .withTutorialGroup("TG1")
                .build();

        PersonContainsKeywords predicate =
                new PersonContainsKeywords(List.of(), List.of(), List.of(), List.of("TG1"));
        assertTrue(predicate.test(person));

        // Substring and case-insensitive
        predicate = new PersonContainsKeywords(List.of(), List.of(), List.of(), List.of("t"));
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_noFieldsMatch_returnsFalse() {
        Person person = new PersonBuilder()
                .withName("Alice")
                .withEmail("alice@outlook.com")
                .withTeleHandle("@alice")
                .withTutorialGroup("TG2")
                .build();


        PersonContainsKeywords predicate =
                new PersonContainsKeywords(
                        Arrays.asList("Bob"),
                        Arrays.asList("@nus.edu.sg"),
                        Arrays.asList("@bob"),
                        Arrays.asList("TG3"));
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        PersonContainsKeywords predicate = new PersonContainsKeywords(
                Arrays.asList("Alice"), List.of("gmail"), List.of("@al"), List.of("TG1"));
        String expected = PersonContainsKeywords.class.getCanonicalName()
                + "{nameKeywords=[Alice], "
                + "emailKeywords=[gmail], "
                + "teleHandleKeywords=[@al], "
                + "tutorialGroupKeywords=[TG1]}";

        assertEquals(expected, predicate.toString());
    }
}
