package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    private Model newModel() {
        return new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortByNameAsc_success() {
        Model model = newModel();
        SortCommand cmd = new SortCommand(SortCommand.Field.NAME, SortCommand.Order.ASC, false);
        CommandResult result = cmd.execute(model);
        assertEquals("Students sorted by name in ascending order.", result.getFeedbackToUser());

        List<Person> list = model.getFilteredPersonList();
        for (int i = 1; i < list.size(); i++) {
            String prev = list.get(i - 1).getName().toString();
            String cur = list.get(i).getName().toString();
            assertTrue(prev.compareToIgnoreCase(cur) <= 0);
        }
    }

    @Test
    public void execute_sortByNameDesc_success() {
        Model model = newModel();
        SortCommand cmd = new SortCommand(SortCommand.Field.NAME, SortCommand.Order.DESC, false);
        cmd.execute(model);

        List<Person> list = model.getFilteredPersonList();
        for (int i = 1; i < list.size(); i++) {
            String prev = list.get(i - 1).getName().toString();
            String cur = list.get(i).getName().toString();
            assertTrue(prev.compareToIgnoreCase(cur) >= 0);
        }
    }

    @Test
    public void execute_sortByTutorialAsc_success() {
        Model model = newModel();
        SortCommand cmd = new SortCommand(SortCommand.Field.TUTORIAL, SortCommand.Order.ASC, false);
        cmd.execute(model);

        List<Person> list = model.getFilteredPersonList();
        for (int i = 1; i < list.size(); i++) {
            int prev = list.get(i - 1).getTutorialGroup().getTutorialGroupNumber();
            int cur = list.get(i).getTutorialGroup().getTutorialGroupNumber();
            assertTrue(prev <= cur);
        }
    }

    @Test
    public void execute_sortByTutorialDesc_success() {
        Model model = newModel();
        SortCommand cmd = new SortCommand(SortCommand.Field.TUTORIAL, SortCommand.Order.DESC, false);
        cmd.execute(model);

        List<Person> list = model.getFilteredPersonList();
        for (int i = 1; i < list.size(); i++) {
            int prev = list.get(i - 1).getTutorialGroup().getTutorialGroupNumber();
            int cur = list.get(i).getTutorialGroup().getTutorialGroupNumber();
            assertTrue(prev >= cur);
        }
    }

    @Test
    public void execute_sortByGradeAsc_success() {
        Model model = newModel();
        SortCommand cmd = new SortCommand(SortCommand.Field.GRADE, SortCommand.Order.ASC, false);
        cmd.execute(model);

        List<Person> list = model.getFilteredPersonList();
        for (int i = 1; i < list.size(); i++) {
            float prev = list.get(i - 1).getOverallGrade().valueFloat;
            float cur = list.get(i).getOverallGrade().valueFloat;
            assertTrue(prev <= cur);
        }
    }

    @Test
    public void execute_sortByGradeDesc_success() {
        Model model = newModel();
        SortCommand cmd = new SortCommand(SortCommand.Field.GRADE, SortCommand.Order.DESC, false);
        cmd.execute(model);

        List<Person> list = model.getFilteredPersonList();
        for (int i = 1; i < list.size(); i++) {
            float prev = list.get(i - 1).getOverallGrade().valueFloat;
            float cur = list.get(i).getOverallGrade().valueFloat;
            assertTrue(prev >= cur);
        }
    }

    @Test
    public void execute_sortByAttendanceAsc_success() {
        Model model = newModel();
        SortCommand cmd = new SortCommand(SortCommand.Field.ATTENDANCE, SortCommand.Order.ASC, false);
        cmd.execute(model);

        List<Person> list = model.getFilteredPersonList();
        for (int i = 1; i < list.size(); i++) {
            double prev = list.get(i - 1).getAttendMap().getAttendanceRate();
            double cur = list.get(i).getAttendMap().getAttendanceRate();
            assertTrue(prev <= cur);
        }
    }

    @Test
    public void execute_sortByAttendanceDesc_success() {
        Model model = newModel();
        SortCommand cmd = new SortCommand(SortCommand.Field.ATTENDANCE, SortCommand.Order.DESC, false);
        cmd.execute(model);

        List<Person> list = model.getFilteredPersonList();
        for (int i = 1; i < list.size(); i++) {
            double prev = list.get(i - 1).getAttendMap().getAttendanceRate();
            double cur = list.get(i).getAttendMap().getAttendanceRate();
            assertTrue(prev >= cur);
        }
    }

    @Test
    public void execute_sortEmptyList_noError() {
        Model emptyModel = new ModelManager();
        SortCommand cmd = new SortCommand(SortCommand.Field.NAME, SortCommand.Order.ASC, false);
        assertDoesNotThrow(() -> cmd.execute(emptyModel));
        assertTrue(emptyModel.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_sortSinglePerson_noChange() {
        Model singleModel = new ModelManager();
        Person p = new PersonBuilder().build();
        singleModel.addPerson(p);

        SortCommand cmd = new SortCommand(SortCommand.Field.GRADE, SortCommand.Order.DESC, false);
        assertDoesNotThrow(() -> cmd.execute(singleModel));
        assertEquals(1, singleModel.getFilteredPersonList().size());
        assertEquals(p, singleModel.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_sortVerbose_success() {
        Model model = newModel();
        SortCommand cmd = new SortCommand(SortCommand.Field.NAME, SortCommand.Order.ASC, true);
        CommandResult result = cmd.execute(model);
        assertEquals("Students sorted by name in ascending order.", result.getFeedbackToUser());
        assertTrue(result.isVerbose());

        List<Person> list = model.getFilteredPersonList();
        for (int i = 1; i < list.size(); i++) {
            String prev = list.get(i - 1).getName().toString();
            String cur = list.get(i).getName().toString();
            assertTrue(prev.compareToIgnoreCase(cur) <= 0);
        }
    }
}
