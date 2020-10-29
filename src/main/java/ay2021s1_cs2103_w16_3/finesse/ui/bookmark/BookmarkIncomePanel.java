package ay2021s1_cs2103_w16_3.finesse.ui.bookmark;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class BookmarkIncomePanel extends UiPart<Region> {
    private static final String FXML = "BookmarkTransactionPanel.fxml";

    @FXML
    private ListView<BookmarkIncome> bookmarkTransactionList;

    /**
     * Creates a {@code BookmarkIncomePanel} with the given {@code ObservableList}.
     */
    public BookmarkIncomePanel(ObservableList<BookmarkIncome> bookmarkIncomesList) {
        super(FXML);
        bookmarkTransactionList.setItems(bookmarkIncomesList);
        bookmarkTransactionList.setCellFactory(listView -> new BookmarkIncomeListViewCell());
    }

    class BookmarkIncomeListViewCell extends ListCell<BookmarkIncome> {
        @Override
        protected void updateItem(BookmarkIncome bookmarkIncome, boolean empty) {
            super.updateItem(bookmarkIncome, empty);

            if (empty || bookmarkIncome == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: #2E2E36");
            } else {
                BookmarkTransactionCard<Income> bookmarkTransactionCard =
                        new BookmarkTransactionCard<>(bookmarkIncome, getIndex() + 1,
                                bookmarkTransactionList.widthProperty());
                setGraphic(bookmarkTransactionCard.getRoot());
            }
        }
    }

}
