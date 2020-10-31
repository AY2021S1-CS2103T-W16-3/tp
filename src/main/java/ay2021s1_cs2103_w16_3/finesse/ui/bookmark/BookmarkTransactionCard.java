package ay2021s1_cs2103_w16_3.finesse.ui.bookmark;

import java.util.Comparator;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class BookmarkTransactionCard<T extends Transaction> extends UiPart<Region> {

    private static final String FXML = "BookmarkTransactionListCard.fxml";

    public final BookmarkTransaction<T> bookmarkTransaction;

    @FXML
    private BorderPane cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private HBox categories;
    @FXML
    private GridPane transactionDetails;
    @FXML
    private ScrollPane scrollPane;

    /**
     * Creates a {@code BookmarkTransactionCard} with the given {@code BookmarkTransaction} and index to display.
     * Binds the width of the {@code BookmarkTransactionCard} to that of its containing list.
     */
    public BookmarkTransactionCard(BookmarkTransaction<T> bookmarkTransaction, int displayedIndex,
                                   ReadOnlyDoubleProperty width) {
        super(FXML);
        this.bookmarkTransaction = bookmarkTransaction;
        cardPane.maxWidthProperty().bind(width.subtract(32));

        id.setText(displayedIndex + ". ");
        title.setText(bookmarkTransaction.getTitle().toString());
        amount.setText(bookmarkTransaction.getAmount().toString());

        bookmarkTransaction.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.getCategoryName()))
                .forEach(category -> {
                    Label newCategory = new Label(category.getCategoryName());
                    categories.getChildren().add(newCategory);
                });

        // Hijack scroll wheel for horizontal scrolling instead of vertical scrolling.
        scrollPane.setOnScroll(scrollEvent -> {
            // If no scrollbar, pass scroll event up to parent.
            if (categories.getWidth() <= scrollPane.getWidth()) {
                return;
            }

            double oldHvalue = scrollPane.getHvalue();
            double newHvalue = scrollPane.getHvalue() - scrollEvent.getDeltaY() / categories.getWidth();
            // Bound the values of 'newHvalue' to [0, 1].
            newHvalue = Math.max(0.0, Math.min(1.0, newHvalue));

            // If there is a change in the Hvalue, consume the scroll event.
            if (oldHvalue != newHvalue) {
                scrollPane.setHvalue(newHvalue);
                // Do not pass scroll event up to parent.
                scrollEvent.consume();
            }
        });

        // Hijack scroll events on category labels and pass on to scroll pane.
        categories.setOnScroll(scrollEvent -> {
            scrollPane.fireEvent(scrollEvent);
            scrollEvent.consume();
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookmarkTransactionCard)) {
            return false;
        }

        // state check
        BookmarkTransactionCard<?> card = (BookmarkTransactionCard<?>) other;
        return id.getText().equals(card.id.getText())
                && bookmarkTransaction.equals(card.bookmarkTransaction);
    }

}
