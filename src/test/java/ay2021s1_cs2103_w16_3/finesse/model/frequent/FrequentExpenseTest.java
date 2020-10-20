package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD_BEVERAGE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.SPOTIFY_SUBSCRIPTION;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentTransactionBuilder;

public class FrequentExpenseTest {
    @Test
    public void equals() {
        // same values -> returns true
        FrequentExpense frequentExpense = new FrequentTransactionBuilder(PHONE_BILL).buildFrequentExpense();
        assertTrue(PHONE_BILL.equals(frequentExpense));

        // same object -> returns true
        assertTrue(PHONE_BILL.equals(PHONE_BILL));

        // null -> returns false
        assertFalse(PHONE_BILL.equals(null));

        // different type -> returns false
        assertFalse(PHONE_BILL.equals(5));

        // different frequent expense -> returns false
        assertFalse(PHONE_BILL.equals(SPOTIFY_SUBSCRIPTION));

        // different title -> returns false
        FrequentExpense editedPhoneBill = new FrequentTransactionBuilder(PHONE_BILL)
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).buildFrequentExpense();
        assertFalse(PHONE_BILL.equals(editedPhoneBill));

        // different amounts -> returns false
        editedPhoneBill = new FrequentTransactionBuilder(PHONE_BILL).withAmount(VALID_AMOUNT_SPOTIFY_SUBSCRIPTION)
                .buildFrequentExpense();
        assertFalse(PHONE_BILL.equals(editedPhoneBill));

        // different categories -> returns false
        editedPhoneBill = new FrequentTransactionBuilder(PHONE_BILL).withCategories(VALID_CATEGORY_FOOD_BEVERAGE)
                .buildFrequentExpense();
        assertFalse(PHONE_BILL.equals(editedPhoneBill));
    }
}
