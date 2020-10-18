package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_INTERNSHIP;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand.EditTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditTransactionDescriptorBuilder;

public class EditTransactionDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTransactionDescriptor descriptorWithSameValues = new EditCommand.EditTransactionDescriptor(DESC_BUBBLE_TEA);
        assertTrue(DESC_BUBBLE_TEA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_BUBBLE_TEA.equals(DESC_BUBBLE_TEA));

        // null -> returns false
        assertFalse(DESC_BUBBLE_TEA.equals(null));

        // different types -> returns false
        assertFalse(DESC_BUBBLE_TEA.equals(5));

        // different values -> returns false
        assertFalse(DESC_BUBBLE_TEA.equals(DESC_INTERNSHIP));

        // different title -> returns false
        EditCommand.EditTransactionDescriptor editedAmy = new EditTransactionDescriptorBuilder(DESC_BUBBLE_TEA)
                .withTitle(VALID_TITLE_INTERNSHIP).build();
        assertFalse(DESC_BUBBLE_TEA.equals(editedAmy));

        // different amount -> returns false
        editedAmy = new EditTransactionDescriptorBuilder(DESC_BUBBLE_TEA).withAmount(VALID_AMOUNT_INTERNSHIP).build();
        assertFalse(DESC_BUBBLE_TEA.equals(editedAmy));

        // different date -> returns false
        editedAmy = new EditTransactionDescriptorBuilder(DESC_BUBBLE_TEA).withDate(VALID_DATE_INTERNSHIP).build();
        assertFalse(DESC_BUBBLE_TEA.equals(editedAmy));

        // different categories -> returns false
        editedAmy = new EditTransactionDescriptorBuilder(DESC_BUBBLE_TEA).withCategories(VALID_CATEGORY_WORK).build();
        assertFalse(DESC_BUBBLE_TEA.equals(editedAmy));
    }
}
