package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.ui.frequent.FrequentIncomePanel;
import ay2021s1_cs2103_w16_3.finesse.ui.income.IncomePanel;
import javafx.collections.ObservableList;

/**
 * Tab pane that displays incomes.
 */
public class IncomeTabPane extends TwoColumnTabPane {
    private static final String MAIN_PANEL_LABEL = "Incomes";
    private static final String SIDE_PANEL_LABEL = "Bookmarked Incomes";

    /**
     * Constructs an {@code IncomeTabPane} that displays the specified list of incomes
     * and bookmarked incomes.
     *
     * @param incomeList The list of incomes to be displayed.
     * @param frequentIncomeList The list of frequent incomes to be displayed.
     */
    public IncomeTabPane(ObservableList<Income> incomeList, ObservableList<FrequentIncome> frequentIncomeList) {
        super(MAIN_PANEL_LABEL, SIDE_PANEL_LABEL,
                new IncomePanel(incomeList), new FrequentIncomePanel(frequentIncomeList));
    }
}
