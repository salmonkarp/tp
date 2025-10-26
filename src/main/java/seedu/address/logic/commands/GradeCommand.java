package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Assignments;
import seedu.address.model.person.AttendMap;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grade;
import seedu.address.model.person.GradeMap;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TeleHandle;
import seedu.address.model.person.TutorialGroup;
import seedu.address.model.tag.Tag;

/**
 * Assigns a grade to an existing person in the address book.
 */
public class GradeCommand extends Command {

    public static final String COMMAND_WORD = "grade";
    public static final String FUZZY_COMMAND_WORD = "gradee";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the grade to the person identified "
            + "by the index number used in the last person listing. "
            + "Existing grade will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GRADE + "GRADE] "
            + "[" + PREFIX_ASSIGNMENT + "ASSIGNMENT]"
            + "\n"
            + "The grade must be a number between 0 and 100 inclusive, with up to two decimal places."
            + "\n"
            + "The assignment name must be one of the following: "
            + Arrays.toString(Assignments.getAllAssignments()) + ".\n"
            + "Example: " + COMMAND_WORD + " 1 g/87.50 n/Q1";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Graded Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Grade grade;
    private final Assignments assignment;

    /**
     * @param index of the person in the filtered person list to assign the grade to
     * @param grade of the person to be assigned to
     */
    public GradeCommand(Index index, Grade grade, Assignments assignment) {
        requireAllNonNull(index, grade, assignment);

        this.index = index;
        this.grade = grade;
        this.assignment = assignment;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private Person createGradedPerson(Person personToEdit) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        TeleHandle teleHandle = personToEdit.getTeleHandle();
        TutorialGroup tutorialGroup = personToEdit.getTutorialGroup();
        GradeMap gradeMap = personToEdit.getGradeMap();
        gradeMap.put(assignment, grade);
        AttendMap attendMap = personToEdit.getAttendMap();
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, teleHandle, tutorialGroup, gradeMap, attendMap, tags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToGrade = lastShownList.get(index.getZeroBased());
        Person gradedPerson = createGradedPerson(personToGrade);

        if (!personToGrade.isSamePerson(gradedPerson) && model.hasPerson(gradedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToGrade, gradedPerson);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(gradedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradeCommand)) {
            return false;
        }

        GradeCommand e = (GradeCommand) other;
        return index.equals(e.index)
                && grade.equals(e.grade);
    }

}
