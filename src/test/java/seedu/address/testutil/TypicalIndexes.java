package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PERSON = Index.fromOneBased(3);
    public static final List<Index> INDICES_SIZE_1 = new ArrayList<Index>() {
        {
            add(Index.fromOneBased(4));
        }
    };
    public static final List<Index> INDICES_SIZE_3 = new ArrayList<Index>() {
        {
            add(Index.fromOneBased(5));
            add(Index.fromOneBased(6));
            add(Index.fromOneBased(7));
        }
    };
}
