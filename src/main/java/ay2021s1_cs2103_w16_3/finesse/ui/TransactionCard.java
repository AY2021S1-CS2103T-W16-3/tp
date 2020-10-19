package ay2021s1_cs2103_w16_3.finesse.ui;

import java.util.Comparator;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";
    private static final double PREFERRED_CARD_HEIGHT = 70.00;

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
    private FlowPane categories;
    @FXML
    private GridPane transactionDetails;

    /**
     * Creates a {@code TransactionCard} with the given {@code Transaction} and index to display.
     */
    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        cardPane.setPrefHeight(PREFERRED_CARD_HEIGHT);
        id.setText(displayedIndex + ". ");
        title.setText(transaction.getTitle().fullTitle);
        amount.setText(transaction.getAmount().toString());
        transaction.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.categoryName))
                .forEach(category -> {
                    Label newCategory = new Label(category.categoryName);
                    newCategory.setStyle("-fx-font-family: Eczar");
                    categories.getChildren().add(newCategory);
                });
    }

    /**
     * Creates a {@code TransactionCard} with the given {@code Transaction} and index to display. The font size of
     * the content is set based on the given {@code fontSize}.
     */
    public TransactionCard(Transaction transaction, int displayedIndex, int fontSize) {
        super(FXML);
        this.transaction = transaction;
        String fontSizeParsedToString = String.valueOf(fontSize);
        String categoriesFontSizeParsedToString = String.valueOf(fontSize - 2);
        cardPane.setPrefHeight(PREFERRED_CARD_HEIGHT);

        id.setText(displayedIndex + ". ");
        id.setStyle(String.format("-fx-font-size: %spx", fontSizeParsedToString));

        title.setText(transaction.getTitle().fullTitle);
        title.setStyle(String.format("-fx-font-size: %spx", fontSizeParsedToString));

        amount.setText(transaction.getAmount().toString());
        amount.setStyle(String.format("-fx-font-size: %spx", fontSizeParsedToString));

        transaction.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.categoryName))
                .forEach(category -> {
                    Label newCategory = new Label(category.categoryName);
                    newCategory.setStyle("-fx-font-family: Eczar");
                    newCategory.setStyle(String.format("-fx-font-size: %spx", categoriesFontSizeParsedToString));
                    categories.getChildren().add(newCategory);
                });
    }

    /**
     * Edits the height and the width of the {@code cardPane} VBox with the new width and new height.
     */
    public void editCardSize(double newWidth, double newHeight) {
        cardPane.setPrefSize(newWidth, newHeight);
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
