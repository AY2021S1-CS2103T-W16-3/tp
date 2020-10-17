package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showIncomeAtIndex;
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
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteIncomeCommand}.
 */
public class DeleteIncomeCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Income incomeToDelete = model.getFilteredIncomeList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteCommandStub superCommand = new DeleteCommandStub(INDEX_FIRST_TRANSACTION);
        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(superCommand);

        String expectedMessage = String.format(DeleteIncomeCommand.MESSAGE_DELETE_INCOME_SUCCESS, incomeToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteIncome(incomeToDelete);

        assertCommandSuccess(deleteIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        DeleteCommandStub superCommand = new DeleteCommandStub(outOfBoundIndex);
        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(superCommand);

        assertCommandFailure(deleteIncomeCommand, model, MESSAGE_INVALID_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showIncomeAtIndex(model, INDEX_FIRST_TRANSACTION);

        Income incomeToDelete = model.getFilteredIncomeList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteCommandStub superCommand = new DeleteCommandStub(INDEX_FIRST_TRANSACTION);
        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(superCommand);

        String expectedMessage = String.format(DeleteIncomeCommand.MESSAGE_DELETE_INCOME_SUCCESS, incomeToDelete);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteIncome(incomeToDelete);
        showNoIncomes(expectedModel);

        assertCommandSuccess(deleteIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showIncomeAtIndex(model, INDEX_FIRST_TRANSACTION);

        Index outOfBoundIndex = INDEX_SECOND_TRANSACTION;
        // Ensures that outOfBoundIndex is still within the boundaries of the finance tracker's list of incomes.
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getIncomeList().size());

        DeleteCommandStub superCommand = new DeleteCommandStub(outOfBoundIndex);
        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(superCommand);

        assertCommandFailure(deleteIncomeCommand, model, MESSAGE_INVALID_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommandStub firstSuperCommand = new DeleteCommandStub(INDEX_FIRST_TRANSACTION);
        DeleteIncomeCommand firstDeleteIncomeCommand = new DeleteIncomeCommand(firstSuperCommand);
        DeleteCommand secondSuperCommand = new DeleteCommand(INDEX_SECOND_TRANSACTION);
        DeleteIncomeCommand secondDeleteIncomeCommand = new DeleteIncomeCommand(secondSuperCommand);

        // same object -> returns true
        assertTrue(firstDeleteIncomeCommand.equals(firstDeleteIncomeCommand));

        // same values -> returns true
        DeleteCommandStub firstSuperCommandCopy = new DeleteCommandStub(INDEX_FIRST_TRANSACTION);
        DeleteIncomeCommand firstDeleteIncomeCommandCopy = new DeleteIncomeCommand(firstSuperCommandCopy);
        assertTrue(firstDeleteIncomeCommand.equals(firstDeleteIncomeCommandCopy));

        // different types -> returns false
        assertFalse(firstDeleteIncomeCommand.equals(1));

        // null -> returns false
        assertFalse(firstDeleteIncomeCommand.equals(null));

        // different incomes -> returns false
        assertFalse(firstDeleteIncomeCommand.equals(secondDeleteIncomeCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no incomes.
     */
    private void showNoIncomes(Model model) {
        model.updateFilteredIncomeList(p -> false);

        assertTrue(model.getFilteredIncomeList().isEmpty());
    }
}
