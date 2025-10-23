package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertVerboseCommandSuccess;

import java.util.List;

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
                List.of(), List.of("gmail.com"), List.of(), List.of());
        expectedModel.updateFilteredPersonList(predicate);

        FindCommand command = new FindCommand(predicate, false);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(bob), model.getFilteredPersonList());

        FindCommand command1 = new FindCommand(predicate, true);
        assertVerboseCommandSuccess(command1, model, expectedMessage, expectedModel);
        assertEquals(List.of(bob), model.getFilteredPersonList());
    }

    @Test
    public void execute_teleHandleExact_matchesOne() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywords predicate = new PersonContainsKeywords(
                List.of(), List.of(), List.of("@carol"), List.of());
        expectedModel.updateFilteredPersonList(predicate);

        FindCommand command = new FindCommand(predicate, false);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(carol), model.getFilteredPersonList());

        FindCommand command1 = new FindCommand(predicate, true);
        assertVerboseCommandSuccess(command1, model, expectedMessage, expectedModel);
        assertEquals(List.of(carol), model.getFilteredPersonList());
    }

    @Test
    public void execute_tutorialGroup_matchesTwo() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PersonContainsKeywords predicate = new PersonContainsKeywords(
                List.of(), List.of(), List.of(), List.of("TG1"));
        expectedModel.updateFilteredPersonList(predicate);

        FindCommand command = new FindCommand(predicate, false);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(alice, bob), model.getFilteredPersonList());

        FindCommand command1 = new FindCommand(predicate, true);
        assertVerboseCommandSuccess(command1, model, expectedMessage, expectedModel);
        assertEquals(List.of(alice, bob), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiFieldOrSemantics_matchesUnion() {
        // email domain school.edu OR tutorial group Tutorial 1 -> should match {alice, bob, carol}
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsKeywords predicate = new PersonContainsKeywords(
                List.of(),
                List.of("school.edu"),
                List.of(),
                List.of("TG1"));
        expectedModel.updateFilteredPersonList(predicate);

        FindCommand command = new FindCommand(predicate, false);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(alice, bob, carol), model.getFilteredPersonList());

        FindCommand command1 = new FindCommand(predicate, true);
        assertVerboseCommandSuccess(command1, model, expectedMessage, expectedModel);
        assertEquals(List.of(alice, bob, carol), model.getFilteredPersonList());
    }

}
