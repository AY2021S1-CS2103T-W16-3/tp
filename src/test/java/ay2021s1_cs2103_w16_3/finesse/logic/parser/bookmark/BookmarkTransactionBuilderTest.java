package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.BookmarkTransactionBuilder;

public class BookmarkTransactionBuilderTest {
    @Test
    public void equals() {
        BookmarkTransactionBuilder bookmarkTransactionBuilder = new BookmarkTransactionBuilder();

        // Same values -> returns true
        assertTrue(bookmarkTransactionBuilder.equals(new BookmarkTransactionBuilder()));

        // Different titles -> returns false
        assertFalse(bookmarkTransactionBuilder.equals(new BookmarkTransactionBuilder().withTitle("Internship")));

        // Different amounts -> returns false
        assertFalse(bookmarkTransactionBuilder.equals(new BookmarkTransactionBuilder().withAmount("5")));

        // Different categories -> returns false
        assertFalse(bookmarkTransactionBuilder.equals(new BookmarkTransactionBuilder().withCategories("Work")));

        // Same object -> returns true
        assertTrue(bookmarkTransactionBuilder.equals(bookmarkTransactionBuilder));

        // null -> returns false
        assertFalse(bookmarkTransactionBuilder.equals(null));

        // Different types -> returns false
        assertFalse(bookmarkTransactionBuilder.equals(5));
    }
}
