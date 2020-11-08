package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmarkcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showBookmarkExpenseAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs.ConvertBookmarkCommandStub;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ConvertBookmarkExpenseCommand}.
 */
public class ConvertBookmarkExpenseCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        BookmarkExpense bookmarkExpenseToBeConverted = model.getFinanceTracker().getBookmarkExpenseList()
                .get(INDEX_FIRST.getZeroBased());
        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertBookmarkCommandStub superCommand = new ConvertBookmarkCommandStub(INDEX_FIRST, dateOfConvertedExpense);
        ConvertBookmarkExpenseCommand convertBookmarkExpenseCommand =
                new ConvertBookmarkExpenseCommand(superCommand);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        Expense convertedExpense = bookmarkExpenseToBeConverted.convert(dateOfConvertedExpense);
        expectedModel.addExpense(convertedExpense);

        String expectedMessage = String.format(ConvertBookmarkExpenseCommand.MESSAGE_CONVERT_BOOKMARK_EXPENSE_SUCCESS,
                convertedExpense);

        assertCommandSuccess(convertBookmarkExpenseCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkExpenseList().size() + 1);
        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);

        ConvertBookmarkCommandStub superCommand =
                new ConvertBookmarkCommandStub(outOfBoundIndex, dateOfConvertedExpense);
        ConvertBookmarkExpenseCommand convertBookmarkExpenseCommand =
                new ConvertBookmarkExpenseCommand(superCommand);

        assertCommandFailure(convertBookmarkExpenseCommand, model, MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookmarkExpenseAtIndex(model, INDEX_FIRST);

        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertBookmarkCommandStub superCommand = new ConvertBookmarkCommandStub(INDEX_FIRST, dateOfConvertedExpense);
        BookmarkExpense bookmarkExpenseToBeConverted = model.getFilteredBookmarkExpenseList()
                .get(INDEX_FIRST.getZeroBased());

        ConvertBookmarkExpenseCommand convertBookmarkExpenseCommand = new ConvertBookmarkExpenseCommand(superCommand);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        Expense convertedExpense = bookmarkExpenseToBeConverted.convert(dateOfConvertedExpense);
        expectedModel.addExpense(convertedExpense);

        String expectedMessage = String.format(ConvertBookmarkExpenseCommand.MESSAGE_CONVERT_BOOKMARK_EXPENSE_SUCCESS,
                convertedExpense);

        assertCommandSuccess(convertBookmarkExpenseCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookmarkExpenseAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        // ensures that outOfBoundIndex is still in bounds of bookmark expense list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getBookmarkExpenseList().size());

        ConvertBookmarkCommandStub superCommand =
                new ConvertBookmarkCommandStub(outOfBoundIndex, dateOfConvertedExpense);
        ConvertBookmarkExpenseCommand convertBookmarkExpenseCommand =
                new ConvertBookmarkExpenseCommand(superCommand);

        assertCommandFailure(convertBookmarkExpenseCommand, model, MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Date dateOfConvertedExpense = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertBookmarkCommandStub firstSuperCommand =
                new ConvertBookmarkCommandStub(INDEX_FIRST, dateOfConvertedExpense);
        ConvertBookmarkExpenseCommand firstConvertBookmarkExpenseCommand =
                new ConvertBookmarkExpenseCommand(firstSuperCommand);
        ConvertBookmarkCommandStub secondSuperCommand =
                new ConvertBookmarkCommandStub(INDEX_SECOND, dateOfConvertedExpense);
        ConvertBookmarkExpenseCommand secondConvertBookmarkExpenseCommand =
                new ConvertBookmarkExpenseCommand(secondSuperCommand);

        // Same object -> returns true
        assertTrue(firstConvertBookmarkExpenseCommand.equals(firstConvertBookmarkExpenseCommand));

        // Same values -> returns true
        ConvertBookmarkCommandStub firstCopySuperCommand =
                new ConvertBookmarkCommandStub(INDEX_FIRST, dateOfConvertedExpense);
        ConvertBookmarkExpenseCommand deleteFirstCommandCopy =
                new ConvertBookmarkExpenseCommand(firstCopySuperCommand);
        assertTrue(firstConvertBookmarkExpenseCommand.equals(deleteFirstCommandCopy));

        // Different types -> returns false
        assertFalse(firstConvertBookmarkExpenseCommand.equals(1));

        // null -> returns false
        assertFalse(firstConvertBookmarkExpenseCommand.equals(null));

        // Different expense -> returns false
        assertFalse(firstConvertBookmarkExpenseCommand.equals(secondConvertBookmarkExpenseCommand));

        // Different dates -> returns false
        ConvertBookmarkCommandStub thirdSuperCommand =
                new ConvertBookmarkCommandStub(INDEX_FIRST, new Date(VALID_DATE_BUBBLE_TEA));
        ConvertBookmarkExpenseCommand thirdConvertBookmarkExpenseCommand =
                new ConvertBookmarkExpenseCommand(thirdSuperCommand);
        assertFalse(firstConvertBookmarkExpenseCommand.equals(thirdConvertBookmarkExpenseCommand));
    }
}
