package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_AMY;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_BOB;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand.EditTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditTransactionDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Transaction editedTransaction = new TransactionBuilder().build();
        EditCommand.EditTransactionDescriptor descriptor =
                new EditTransactionDescriptorBuilder(editedTransaction).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());

        TransactionBuilder transactionInList = new TransactionBuilder(lastTransaction);
        Transaction editedTransaction = transactionInList.withTitle(VALID_TITLE_BOB).withAmount(VALID_AMOUNT_BOB)
                .withCategories(VALID_CATEGORY_HUSBAND).build();

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_BOB).withAmount(VALID_AMOUNT_BOB).withCategories(VALID_CATEGORY_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastTransaction, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setTransaction(lastTransaction, editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION, new EditTransactionDescriptor());
        Transaction editedTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_TRANSACTION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTransactionAtIndex(model, INDEX_FIRST_TRANSACTION);

        Transaction transactionInFilteredList = model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        Transaction editedTransaction =
                new TransactionBuilder(transactionInFilteredList).withTitle(VALID_TITLE_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION,
                new EditTransactionDescriptorBuilder().withTitle(VALID_TITLE_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of finance tracker
     */
    @Test
    public void execute_invalidTransactionIndexFilteredList_failure() {
        showTransactionAtIndex(model, INDEX_FIRST_TRANSACTION);
        Index outOfBoundIndex = INDEX_SECOND_TRANSACTION;
        // Ensures that outOfBoundIndex is still within the boundaries of the finance tracker's list of transactions.
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getTransactionList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTransactionDescriptorBuilder().withTitle(VALID_TITLE_BOB).build());

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_TRANSACTION, DESC_AMY);

        // same values -> returns true
        EditCommand.EditTransactionDescriptor copyDescriptor = new EditCommand.EditTransactionDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_TRANSACTION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_TRANSACTION, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_TRANSACTION, DESC_BOB)));
    }

}
