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
        // same values (expense) -> returns true
        Expense bubbleTeaCopy = new TransactionBuilder(BUBBLE_TEA).buildExpense();
        assertTrue(BUBBLE_TEA.equals(bubbleTeaCopy));

        // same values (income) -> returns true
        Income internshipCopy = new TransactionBuilder(INTERNSHIP).buildIncome();
        assertTrue(INTERNSHIP.equals(internshipCopy));

        // same object -> returns true
        assertTrue(BUBBLE_TEA.equals(BUBBLE_TEA));

        // null -> returns false
        assertFalse(BUBBLE_TEA.equals(null));

        // different type -> returns false
        assertFalse(BUBBLE_TEA.equals(5));

        // different transaction -> returns false
        assertFalse(BUBBLE_TEA.equals(INTERNSHIP_2));

        // different title -> returns false
        Transaction editedBubbleTea = new TransactionBuilder(BUBBLE_TEA).withTitle(VALID_TITLE_INTERNSHIP).build();
        assertFalse(BUBBLE_TEA.equals(editedBubbleTea));

        // different amounts -> returns false
        editedBubbleTea = new TransactionBuilder(BUBBLE_TEA).withAmount(VALID_AMOUNT_INTERNSHIP).build();
        assertFalse(BUBBLE_TEA.equals(editedBubbleTea));

        // different dates -> returns false
        editedBubbleTea = new TransactionBuilder(BUBBLE_TEA).withDate(VALID_DATE_INTERNSHIP).build();
        assertFalse(BUBBLE_TEA.equals(editedBubbleTea));

        // different categories -> returns false
        editedBubbleTea = new TransactionBuilder(BUBBLE_TEA).withCategories(VALID_CATEGORY_WORK).build();
        assertFalse(BUBBLE_TEA.equals(editedBubbleTea));
    }
}
