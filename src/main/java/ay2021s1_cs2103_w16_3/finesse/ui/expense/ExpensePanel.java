package ay2021s1_cs2103_w16_3.finesse.ui.expense;

import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.ui.TransactionCard;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class ExpensePanel extends UiPart<Region> {
    private static final String FXML = "ExpensePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExpensePanel.class);

    @FXML
    private ListView<Expense> expenseListView;

    /**
     * Creates a {@code ExpensePanel} with the given {@code ObservableList}.
     */
    public ExpensePanel(ObservableList<Expense> expenseList) {
        super(FXML);
        expenseListView.setItems(expenseList);
        expenseListView.setCellFactory(listView -> new ExpenseListViewCell());
    }

    class ExpenseListViewCell extends ListCell<Expense> {
        @Override
        protected void updateItem(Expense expense, boolean empty) {
            super.updateItem(expense, empty);

            if (empty || expense == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: #2E2E36");
            } else {
                setGraphic(new TransactionCard(expense, getIndex() + 1).getRoot());
            }
        }
    }
}
