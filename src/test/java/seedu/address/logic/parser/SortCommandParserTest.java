package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_fieldOnly_success() throws Exception {
        assertEquals(new SortCommand(SortCommand.Field.NAME, SortCommand.Order.ASC),
                parser.parse("name"));
        assertEquals(new SortCommand(SortCommand.Field.TUTORIAL, SortCommand.Order.ASC),
                parser.parse("tutorial"));
        assertEquals(new SortCommand(SortCommand.Field.GRADE, SortCommand.Order.ASC),
                parser.parse("grade"));
        assertEquals(new SortCommand(SortCommand.Field.ATTENDANCE, SortCommand.Order.ASC),
                parser.parse("attendance"));
    }

    @Test
    public void parse_fieldAndOrder_success() throws Exception {
        assertEquals(new SortCommand(SortCommand.Field.NAME, SortCommand.Order.DESC),
                parser.parse("name desc"));
        assertEquals(new SortCommand(SortCommand.Field.TUTORIAL, SortCommand.Order.ASC),
                parser.parse("tutorial asc"));
        assertEquals(new SortCommand(SortCommand.Field.GRADE, SortCommand.Order.DESC),
                parser.parse("grade desc"));
        assertEquals(new SortCommand(SortCommand.Field.ATTENDANCE, SortCommand.Order.ASC),
                parser.parse("attendance asc"));
    }

    @Test
    public void parse_emptyDefaultsToNameAsc_success() throws Exception {
        assertEquals(new SortCommand(SortCommand.Field.NAME, SortCommand.Order.ASC), parser.parse(""));
        assertEquals(new SortCommand(SortCommand.Field.NAME, SortCommand.Order.ASC), parser.parse("   "));
    }

    @Test
    public void parse_caseInsensitiveAndWhitespace_success() throws Exception {
        assertEquals(new SortCommand(SortCommand.Field.NAME, SortCommand.Order.DESC),
                parser.parse("  NaMe   DeSc  "));
        assertEquals(new SortCommand(SortCommand.Field.ATTENDANCE, SortCommand.Order.ASC),
                parser.parse("\tAttEndance\tasc\n"));
    }

    @Test
    public void parse_invalidField_throws() {
        assertThrows(ParseException.class, () -> parser.parse("xyz"));
        assertThrows(ParseException.class, () -> parser.parse("gradee"));
    }

    @Test
    public void parse_invalidOrder_throws() {
        assertThrows(ParseException.class, () -> parser.parse("name up"));
        assertThrows(ParseException.class, () -> parser.parse("tutorial down"));
        assertThrows(ParseException.class, () -> parser.parse("attendance descending"));
    }

    @Test
    public void parse_extraArguments_throws() {
        assertThrows(ParseException.class, () -> parser.parse("name asc extra"));
        assertThrows(ParseException.class, () -> parser.parse("grade desc trailing words"));
    }
}
