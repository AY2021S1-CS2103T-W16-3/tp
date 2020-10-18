package ay2021s1_cs2103_w16_3.finesse.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;

public class SampleDataUtilTest {
    @Test
    public void getSampleFinanceTracker_countTransactions_correctNumberOfTransactions() {
        ReadOnlyFinanceTracker financeTracker = SampleDataUtil.getSampleFinanceTracker();
        assertEquals(6, financeTracker.getTransactionList().size());
        assertEquals(3, financeTracker.getExpenseList().size());
        assertEquals(3, financeTracker.getIncomeList().size());
    }
}
