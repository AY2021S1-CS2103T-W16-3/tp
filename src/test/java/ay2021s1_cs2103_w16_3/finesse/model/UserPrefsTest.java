package ay2021s1_cs2103_w16_3.finesse.model;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;

public class UserPrefsTest {
    private final GuiSettings guiSettings = new GuiSettings(1200, 800, 100, 100);

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setFinanceTrackerFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setFinanceTrackerFilePath(null));
    }

    @Test
    public void equals_sameUserPrefs_returnsTrue() {
        UserPrefs userPrefs = new UserPrefs();
        GuiSettings guiSettings = new GuiSettings(1200, 800, 100, 100);
        userPrefs.setGuiSettings(guiSettings);
        assertEquals(userPrefs, userPrefs);
    }

    @Test
    public void equals_compareUserPrefsWithNull_returnsFalse() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(guiSettings);
        assertNotEquals(userPrefs, null);
    }

    @Test
    public void equals_distinctUserPrefsWithSameAttributes_returnsTrue() {
        UserPrefs firstUserPrefs = new UserPrefs();
        UserPrefs secondUserPrefs = new UserPrefs();

        firstUserPrefs.setGuiSettings(guiSettings);
        secondUserPrefs.setGuiSettings(guiSettings);

        assertNotSame(firstUserPrefs, secondUserPrefs);
        assertEquals(firstUserPrefs, secondUserPrefs);
    }

    @Test
    public void hashCode_distinctUserPrefsWithSameAttributes_returnsTrue() {
        UserPrefs firstUserPrefs = new UserPrefs();
        UserPrefs secondUserPrefs = new UserPrefs();

        firstUserPrefs.setGuiSettings(guiSettings);
        secondUserPrefs.setGuiSettings(guiSettings);

        assertNotSame(firstUserPrefs, secondUserPrefs);
        assertEquals(firstUserPrefs.hashCode(), secondUserPrefs.hashCode());
    }
}
