package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequentcommands;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_SPOTIFY_SUBSCRIPTION;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentExpenseCommand.EditFrequentExpenseDescriptor;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditFrequentExpenseDescriptorBuilder;

public class EditFrequentExpenseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditFrequentExpenseDescriptor descriptorWithSameValues = new EditFrequentExpenseDescriptor(DESC_PHONE_BILL);
        assertTrue(DESC_PHONE_BILL.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PHONE_BILL.equals(DESC_PHONE_BILL));

        // null -> returns false
        assertFalse(DESC_PHONE_BILL.equals(null));

        // different types -> returns false
        assertFalse(DESC_PHONE_BILL.equals(5));

        // different values -> returns false
        assertFalse(DESC_PHONE_BILL.equals(DESC_SPOTIFY_SUBSCRIPTION));

        // different title -> returns false
        EditFrequentExpenseDescriptor editedAmy = new EditFrequentExpenseDescriptorBuilder(DESC_PHONE_BILL)
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).build();
        assertFalse(DESC_PHONE_BILL.equals(editedAmy));

        // different amount -> returns false
        editedAmy = new EditFrequentExpenseDescriptorBuilder(DESC_PHONE_BILL)
                .withAmount(VALID_AMOUNT_SPOTIFY_SUBSCRIPTION).build();
        assertFalse(DESC_PHONE_BILL.equals(editedAmy));

        // different categories -> returns false
        editedAmy = new EditFrequentExpenseDescriptorBuilder(DESC_PHONE_BILL).withCategories(VALID_CATEGORY_WORK)
                .build();
        assertFalse(DESC_PHONE_BILL.equals(editedAmy));
    }
}
