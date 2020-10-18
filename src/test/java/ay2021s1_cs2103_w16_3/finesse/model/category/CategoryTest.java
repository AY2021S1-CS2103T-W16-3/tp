package ay2021s1_cs2103_w16_3.finesse.model.category;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
