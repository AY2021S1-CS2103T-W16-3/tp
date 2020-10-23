package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.ui.SavingsGoalPanel;
import ay2021s1_cs2103_w16_3.finesse.ui.TransactionListPanel;
import javafx.collections.ObservableList;

/**
 * Tab pane that displays an overview of transactions.
 */
public class OverviewTabPane extends TwoColumnTabPane {
    private static final String MAIN_PANEL_LABEL = "Overview";
    private static final String SIDE_PANEL_LABEL = "Savings Summary";

    /**
     * Constructs an {@code OverviewTabPane} that displays the specified list of transactions
     * and savings goal.
     *
     * @param transactionList The list of transactions to be displayed.
     * @param monthlyBudget The monthly budget in the finance tracker.
     */
    public OverviewTabPane(ObservableList<Transaction> transactionList, MonthlyBudget monthlyBudget) {
        super(MAIN_PANEL_LABEL, SIDE_PANEL_LABEL, new TransactionListPanel(transactionList),
                new SavingsGoalPanel(monthlyBudget));
    }
}
