package ay2021s1_cs2103_w16_3.finesse.ui.frequent;

import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class FrequentExpensePanel extends UiPart<Region> {
    private static final String FXML = "FrequentExpensePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FrequentExpensePanel.class);

    @FXML
    private ListView<FrequentExpense> frequentExpenseList;

    /**
     * Creates a {@code FrequentExpensePanel} with the given {@code ObservableList}.
     */
    public FrequentExpensePanel(ObservableList<FrequentExpense> frequentExpensesList) {
        super(FXML);
        frequentExpenseList.setItems(frequentExpensesList);
        frequentExpenseList.setCellFactory(listView -> new FrequentExpenseListViewCell());
    }

    class FrequentExpenseListViewCell extends ListCell<FrequentExpense> {
        @Override
        protected void updateItem(FrequentExpense frequentExpense, boolean empty) {
            super.updateItem(frequentExpense, empty);

            if (empty || frequentExpense == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: #2E2E36");
            } else {
                FrequentTransactionCard frequentTransactionCard = new FrequentTransactionCard<Expense>(frequentExpense,
                        getIndex() + 1, 13);
                setGraphic((Node) frequentTransactionCard.getRoot());
            }
        }
    }

}
