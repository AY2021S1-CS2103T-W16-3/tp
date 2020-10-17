package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs.DeleteCommandStub;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteExpenseCommand}.
 */
public class DeleteExpenseCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteCommandStub superCommand = new DeleteCommandStub(INDEX_FIRST_TRANSACTION);
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(superCommand);

        String expectedMessage = String.format(DeleteExpenseCommand.MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteTransaction(expenseToDelete);

        assertCommandSuccess(deleteExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        DeleteCommandStub superCommand = new DeleteCommandStub(outOfBoundIndex);
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(superCommand);

        assertCommandFailure(deleteExpenseCommand, model, MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showExpenseAtIndex(model, INDEX_FIRST_TRANSACTION);

        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteCommandStub superCommand = new DeleteCommandStub(INDEX_FIRST_TRANSACTION);
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(superCommand);

        String expectedMessage = String.format(DeleteExpenseCommand.MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteTransaction(expenseToDelete);
        showNoExpenses(expectedModel);

        assertCommandSuccess(deleteExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showExpenseAtIndex(model, INDEX_FIRST_TRANSACTION);

        Index outOfBoundIndex = INDEX_SECOND_TRANSACTION;
        // Ensures that outOfBoundIndex is still within the boundaries of the finance tracker's list of expenses.
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getExpenseList().size());

        DeleteCommandStub superCommand = new DeleteCommandStub(outOfBoundIndex);
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(superCommand);

        assertCommandFailure(deleteExpenseCommand, model, MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommandStub firstSuperCommand = new DeleteCommandStub(INDEX_FIRST_TRANSACTION);
        DeleteExpenseCommand firstDeleteExpenseCommand = new DeleteExpenseCommand(firstSuperCommand);
        DeleteCommand secondSuperCommand = new DeleteCommand(INDEX_SECOND_TRANSACTION);
        DeleteExpenseCommand secondDeleteExpenseCommand = new DeleteExpenseCommand(secondSuperCommand);

        // same object -> returns true
        assertTrue(firstDeleteExpenseCommand.equals(firstDeleteExpenseCommand));

        // same values -> returns true
        DeleteCommandStub firstSuperCommandCopy = new DeleteCommandStub(INDEX_FIRST_TRANSACTION);
        DeleteExpenseCommand firstDeleteExpenseCommandCopy = new DeleteExpenseCommand(firstSuperCommandCopy);
        assertTrue(firstDeleteExpenseCommand.equals(firstDeleteExpenseCommandCopy));

        // different types -> returns false
        assertFalse(firstDeleteExpenseCommand.equals(1));

        // null -> returns false
        assertFalse(firstDeleteExpenseCommand.equals(null));

        // different expenses -> returns false
        assertFalse(firstDeleteExpenseCommand.equals(secondDeleteExpenseCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no expenses.
     */
    private void showNoExpenses(Model model) {
        model.updateFilteredExpenseList(p -> false);

        assertTrue(model.getFilteredExpenseList().isEmpty());
    }
}
