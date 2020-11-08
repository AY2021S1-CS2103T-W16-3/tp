package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PrefixTest {
    private static final String PREFIX_T = "t/";

    private final Prefix prefix = new Prefix(PREFIX_T);

    @Test
    public void constructor_nullPrefix_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Prefix(null));
    }

    @Test
    public void getPrefix() {
        assertEquals(PREFIX_T, prefix.getPrefix());
    }

    @Test
    public void testToString() {
        assertEquals(PREFIX_T, prefix.toString());
    }

    @Test
    public void testHashCode() {
        assertEquals(PREFIX_T.hashCode(), prefix.hashCode());
    }

    @Test
    public void equals() {
        // Same prefix -> returns true
        assertTrue(prefix.equals(new Prefix(PREFIX_T)));

        // Same object -> returns true
        assertTrue(prefix.equals(prefix));

        // null -> returns false
        assertFalse(prefix.equals(null));

        // Different types -> returns false
        assertFalse(prefix.equals(5));
    }
}
