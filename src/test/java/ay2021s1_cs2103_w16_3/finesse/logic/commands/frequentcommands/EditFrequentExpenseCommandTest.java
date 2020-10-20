package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequentcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_MISCELLANEOUS;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showFrequentExpenseAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST_FREQUENT_EXPENSE;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND_FREQUENT_EXPENSE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentExpenseCommand.EditFrequentExpenseDescriptor;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditFrequentExpenseDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TypicalFrequentTransactions;

public class EditFrequentExpenseCommandTest {

    private Model model = new ModelManager(TypicalFrequentTransactions.getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        FrequentExpense editedFrequentExpense = new FrequentTransactionBuilder().buildFrequentExpense();
        EditFrequentExpenseDescriptor descriptor =
                new EditFrequentExpenseDescriptorBuilder(editedFrequentExpense).build();
        EditFrequentExpenseCommand editFrequentExpenseCommand =
                new EditFrequentExpenseCommand(INDEX_FIRST_FREQUENT_EXPENSE, descriptor);

        String expectedMessage = String.format(EditFrequentExpenseCommand.MESSAGE_EDIT_FREQUENT_EXPENSE_SUCCESS,
                editedFrequentExpense);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setFrequentExpense(model.getFilteredFrequentExpenseList().get(0), editedFrequentExpense);

        assertCommandSuccess(editFrequentExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExpense = Index.fromOneBased(model.getFilteredFrequentExpenseList().size());
        FrequentExpense lastExpense = model.getFilteredFrequentExpenseList().get(indexLastExpense.getZeroBased());

        FrequentTransactionBuilder expenseInList = new FrequentTransactionBuilder(lastExpense);
        FrequentExpense editedExpense = expenseInList.withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION)
                .withAmount(VALID_AMOUNT_SPOTIFY_SUBSCRIPTION)
                .withCategories(VALID_CATEGORY_MISCELLANEOUS).buildFrequentExpense();

        EditFrequentExpenseDescriptor descriptor = new EditFrequentExpenseDescriptorBuilder()
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).withAmount(VALID_AMOUNT_SPOTIFY_SUBSCRIPTION)
                .withCategories(VALID_CATEGORY_MISCELLANEOUS).build();
        EditFrequentExpenseCommand editFrequentExpenseCommand =
                new EditFrequentExpenseCommand(indexLastExpense, descriptor);

        String expectedMessage = String.format(EditFrequentExpenseCommand.MESSAGE_EDIT_FREQUENT_EXPENSE_SUCCESS,
                editedExpense);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setFrequentExpense(lastExpense, editedExpense);

        assertCommandSuccess(editFrequentExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidFrequentExpenseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFrequentExpenseList().size() + 1);
        EditFrequentExpenseDescriptor descriptor = new EditFrequentExpenseDescriptorBuilder()
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).build();
        EditFrequentExpenseCommand editExpenseCommand = new EditFrequentExpenseCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editExpenseCommand, model, MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX);
    }


    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidFrequentExpenseIndexFilteredList_failure() {
        showFrequentExpenseAtIndex(model, INDEX_FIRST_FREQUENT_EXPENSE);
        Index outOfBoundIndex = INDEX_SECOND_FREQUENT_EXPENSE;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getFrequentExpenseList().size());

        EditFrequentExpenseCommand editRecurringCommand = new EditFrequentExpenseCommand(outOfBoundIndex,
                new EditFrequentExpenseDescriptorBuilder().withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).build());

        assertCommandFailure(editRecurringCommand, model, MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditFrequentExpenseCommand standardCommand =
                new EditFrequentExpenseCommand(INDEX_FIRST_FREQUENT_EXPENSE, DESC_SPOTIFY_SUBSCRIPTION);

        // same values -> returns true
        EditFrequentExpenseDescriptor copyDescriptor = new EditFrequentExpenseDescriptor(DESC_SPOTIFY_SUBSCRIPTION);
        EditFrequentExpenseCommand commandWithSameValues =
                new EditFrequentExpenseCommand(INDEX_FIRST_FREQUENT_EXPENSE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand
                .equals(new EditFrequentExpenseCommand(INDEX_SECOND_FREQUENT_EXPENSE, DESC_SPOTIFY_SUBSCRIPTION)));

        // different descriptor -> returns false
        assertFalse(standardCommand
                .equals(new EditFrequentExpenseCommand(INDEX_FIRST_FREQUENT_EXPENSE, DESC_PHONE_BILL)));
    }
}
