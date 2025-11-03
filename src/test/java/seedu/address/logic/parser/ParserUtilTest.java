package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDICES_SIZE_1;
import static seedu.address.testutil.TypicalIndexes.INDICES_SIZE_3;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Assignments;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TeleHandle;
import seedu.address.model.person.TutorialClass;
import seedu.address.model.person.TutorialGroup;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_TELEHANDLE = "rachel";
    private static final String INVALID_TUTORIAL_GROUP = "TG@1";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ASSIGNMENT = "Quiz1";
    private static final String INVALID_TUTORIAL_CLASS = "Tutorial1";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_TELEHANDLE = "@rachel";
    private static final String VALID_TUTORIAL_GROUP_1 = "tg01";
    private static final String VALID_TUTORIAL_GROUP_2 = "TG99";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_ASSIGNMENT_1 = "Q1";
    private static final String VALID_ASSIGNMENT_2 = "Finals";
    private static final String VALID_ASSIGNMENT_1_CASE_INSENSITIVE = "q1";
    private static final String VALID_ASSIGNMENT_2_CASE_INSENSITIVE = "fInALs";
    private static final String VALID_TUTORIAL_CLASS_1 = "t1";
    private static final String VALID_TUTORIAL_CLASS_2 = "t2";
    private static final String VALID_TUTORIAL_CLASS_1_UPPER_CASE = "T1";
    private static final String VALID_TUTORIAL_CLASS_2_UPPER_CASE = "T2";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseIndices_validInput_success() throws Exception {
        // Indices of size 1
        assertEquals(INDICES_SIZE_1, ParserUtil.parseIndices("4"));

        // Indices of size 3
        assertEquals(INDICES_SIZE_3, ParserUtil.parseIndices("5 6 7"));

        // Indices of size 3 with whitespaces
        assertEquals(INDICES_SIZE_3, ParserUtil.parseIndices("    5    6   7 "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseTelehandle_validValueWithoutWhitespace_returnsTelehandle() throws Exception {
        TeleHandle expectedTelehandle = new TeleHandle(VALID_TELEHANDLE);
        assertEquals(expectedTelehandle, ParserUtil.parseTeleHandle(VALID_TELEHANDLE));
    }

    @Test
    public void parseTelehandle_validValueWithWhitespace_returnsTrimmedTelehandle() throws Exception {
        String telehandleWithWhitespace = WHITESPACE + VALID_TELEHANDLE + WHITESPACE;
        TeleHandle expectedTelehandle = new TeleHandle(VALID_TELEHANDLE);
        assertEquals(expectedTelehandle, ParserUtil.parseTeleHandle(telehandleWithWhitespace));
    }

    @Test
    public void parseTeleHandle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTeleHandle((String) null));
    }

    @Test
    public void parseTeleHandle_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTeleHandle(INVALID_TELEHANDLE));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTutorialGroup_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTutorialGroup((String) null));
    }

    @Test
    public void parseTutorialGroup_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorialGroup(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorialGroup(WHITESPACE));
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorialGroup(INVALID_TUTORIAL_GROUP));
    }

    @Test
    public void parseTutorialGroup_validValueWithoutWhitespace_returnsTutorialGroup() throws Exception {
        TutorialGroup expectedTutorialGroup1 = new TutorialGroup(VALID_TUTORIAL_GROUP_1.toUpperCase());
        assertEquals(expectedTutorialGroup1, ParserUtil.parseTutorialGroup(VALID_TUTORIAL_GROUP_1));
        TutorialGroup expectedTutorialGroup2 = new TutorialGroup(VALID_TUTORIAL_GROUP_2);
        assertEquals(expectedTutorialGroup2, ParserUtil.parseTutorialGroup(VALID_TUTORIAL_GROUP_2));
    }

    @Test
    public void parseTutorialGroup_validValueWithWhitespace_returnsTrimmedTutorialGroup() throws Exception {
        String tutorialGroupStringWithWhitespace = WHITESPACE + VALID_TUTORIAL_GROUP_1 + WHITESPACE;
        TutorialGroup expectedTutorialGroup = new TutorialGroup(VALID_TUTORIAL_GROUP_1.toUpperCase());
        assertEquals(expectedTutorialGroup, ParserUtil.parseTutorialGroup(tutorialGroupStringWithWhitespace));
    }

    @Test
    public void parseAssignment_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAssignment((String) null));
    }

    @Test
    public void parseAssignment_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAssignment(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseAssignment(WHITESPACE));
        assertThrows(ParseException.class, () -> ParserUtil.parseAssignment(INVALID_ASSIGNMENT));
    }

    @Test
    public void parseAssignment_validValueWithoutWhitespace_returnsAssignment() throws Exception {
        assertEquals(Assignments.Q1, ParserUtil.parseAssignment(VALID_ASSIGNMENT_1));
        assertEquals(Assignments.Finals, ParserUtil.parseAssignment(VALID_ASSIGNMENT_2));
        assertEquals(Assignments.Q1, ParserUtil.parseAssignment(VALID_ASSIGNMENT_1_CASE_INSENSITIVE));
        assertEquals(Assignments.Finals, ParserUtil.parseAssignment(VALID_ASSIGNMENT_2_CASE_INSENSITIVE));
    }

    @Test
    public void parseAssignment_validValueWithWhitespace_returnsTrimmedAssignment() throws Exception {
        String assignmentWithWhitespace = WHITESPACE + VALID_ASSIGNMENT_1_CASE_INSENSITIVE + WHITESPACE;
        assertEquals(Assignments.Q1, ParserUtil.parseAssignment(assignmentWithWhitespace));
    }

    @Test
    public void parseTutorialClass_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTutorialClass((String) null));
    }

    @Test
    public void parseTutorialClass_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorialClass(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorialClass(WHITESPACE));
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorialClass(INVALID_TUTORIAL_CLASS));
    }

    @Test
    public void parseTutorialClass_validValueWithoutWhitespace_returnsTutorialClass() throws Exception {
        assertEquals(TutorialClass.t1, ParserUtil.parseTutorialClass(VALID_TUTORIAL_CLASS_1));
        assertEquals(TutorialClass.t2, ParserUtil.parseTutorialClass(VALID_TUTORIAL_CLASS_2));
        assertEquals(TutorialClass.t1, ParserUtil.parseTutorialClass(VALID_TUTORIAL_CLASS_1_UPPER_CASE));
        assertEquals(TutorialClass.t2, ParserUtil.parseTutorialClass(VALID_TUTORIAL_CLASS_2_UPPER_CASE));
    }

    @Test
    public void parseTutorialClass_validValueWithWhitespace_returnsTrimmedTutorialClass() throws Exception {
        String tutorialClasstWithWhitespace = WHITESPACE + VALID_TUTORIAL_CLASS_1_UPPER_CASE + WHITESPACE;
        assertEquals(TutorialClass.t1, ParserUtil.parseTutorialClass(tutorialClasstWithWhitespace));
    }

}
