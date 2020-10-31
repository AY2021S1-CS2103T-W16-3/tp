package ay2021s1_cs2103_w16_3.finesse.model.category;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategoryName_throwsIllegalArgumentException() {
        String invalidCategoryName = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategoryName));
    }

    @Test
    public void isValidCategoryName() {
        // null category name
        assertThrows(NullPointerException.class, () -> Category.isValidCategoryName(null));

        // invalid category name
        assertFalse(Category.isValidCategoryName("")); // empty string
        assertFalse(Category.isValidCategoryName("   ")); // spaces only
        assertFalse(Category.isValidCategoryName("\u0000world")); // non-graphical character

        // valid category name
        assertTrue(Category.isValidCategoryName("peter jack")); // alphabets only
        assertTrue(Category.isValidCategoryName("12345")); // numbers only
        assertTrue(Category.isValidCategoryName("peter the 2nd")); // alphanumeric characters
        assertTrue(Category.isValidCategoryName("Capital Tan")); // with capital letters
        assertTrue(Category.isValidCategoryName("David Roger Jackson Ray Jr 2nd")); // long category name
        assertTrue(Category.isValidCategoryName("^")); // only non-alphanumeric characters
        assertTrue(Category.isValidCategoryName("peter*")); // contains non-alphanumeric characters
    }

    @Test
    public void asciiTest() {
        String categoryNamePrefix = "Test: ";

        // Check that the category name prefix is valid. It will need to be used to get around the
        // 'at least one non-whitespace printable ASCII character' restriction.
        assertTrue(Category.isValidCategoryName(categoryNamePrefix));

        // Check that the ASCII control characters (char codes 0 to 31 inclusive) are invalid.
        IntStream.range(0, 32).forEach(i ->
                assertFalse(Category.isValidCategoryName(categoryNamePrefix + getCharFromCharCode(i))));

        // Check that the ASCII printable characters (char codes 32 to 126 inclusive) are valid.
        IntStream.range(32, 127).forEach(i ->
                assertTrue(Category.isValidCategoryName(categoryNamePrefix + getCharFromCharCode(i))));

        // Check that the ASCII delete character (char code 127) is invalid.
        assertFalse(Category.isValidCategoryName(categoryNamePrefix + getCharFromCharCode(127)));

        // Check that extended ASCII characters (char codes 128 to 255 inclusive) are invalid.
        IntStream.range(128, 256).forEach(i ->
                assertFalse(Category.isValidCategoryName(categoryNamePrefix + getCharFromCharCode(i))));

        // Check that Unicode characters are invalid.
        IntStream.iterate(300, i -> i <= 10000, i -> i + 100).forEach(i ->
                assertFalse(Category.isValidCategoryName(categoryNamePrefix + getCharFromCharCode(i))));
    }

    private String getCharFromCharCode(int i) {
        return Character.toString((char) i);
    }
}
