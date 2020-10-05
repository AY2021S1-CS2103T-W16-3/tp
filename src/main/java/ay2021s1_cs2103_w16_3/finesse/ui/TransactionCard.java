package ay2021s1_cs2103_w16_3.finesse.ui;

import java.util.Comparator;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private VBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label date;
    @FXML
    private FlowPane categories;

    /**
     * Creates a {@code TransactionCard} with the given {@code Transaction} and index to display.
     */
    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        title.setText(transaction.getTitle().fullTitle);
        amount.setText(transaction.getAmount().value);
        date.setText(transaction.getDate().value);
        categories.setHgap(10);
        transaction.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.categoryName))
                .forEach(category -> {
                    Label newCategory = new Label(category.categoryName);
                    newCategory.setStyle("-fx-font-family: Eczar");
                    categories.getChildren().add(newCategory);
                });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionCard)) {
            return false;
        }

        // state check
        TransactionCard card = (TransactionCard) other;
        return id.getText().equals(card.id.getText())
                && transaction.equals(card.transaction);
    }
}
