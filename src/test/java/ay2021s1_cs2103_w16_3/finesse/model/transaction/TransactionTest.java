package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INTERNSHIP_2;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;

public class TransactionTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Transaction transaction = new TransactionBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> transaction.getCategories().remove(0));
    }

    // TODO: Not properly fixing this since this will be deleted once {@code Transaction} is made abstract.
    @Test
    public void equals() {
        Transaction bubbleTea = new TransactionBuilder(BUBBLE_TEA).build();

        // same values -> returns true
        Transaction bubbleTeaCopy = new TransactionBuilder(bubbleTea).build();
        assertTrue(bubbleTea.equals(bubbleTeaCopy));

        // same object -> returns true
        assertTrue(bubbleTea.equals(bubbleTea));

        // null -> returns false
        assertFalse(bubbleTea.equals(null));

        // different type -> returns false
        assertFalse(bubbleTea.equals(5));

        // different transaction -> returns false
        assertFalse(bubbleTea.equals(INTERNSHIP_2));

        // different title -> returns false
        Transaction editedBubbleTea = new TransactionBuilder(bubbleTea).withTitle(VALID_TITLE_INTERNSHIP).build();
        assertFalse(bubbleTea.equals(editedBubbleTea));

        // different amounts -> returns false
        editedBubbleTea = new TransactionBuilder(bubbleTea).withAmount(VALID_AMOUNT_INTERNSHIP).build();
        assertFalse(bubbleTea.equals(editedBubbleTea));

        // different dates -> returns false
        editedBubbleTea = new TransactionBuilder(bubbleTea).withDate(VALID_DATE_INTERNSHIP).build();
        assertFalse(bubbleTea.equals(editedBubbleTea));

        // different categories -> returns false
        editedBubbleTea = new TransactionBuilder(bubbleTea).withCategories(VALID_CATEGORY_WORK).build();
        assertFalse(bubbleTea.equals(editedBubbleTea));
    }
}
