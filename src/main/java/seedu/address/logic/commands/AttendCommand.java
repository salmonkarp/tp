package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttendMap;
import seedu.address.model.person.Email;
import seedu.address.model.person.GradeMap;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TeleHandle;
import seedu.address.model.person.TutorialClass;
import seedu.address.model.person.TutorialGroup;
import seedu.address.model.tag.Tag;

/**
 * Marks attendance for an existing student in the address book.
 */
public class AttendCommand extends Command {

    public static final String COMMAND_WORD = "attend";
    public static final String FUZZY_COMMAND_WORD = "attendd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of the given tutorial class of the student identified by "
            + "the index number used in the displayed student list.\n"
            + "Parameters: INDEX [MORE_INDICES]... (must be a positive integer) "
            + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS\n"
            + "The tutorial class must be one of the following: "
            + Arrays.toString(TutorialClass.getAllTutorialClass()) + ".\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 c/t1";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS =
            "Attendance marked for Tutorial Class (%1$s) for: \n%2$s";
    public static final String MESSAGE_EDIT_PERSONS_SUCCESS =
            "Attendance marked for Tutorial Class (%1$s) for students: \n%2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This student already exists in the address book.";

    private final List<Index> indices;
    private final TutorialClass tutClass;

    /**
     * @param indices of the student in the student list
     * @param tutClass the tutorial class to be marked as present
     */
    public AttendCommand(List<Index> indices, TutorialClass tutClass) {
        requireAllNonNull(indices, tutClass);

        this.indices = indices;
        this.tutClass = tutClass;
    }

    /**
     * @param index of the student in the filtered student list
     * @param tutClass the tutorial class to be marked as present
     */
    public AttendCommand(Index index, TutorialClass tutClass) {
        requireAllNonNull(index, tutClass);

        this.indices = List.of(index);
        this.tutClass = tutClass;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private Person createAttendancePerson(Person personToEdit) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        TeleHandle teleHandle = personToEdit.getTeleHandle();
        TutorialGroup tutorialGroup = personToEdit.getTutorialGroup();
        GradeMap gradeMap = personToEdit.getGradeMap();
        AttendMap attendMap = personToEdit.getAttendMap();
        // Removed clone behaviour here since logic would've applied
        // to other fields as well. Either way, normal behaviour would not
        // cause sharing of objects as attributes, and is thus fine to take directly.
        attendMap.markPresent(tutClass);
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, teleHandle, tutorialGroup, gradeMap, attendMap, tags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        StringBuilder successMessage = new StringBuilder();

        for (Index index : indices) {
            Person personToMarkAttend = lastShownList.get(index.getZeroBased());
            Person attendancePerson = createAttendancePerson(personToMarkAttend);

            if (!personToMarkAttend.isSamePerson(attendancePerson) && model.hasPerson(attendancePerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.setPerson(personToMarkAttend, attendancePerson);
            successMessage.append(Messages.format(attendancePerson).replace("; ", System.lineSeparator()));

        }

        if (indices.size() > 1) {
            return new CommandResult(String.format(MESSAGE_EDIT_PERSONS_SUCCESS, tutClass, successMessage),
                    false, false, true);
        } else {
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, tutClass, successMessage),
                    false, false, true);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendCommand)) {
            return false;
        }

        AttendCommand e = (AttendCommand) other;
        return indices.equals(e.indices)
                && tutClass.equals(e.tutClass);
    }

}
