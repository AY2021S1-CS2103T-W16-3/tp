package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.ui.expense.ExpensePanel;
import ay2021s1_cs2103_w16_3.finesse.ui.frequent.FrequentExpensePanel;
import javafx.collections.ObservableList;

/**
 * Tab pane that displays expenses.
 */
public class ExpenseTabPane extends TwoColumnTabPane {
    private static final String MAIN_PANEL_LABEL = "Expenses";
    private static final String SIDE_PANEL_LABEL = "Bookmarked Expenses";

    /**
     * Constructs an {@code ExpenseTabPane} that displays the specified list of expenses
     * and bookmarked expenses.
     *
     * @param expenseList The list of expenses to be displayed.
     * @param frequentExpenseList The list of frequent expenses to be displayed.
     */
    public ExpenseTabPane(ObservableList<Expense> expenseList, ObservableList<FrequentExpense> frequentExpenseList) {
        super(MAIN_PANEL_LABEL, SIDE_PANEL_LABEL,
                new ExpensePanel(expenseList), new FrequentExpensePanel(frequentExpenseList));
    }
}
