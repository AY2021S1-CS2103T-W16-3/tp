package ay2021s1_cs2103_w16_3.finesse.ui;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

public class UiStateTest {
    @Test
    public void getTabEnumIndex_returnsCorrectIndex() {
        assertEquals(1, Tab.OVERVIEW.getTabIndex().getOneBased());
        assertEquals(2, Tab.INCOME.getTabIndex().getOneBased());
        assertEquals(3, Tab.EXPENSES.getTabIndex().getOneBased());
        assertEquals(4, Tab.ANALYTICS.getTabIndex().getOneBased());
    }

    @Test
    public void constructor_nullCurrentTab_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UiState((Tab) null));
    }

    @Test
    public void setCurrentTab_nullCurrentTab_throwsNullPointerException() {
        UiState uiState = new UiState();
        assertThrows(NullPointerException.class, () -> uiState.setCurrentTab((Tab) null));
    }

    @Test
    public void setCurrentTab_validTab_setsCurrentTab() {
        UiState uiState = new UiState();
        uiState.setCurrentTab(Tab.EXPENSES);
        assertEquals(Tab.EXPENSES, uiState.getCurrentTab());
    }
}
