package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmarkcommands;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs.ModelStub;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.BookmarkTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.DuplicateBookmarkTransactionException;

public class AddBookmarkExpenseCommandTest {

    @Test
    public void constructor_nullBookmarkExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBookmarkExpenseCommand(null));
    }

    @Test
    public void execute_bookmarkExpenseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookmarkExpenseAdded modelStub = new ModelStubAcceptingBookmarkExpenseAdded();
        BookmarkExpense validBookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();

        CommandResult commandResult = new AddBookmarkExpenseCommand(validBookmarkExpense).execute(modelStub);

        assertEquals(String.format(AddBookmarkExpenseCommand.MESSAGE_SUCCESS, validBookmarkExpense),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBookmarkExpense), modelStub.bookmarkExpensesAdded);
    }

    @Test
    public void execute_duplicateBookmarkExpense_throwsDuplicateBookmarkTransactionException() {
        ModelStubRejectingDuplicateBookmarkExpense modelStub = new ModelStubRejectingDuplicateBookmarkExpense();
        BookmarkExpense validBookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        AddBookmarkExpenseCommand addBookmarkExpenseCommand = new AddBookmarkExpenseCommand(validBookmarkExpense);
        assertThrows(CommandException.class, () -> addBookmarkExpenseCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        BookmarkExpense bubbleTea = new BookmarkTransactionBuilder().withTitle("Bubble Tea").buildBookmarkExpense();
        BookmarkExpense tuitionFees = new BookmarkTransactionBuilder().withTitle("Tuition Fees").buildBookmarkExpense();
        AddBookmarkExpenseCommand addBubbleTeaCommand = new AddBookmarkExpenseCommand(bubbleTea);
        AddBookmarkExpenseCommand addTuitionFeesCommand = new AddBookmarkExpenseCommand(tuitionFees);

        // same object -> returns true
        assertTrue(addBubbleTeaCommand.equals(addBubbleTeaCommand));

        // same values -> returns true
        AddBookmarkExpenseCommand addBubbleTeaCommandCopy = new AddBookmarkExpenseCommand(bubbleTea);
        assertTrue(addBubbleTeaCommand.equals(addBubbleTeaCommandCopy));

        // different types -> returns false
        assertFalse(addBubbleTeaCommand.equals(1));

        // null -> returns false
        assertFalse(addBubbleTeaCommand.equals(null));

        // different transaction -> returns false
        assertFalse(addBubbleTeaCommand.equals(addTuitionFeesCommand));
    }

    /**
     * A model stub that always accepts the bookmark expense being added.
     */
    private class ModelStubAcceptingBookmarkExpenseAdded extends ModelStub {
        final ArrayList<BookmarkExpense> bookmarkExpensesAdded = new ArrayList<>();

        @Override
        public void addBookmarkExpense(BookmarkExpense expense) {
            requireNonNull(expense);
            bookmarkExpensesAdded.add(expense);
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            return new FinanceTracker();
        }
    }

    /**
     * A model stub that always throws a {@code DuplicateBookmarkTransactionException}
     * when a {@code BookmarkExpense} is added.
     */
    private class ModelStubRejectingDuplicateBookmarkExpense extends ModelStub {
        @Override
        public void addBookmarkExpense(BookmarkExpense bookmarkExpense) {
            throw new DuplicateBookmarkTransactionException("Cannot add duplicate bookmark expense.");
        }
    }
}
