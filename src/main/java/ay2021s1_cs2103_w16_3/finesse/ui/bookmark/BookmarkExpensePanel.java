package ay2021s1_cs2103_w16_3.finesse.ui.bookmark;

import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class BookmarkExpensePanel extends UiPart<Region> {
    private static final String FXML = "BookmarkTransactionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BookmarkExpensePanel.class);

    @FXML
    private ListView<BookmarkExpense> bookmarkTransactionList;

    /**
     * Creates a {@code BookmarkExpensePanel} with the given {@code ObservableList}.
     */
    public BookmarkExpensePanel(ObservableList<BookmarkExpense> bookmarkExpensesList) {
        super(FXML);
        bookmarkTransactionList.setItems(bookmarkExpensesList);
        bookmarkTransactionList.setCellFactory(listView -> new BookmarkExpenseListViewCell());
    }

    class BookmarkExpenseListViewCell extends ListCell<BookmarkExpense> {
        @Override
        protected void updateItem(BookmarkExpense bookmarkExpense, boolean empty) {
            super.updateItem(bookmarkExpense, empty);

            if (empty || bookmarkExpense == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: #2E2E36");
            } else {
                BookmarkTransactionCard<Expense> bookmarkTransactionCard =
                        new BookmarkTransactionCard<>(bookmarkExpense, getIndex() + 1,
                                bookmarkTransactionList.widthProperty());
                setGraphic(bookmarkTransactionCard.getRoot());
            }
        }
    }
}
