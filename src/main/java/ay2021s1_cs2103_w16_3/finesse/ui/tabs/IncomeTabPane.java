package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.ui.bookmark.BookmarkIncomePanel;
import ay2021s1_cs2103_w16_3.finesse.ui.income.IncomePanel;
import javafx.collections.ObservableList;

/**
 * Tab pane that displays incomes.
 */
public class IncomeTabPane extends TwoColumnTabPane {
    private static final String MAIN_PANEL_LABEL = "Incomes";
    private static final String SIDE_PANEL_LABEL = "Bookmark Incomes";

    /**
     * Constructs an {@code IncomeTabPane} that displays the specified list of incomes
     * and bookmark incomes.
     *
     * @param incomeList The list of incomes to be displayed.
     * @param bookmarkIncomeList The list of bookmark incomes to be displayed.
     */
    public IncomeTabPane(ObservableList<Income> incomeList, ObservableList<BookmarkIncome> bookmarkIncomeList) {
        super(MAIN_PANEL_LABEL, SIDE_PANEL_LABEL,
                new IncomePanel(incomeList), new BookmarkIncomePanel(bookmarkIncomeList));
    }
}
