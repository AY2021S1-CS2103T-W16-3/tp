package ay2021s1_cs2103_w16_3.finesse.ui.income;

import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.ui.TransactionCard;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class IncomePanel extends UiPart<Region> {
    private static final String FXML = "IncomePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IncomePanel.class);

    @FXML
    private ListView<Income> incomeListView;

    /**
     * Creates a {@code IncomePanel} with the given {@code ObservableList}.
     */
    public IncomePanel(ObservableList<Income> incomeList) {
        super(FXML);
        incomeListView.setItems(incomeList);
        incomeListView.setCellFactory(listView -> new IncomeListViewCell());
    }

    class IncomeListViewCell extends ListCell<Income> {
        @Override
        protected void updateItem(Income income, boolean empty) {
            super.updateItem(income, empty);

            if (empty || income == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: #2E2E36");
            } else {
                setGraphic(new TransactionCard(income, getIndex() + 1).getRoot());
            }
        }
    }
}
