package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequentcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showFrequentExpenseAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.ConvertFrequentExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;


public class ConvertFrequentExpenseCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        FrequentExpense frequentExpenseToBeConverted = model.getFinanceTracker().getFrequentExpenseList()
                .get(INDEX_FIRST.getZeroBased());
        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertFrequentExpenseCommand convertFrequentExpenseCommand =
                new ConvertFrequentExpenseCommand(INDEX_FIRST, dateOfConvertedExpense);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        Expense convertedExpense = frequentExpenseToBeConverted.convert(dateOfConvertedExpense);
        expectedModel.addExpense(convertedExpense);

        String expectedMessage = String.format(ConvertFrequentExpenseCommand.MESSAGE_CONVERT_FREQUENT_EXPENSE_SUCCESS,
                convertedExpense);

        assertCommandSuccess(convertFrequentExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFrequentExpenseList().size() + 1);
        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);

        ConvertFrequentExpenseCommand convertFrequentExpenseCommand =
                new ConvertFrequentExpenseCommand(outOfBoundIndex, dateOfConvertedExpense);

        assertCommandFailure(convertFrequentExpenseCommand, model, MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFrequentExpenseAtIndex(model, INDEX_FIRST);

        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);

        FrequentExpense frequentExpenseToBeConverted = model.getFilteredFrequentExpenseList()
                .get(INDEX_FIRST.getZeroBased());
        ConvertFrequentExpenseCommand convertFrequentExpenseCommand =
                new ConvertFrequentExpenseCommand(INDEX_FIRST, dateOfConvertedExpense);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        Expense convertedExpense = frequentExpenseToBeConverted.convert(dateOfConvertedExpense);
        expectedModel.addExpense(convertedExpense);

        String expectedMessage = String.format(ConvertFrequentExpenseCommand.MESSAGE_CONVERT_FREQUENT_EXPENSE_SUCCESS,
                convertedExpense);

        assertCommandSuccess(convertFrequentExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFrequentExpenseAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        // ensures that outOfBoundIndex is still in bounds of frequent expense list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getFrequentExpenseList().size());

        ConvertFrequentExpenseCommand convertFrequentExpenseCommand =
                new ConvertFrequentExpenseCommand(outOfBoundIndex, dateOfConvertedExpense);

        assertCommandFailure(convertFrequentExpenseCommand, model, MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertFrequentExpenseCommand convertFirstFrequentExpenseCommand =
                new ConvertFrequentExpenseCommand(INDEX_FIRST, dateOfConvertedExpense);
        ConvertFrequentExpenseCommand convertSecondFrequentExpenseCommand =
                new ConvertFrequentExpenseCommand(INDEX_SECOND, dateOfConvertedExpense);

        // same object -> returns true
        assertTrue(convertFirstFrequentExpenseCommand.equals(convertFirstFrequentExpenseCommand));

        // same values -> returns true
        ConvertFrequentExpenseCommand deleteFirstCommandCopy =
                new ConvertFrequentExpenseCommand(INDEX_FIRST, dateOfConvertedExpense);
        assertTrue(convertFirstFrequentExpenseCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(convertFirstFrequentExpenseCommand.equals(1));

        // null -> returns false
        assertFalse(convertFirstFrequentExpenseCommand.equals(null));

        // different expense -> returns false
        assertFalse(convertFirstFrequentExpenseCommand.equals(convertSecondFrequentExpenseCommand));
    }
}
