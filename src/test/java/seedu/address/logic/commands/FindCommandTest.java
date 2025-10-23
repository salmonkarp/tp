package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsKeywords;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsKeywords firstPredicate =
                new PersonContainsKeywords(Collections.singletonList("first"), List.of(), List.of(), List.of());
        PersonContainsKeywords secondPredicate =
                new PersonContainsKeywords(Collections.singletonList("second"), List.of(), List.of(), List.of());
        PersonContainsKeywords withEmailPredicate =
                new PersonContainsKeywords(List.of("email"),
                        Collections.singletonList("gmail.com"), List.of(), List.of());
        PersonContainsKeywords withTeleHandlePredicate =
                new PersonContainsKeywords(List.of("telehandle"),
                        List.of(), Collections.singletonList("@handle"), List.of());
        PersonContainsKeywords withTutorialPredicate =
                new PersonContainsKeywords(
                        List.of("tutorial"),
                        List.of(), List.of(), Collections.singletonList("Tutorial 1"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate, false);
        FindCommand findSecondCommand = new FindCommand(secondPredicate, false);
        FindCommand findSecondVerboseCommand = new FindCommand(secondPredicate, true);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate, false);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findSecondCommand.equals(findSecondVerboseCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsKeywords predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate, false);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsKeywords predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate, false);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_verboseCommand_moreDetailsShown() {
        PersonContainsKeywords predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate, true);
        CommandResult result = command.execute(model);
        assertTrue(result.isVerbose());
        assertEquals(List.of(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonContainsKeywords predicate =
                new PersonContainsKeywords(List.of("keyword"), List.of(), List.of(), List.of());
        boolean isVerbose = false;
        FindCommand findCommand = new FindCommand(predicate, isVerbose);
        String expected = FindCommand.class.getCanonicalName()
                + "{predicate=" + predicate + ", isVerbose=" + isVerbose + "}";
        assertEquals(expected, findCommand.toString());
        boolean isVerbose1 = true;
        FindCommand findCommand1 = new FindCommand(predicate, isVerbose1);
        String expected1 = FindCommand.class.getCanonicalName()
                + "{predicate=" + predicate + ", isVerbose=" + isVerbose1 + "}";
        assertEquals(expected1, findCommand1.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private PersonContainsKeywords preparePredicate(String userInput) {
        return new PersonContainsKeywords(List.of(userInput.split("\\s+")), List.of(), List.of(), List.of());
    }
}
