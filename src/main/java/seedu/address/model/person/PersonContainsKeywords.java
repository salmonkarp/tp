package seedu.address.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields contain any of the keywords given.
 */
public class PersonContainsKeywords implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> emailKeywords;
    private final List<String> teleHandleKeywords;
    private final List<String> tutorialGroupKeywords;

    /**
     * Constructs a {@code PersonContainsKeywords} predicate.
     *
     * @param nameKeywords List of keywords to search in the name field.
     * @param emailKeywords List of keywords to search in the email field.
     * @param teleHandleKeywords List of keywords to search in the teleHandle field.
     * @param tutorialGroupKeywords List of keywords to search in the tutorialGroup field.
     */
    public PersonContainsKeywords(List<String> nameKeywords, List<String> emailKeywords,
                                 List<String> teleHandleKeywords, List<String> tutorialGroupKeywords) {
        this.nameKeywords = nameKeywords;
        this.emailKeywords = emailKeywords;
        this.teleHandleKeywords = teleHandleKeywords;
        this.tutorialGroupKeywords = tutorialGroupKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean matchesName = anyContainsIgnoreCase(
                valueOrEmpty(person.getName() == null ? null : person.getName().fullName),
                nameKeywords);

        boolean matchesEmail = anyContainsIgnoreCase(
                valueOrEmpty(person.getEmail() == null ? null : person.getEmail().value),
                emailKeywords);

        boolean matchesTeleHandle = anyContainsIgnoreCase(
                valueOrEmpty(person.getTeleHandle() == null ? null : person.getTeleHandle().value),
                teleHandleKeywords);

        boolean matchesTutorialGroup = anyContainsIgnoreCase(
                valueOrEmpty(person.getTutorialGroup() == null ? null : person.getTutorialGroup().toString()),
                tutorialGroupKeywords);

        return matchesName || matchesEmail || matchesTeleHandle || matchesTutorialGroup;
    }

    // Helper method to convert null to empty string
    private static String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }

    // Helper method to check if any keyword is contained in the field value (case-insensitive)
    private static boolean anyContainsIgnoreCase(String fieldValue, List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return false;
        }

        String lower = fieldValue.toLowerCase();
        return keywords.stream()
            .map(String::toLowerCase)
            .anyMatch(lower::contains);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonContainsKeywords)) {
            return false;
        }

        PersonContainsKeywords otherPredicate = (PersonContainsKeywords) other;
        return Objects.equals(nameKeywords, otherPredicate.nameKeywords)
            && Objects.equals(emailKeywords, otherPredicate.emailKeywords)
            && Objects.equals(teleHandleKeywords, otherPredicate.teleHandleKeywords)
            && Objects.equals(tutorialGroupKeywords, otherPredicate.tutorialGroupKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("nameKeywords", nameKeywords)
            .add("emailKeywords", emailKeywords)
            .add("teleHandleKeywords", teleHandleKeywords)
            .add("tutorialGroupKeywords", tutorialGroupKeywords)
            .toString();
    }

}
