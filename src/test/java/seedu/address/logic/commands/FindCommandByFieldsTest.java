package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsKeywords;
import seedu.address.testutil.PersonBuilder;

public class FindCommandByFieldsTest {

    private Model model;
    private Model expectedModel;

    private Person alice;
    private Person bob;
    private Person carol;

    @BeforeEach
    public void setUp() {
        // Build persons with distinct email/tele/tg
        alice = new PersonBuilder()
                .withName("Alice Pauline")
                .withEmail("alice@outlook.com")
                .withTeleHandle("@alice")
                .withPhone("11111111")
                .withTags("noisy") // tags are independent; predicate uses tutorialGroup field
                .withTutorialGroup("TG1")
                .build();

        bob = new PersonBuilder()
                .withName("Bob Brown")
                .withEmail("bob@gmail.com")
                .withTeleHandle("@bobby")
                .withPhone("22222222")
                .withTags("lousy") // same TG as Alice to test multi-match
                .withTutorialGroup("TG1")
                .build();

        carol = new PersonBuilder()
                .withName("Carol Chan")
                .withEmail("carol@school.edu")
                .withTeleHandle("@carol")
                .withPhone("33333333")
                .withTags("excellent")
                .withTutorialGroup("TG2")
                .build();

        AddressBook ab = new AddressBook();
        ab.addPerson(alice);
        ab.addPerson(bob);
        ab.addPerson(carol);

        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(ab), new UserPrefs());
    }

    @Test
    public void execute_emailDomain_matchesOne() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywords predicate = new PersonContainsKeywords(
                java.util.List.of(), java.util.List.of("gmail.com"), java.util.List.of(), java.util.List.of());
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(java.util.List.of(bob), model.getFilteredPersonList());
    }

    @Test
    public void execute_teleHandleExact_matchesOne() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywords predicate = new PersonContainsKeywords(
                java.util.List.of(), java.util.List.of(), java.util.List.of("@carol"), java.util.List.of());
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(java.util.List.of(carol), model.getFilteredPersonList());
    }

    @Test
    public void execute_tutorialGroup_matchesTwo() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PersonContainsKeywords predicate = new PersonContainsKeywords(
                java.util.List.of(), java.util.List.of(), java.util.List.of(), java.util.List.of("TG1"));
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(alice, bob), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiFieldOrSemantics_matchesUnion() {
        // email domain school.edu OR tutorial group Tutorial 1 -> should match {alice, bob, carol}
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsKeywords predicate = new PersonContainsKeywords(
                java.util.List.of(),
                java.util.List.of("school.edu"),
                java.util.List.of(),
                java.util.List.of("TG1"));
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(alice, bob, carol), model.getFilteredPersonList());
    }

}
