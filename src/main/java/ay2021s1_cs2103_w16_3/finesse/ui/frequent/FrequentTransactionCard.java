package ay2021s1_cs2103_w16_3.finesse.ui.frequent;

import java.util.Comparator;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class FrequentTransactionCard<T extends Transaction> extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";
    private static final double PREFERRED_CARD_HEIGHT = 60.00;
    private static final double PREFERRED_CARD_WIDTH = 100.00;

    public final FrequentTransaction<T> frequentTransaction;

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
     * Creates a {@code FrequentTransactionCard} with the given {@code FrequentTransaction} and index to display.
     * The font size of the content is set based on the given {@code fontSize}.
     */
    public FrequentTransactionCard(FrequentTransaction<T> frequentTransaction, int displayedIndex, int fontSize) {
        super(FXML);
        this.frequentTransaction = frequentTransaction;
        String fontSizeParsedToString = String.valueOf(fontSize);
        String categoriesFontSizeParsedToString = String.valueOf(fontSize - 2);
        cardPane.setPrefHeight(PREFERRED_CARD_HEIGHT);
        cardPane.setPrefWidth(PREFERRED_CARD_WIDTH);

        id.setText(displayedIndex + ". ");
        id.setStyle(String.format("-fx-font-size: %spx", fontSizeParsedToString));

        title.setText(frequentTransaction.getTitle().fullTitle);
        title.setStyle(String.format("-fx-font-size: %spx", fontSizeParsedToString));

        amount.setText(frequentTransaction.getAmount().toString());
        amount.setStyle(String.format("-fx-font-size: %spx", fontSizeParsedToString));

        frequentTransaction.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.categoryName))
                .forEach(category -> {
                    Label newCategory = new Label(category.categoryName);
                    newCategory.setStyle("-fx-font-family: Eczar");
                    newCategory.setStyle(String.format("-fx-font-size: %spx", categoriesFontSizeParsedToString));
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
        if (!(other instanceof FrequentTransactionCard)) {
            return false;
        }

        // state check
        FrequentTransactionCard<?> card = (FrequentTransactionCard<?>) other;
        return id.getText().equals(card.id.getText())
                && frequentTransaction.equals(card.frequentTransaction);
    }

}
