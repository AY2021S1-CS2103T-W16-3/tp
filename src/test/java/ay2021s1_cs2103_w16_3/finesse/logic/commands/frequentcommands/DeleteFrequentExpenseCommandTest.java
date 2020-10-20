package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequentcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showFrequentExpenseAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST_FREQUENT_EXPENSE;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND_FREQUENT_EXPENSE;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.DeleteFrequentExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;

public class DeleteFrequentExpenseCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        FrequentExpense frequentExpenseToDelete = model.getFilteredFrequentExpenseList()
                .get(INDEX_FIRST_FREQUENT_EXPENSE.getZeroBased());
        DeleteFrequentExpenseCommand deleteFrequentExpenseCommand =
                new DeleteFrequentExpenseCommand(INDEX_FIRST_FREQUENT_EXPENSE);

        String expectedMessage = String.format(DeleteFrequentExpenseCommand.MESSAGE_DELETE_FREQUENT_EXPENSE_SUCCESS,
                frequentExpenseToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteFrequentExpense(frequentExpenseToDelete);

        assertCommandSuccess(deleteFrequentExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFrequentExpenseList().size() + 1);
        DeleteFrequentExpenseCommand deleteFrequentExpenseCommand = new DeleteFrequentExpenseCommand(outOfBoundIndex);

        assertCommandFailure(deleteFrequentExpenseCommand, model, MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFrequentExpenseAtIndex(model, INDEX_FIRST_FREQUENT_EXPENSE);

        FrequentExpense frequentExpenseToDelete = model.getFilteredFrequentExpenseList()
                .get(INDEX_FIRST_FREQUENT_EXPENSE.getZeroBased());
        DeleteFrequentExpenseCommand deleteFrequentExpenseCommand =
                new DeleteFrequentExpenseCommand(INDEX_FIRST_FREQUENT_EXPENSE);

        String expectedMessage = String.format(DeleteFrequentExpenseCommand.MESSAGE_DELETE_FREQUENT_EXPENSE_SUCCESS,
                frequentExpenseToDelete);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteFrequentExpense(frequentExpenseToDelete);
        showNoFrequentExpenses(expectedModel);

        assertCommandSuccess(deleteFrequentExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFrequentExpenseAtIndex(model, INDEX_FIRST_FREQUENT_EXPENSE);

        Index outOfBoundIndex = INDEX_SECOND_FREQUENT_EXPENSE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getFrequentExpenseList().size());

        DeleteFrequentExpenseCommand deleteFrequentExpenseCommand = new DeleteFrequentExpenseCommand(outOfBoundIndex);

        assertCommandFailure(deleteFrequentExpenseCommand, model, MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteFrequentExpenseCommand deleteFirstCommand =
                new DeleteFrequentExpenseCommand(INDEX_FIRST_FREQUENT_EXPENSE);
        DeleteFrequentExpenseCommand deleteSecondCommand =
                new DeleteFrequentExpenseCommand(INDEX_SECOND_FREQUENT_EXPENSE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteFrequentExpenseCommand deleteFirstCommandCopy =
                new DeleteFrequentExpenseCommand(INDEX_FIRST_FREQUENT_EXPENSE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different expense -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no frequent expenses.
     */
    private void showNoFrequentExpenses(Model model) {
        model.updateFilteredFrequentExpenseList(p -> false);

        assertTrue(model.getFilteredFrequentExpenseList().isEmpty());
    }
}
