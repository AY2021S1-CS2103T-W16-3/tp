package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand.EditTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs.EditCommandStub;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditTransactionDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditExpenseCommand.
 */
public class EditExpenseCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Expense editedExpense = new TransactionBuilder().buildExpense();
        EditTransactionDescriptor descriptor =
                new EditTransactionDescriptorBuilder(editedExpense).build();
        EditCommandStub superCommand = new EditCommandStub(INDEX_FIRST, descriptor);
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(superCommand);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredExpenseList().get(0), editedExpense);
        if (descriptor.isAmountOrDateEdited()) {
            expectedModel.calculateBudgetInfo();
        }

        assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel,
                descriptor.isAmountOrDateEdited());
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExpense = Index.fromOneBased(model.getFilteredExpenseList().size());
        Expense lastExpense = model.getFilteredExpenseList().get(indexLastExpense.getZeroBased());

        TransactionBuilder expenseInList = new TransactionBuilder(lastExpense);
        Expense editedExpense = expenseInList.withTitle(VALID_TITLE_INTERNSHIP).withAmount(VALID_AMOUNT_INTERNSHIP)
                .withCategories(VALID_CATEGORY_WORK).buildExpense();

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_INTERNSHIP).withAmount(VALID_AMOUNT_INTERNSHIP)
                .withCategories(VALID_CATEGORY_WORK).build();
        EditCommandStub superCommand = new EditCommandStub(indexLastExpense, descriptor);
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(superCommand);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setTransaction(lastExpense, editedExpense);
        if (descriptor.isAmountOrDateEdited()) {
            expectedModel.calculateBudgetInfo();
        }

        assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel,
                descriptor.isAmountOrDateEdited());
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommandStub superCommand = new EditCommandStub(INDEX_FIRST, new EditTransactionDescriptor());
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(superCommand);
        Expense editedExpense = model.getFilteredExpenseList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());

        assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showExpenseAtIndex(model, INDEX_FIRST);

        Expense expenseInFilteredList = model.getFilteredExpenseList()
                .get(INDEX_FIRST.getZeroBased());
        Expense editedExpense =
                new TransactionBuilder(expenseInFilteredList).withTitle(VALID_TITLE_INTERNSHIP).buildExpense();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_INTERNSHIP).build();
        EditCommandStub superCommand = new EditCommandStub(INDEX_FIRST, descriptor);
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(superCommand);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredExpenseList().get(0), editedExpense);
        if (descriptor.isAmountOrDateEdited()) {
            expectedModel.calculateBudgetInfo();
        }

        assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel,
                descriptor.isAmountOrDateEdited());
    }

    @Test
    public void execute_invalidExpenseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_INTERNSHIP).build();
        EditCommandStub superCommand = new EditCommandStub(outOfBoundIndex, descriptor);
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(superCommand);

        assertCommandFailure(editExpenseCommand, model, MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of finance tracker
     */
    @Test
    public void execute_invalidExpenseIndexFilteredList_failure() {
        showExpenseAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // Ensures that outOfBoundIndex is still within the boundaries of the finance tracker's list of expenses.
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getExpenseList().size());

        EditCommandStub superCommand = new EditCommandStub(outOfBoundIndex,
                new EditTransactionDescriptorBuilder().withTitle(VALID_TITLE_INTERNSHIP).build());
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(superCommand);

        assertCommandFailure(editExpenseCommand, model, MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommandStub superCommand = new EditCommandStub(INDEX_FIRST, DESC_BUBBLE_TEA);
        final EditExpenseCommand standardCommand = new EditExpenseCommand(superCommand);

        // same values -> returns true
        EditTransactionDescriptor copyDescriptor = new EditTransactionDescriptor(DESC_BUBBLE_TEA);
        EditCommandStub superCommandWithSameValues = new EditCommandStub(INDEX_FIRST, copyDescriptor);
        EditExpenseCommand commandWithSameValues = new EditExpenseCommand(superCommandWithSameValues);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditExpenseCommand(
                new EditCommandStub(INDEX_SECOND, DESC_BUBBLE_TEA))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditExpenseCommand(
                new EditCommandStub(INDEX_FIRST, DESC_INTERNSHIP))));
    }
}
