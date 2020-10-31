package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // null title
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle("   ")); // spaces only
        assertFalse(Title.isValidTitle("\u0000world")); // non-graphical character

        // valid title
        assertTrue(Title.isValidTitle("peter jack")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(Title.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long title
        assertTrue(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertTrue(Title.isValidTitle("peter*")); // contains non-alphanumeric characters
    }

    @Test
    public void asciiTest() {
        String titlePrefix = "Test: ";

        // Check that the title prefix is valid. It will need to be used to get around the
        // 'at least one non-whitespace printable ASCII character' restriction.
        assertTrue(Title.isValidTitle(titlePrefix));

        // Each character is its own equivalence partition.

        // Check that the ASCII control characters (char codes 0 to 31 inclusive) are invalid.
        IntStream.range(0, 32).forEach(i ->
                assertFalse(Title.isValidTitle(titlePrefix + getCharFromCharCode(i))));

        // Check that the ASCII printable characters (char codes 32 to 126 inclusive) are valid.
        IntStream.range(32, 127).forEach(i ->
                assertTrue(Title.isValidTitle(titlePrefix + getCharFromCharCode(i))));

        // Check that the ASCII delete character (char code 127) is invalid.
        assertFalse(Title.isValidTitle(titlePrefix + getCharFromCharCode(127)));

        // Check that extended ASCII characters (char codes 128 to 255 inclusive) are invalid.
        IntStream.range(128, 256).forEach(i ->
                assertFalse(Title.isValidTitle(titlePrefix + getCharFromCharCode(i))));

        // Check that Unicode characters are invalid.
        IntStream.iterate(300, i -> i <= 10000, i -> i + 100).forEach(i ->
                assertFalse(Title.isValidTitle(titlePrefix + getCharFromCharCode(i))));
    }

    private String getCharFromCharCode(int i) {
        return Character.toString((char) i);
    }
}
