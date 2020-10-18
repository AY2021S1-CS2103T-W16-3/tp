package ay2021s1_cs2103_w16_3.finesse.model;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalTransactions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class FinanceTrackerTest {

    private final FinanceTracker financeTracker = new FinanceTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), financeTracker.getTransactionList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> financeTracker.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyFinanceTracker_replacesData() {
        FinanceTracker newData = getTypicalFinanceTracker();
        financeTracker.resetData(newData);
        assertEquals(newData, financeTracker);
    }

    @Test
    public void getTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> financeTracker.getTransactionList().remove(0));
    }

    @Test
    public void getExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> financeTracker.getExpenseList().remove(0));
    }

    @Test
    public void getIncomeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> financeTracker.getIncomeList().remove(0));
    }

    @Test
    public void equals_distinctFinanceTrackersWithSameAttributes_returnsTrue() {
        FinanceTracker firstFinanceTracker = new FinanceTracker();
        FinanceTracker secondFinanceTracker = new FinanceTracker();

        firstFinanceTracker.setTransactions(getTypicalTransactions());
        secondFinanceTracker.setTransactions(getTypicalTransactions());

        assertNotSame(firstFinanceTracker, secondFinanceTracker);
        assertEquals(firstFinanceTracker, secondFinanceTracker);
    }

    @Test
    public void hashCode_distinctFinanceTrackersWithSameAttributes_returnsTrue() {
        FinanceTracker firstFinanceTracker = new FinanceTracker();
        FinanceTracker secondFinanceTracker = new FinanceTracker();

        firstFinanceTracker.setTransactions(getTypicalTransactions());
        secondFinanceTracker.setTransactions(getTypicalTransactions());

        assertNotSame(firstFinanceTracker, secondFinanceTracker);
        assertEquals(firstFinanceTracker.hashCode(), secondFinanceTracker.hashCode());
    }
}
