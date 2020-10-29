package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.ui.bookmark.BookmarkExpensePanel;
import ay2021s1_cs2103_w16_3.finesse.ui.expense.ExpensePanel;
import javafx.collections.ObservableList;

/**
 * Tab pane that displays expenses.
 */
public class ExpenseTabPane extends TwoColumnTabPane {
    private static final String MAIN_PANEL_LABEL = "Expenses";
    private static final String SIDE_PANEL_LABEL = "Bookmark Expenses";

    /**
     * Constructs an {@code ExpenseTabPane} that displays the specified list of expenses
     * and bookmark expenses.
     *
     * @param expenseList The list of expenses to be displayed.
     * @param bookmarkExpenseList The list of bookmark expenses to be displayed.
     */
    public ExpenseTabPane(ObservableList<Expense> expenseList, ObservableList<BookmarkExpense> bookmarkExpenseList) {
        super(MAIN_PANEL_LABEL, SIDE_PANEL_LABEL,
                new ExpensePanel(expenseList), new BookmarkExpensePanel(bookmarkExpenseList));
    }
}
