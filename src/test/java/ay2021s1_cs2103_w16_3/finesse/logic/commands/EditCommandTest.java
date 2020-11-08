package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand.EditTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    @Test
    public void execute_throwsCommandExcepion() {
        Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        assertCommandFailure(new EditCommand(INDEX_FIRST, new EditTransactionDescriptor()), model,
                MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Test
    public void isAmountOrDateEdited() {
        Amount amount = new Amount(VALID_AMOUNT_BUBBLE_TEA);
        Date date = new Date(VALID_DATE_BUBBLE_TEA);

        EditTransactionDescriptor uneditedEditTransactionDescriptor = new EditTransactionDescriptor();
        assertFalse(uneditedEditTransactionDescriptor.isAmountOrDateEdited());

        EditTransactionDescriptor amountEditedEditTransactionDescriptor = new EditTransactionDescriptor();
        amountEditedEditTransactionDescriptor.setAmount(amount);
        assertTrue(amountEditedEditTransactionDescriptor.isAmountOrDateEdited());

        EditTransactionDescriptor dateEditedEditTransactionDescriptor = new EditTransactionDescriptor();
        dateEditedEditTransactionDescriptor.setDate(date);
        assertTrue(dateEditedEditTransactionDescriptor.isAmountOrDateEdited());

        EditTransactionDescriptor bothEditedEditTransactionDescriptor = new EditTransactionDescriptor();
        bothEditedEditTransactionDescriptor.setAmount(amount);
        bothEditedEditTransactionDescriptor.setDate(date);
        assertTrue(bothEditedEditTransactionDescriptor.isAmountOrDateEdited());
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST, DESC_BUBBLE_TEA);

        // same values -> returns true
        EditTransactionDescriptor copyDescriptor = new EditTransactionDescriptor(DESC_BUBBLE_TEA);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND, DESC_BUBBLE_TEA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST, DESC_INTERNSHIP)));
    }
}
