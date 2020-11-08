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
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs.ModelStub;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.BookmarkTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.DuplicateBookmarkTransactionException;

public class AddBookmarkIncomeCommandTest {

    @Test
    public void constructor_nullBookmarkIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBookmarkIncomeCommand(null));
    }

    @Test
    public void execute_bookmarkIncomeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookmarkIncomeAdded modelStub = new ModelStubAcceptingBookmarkIncomeAdded();
        BookmarkIncome validBookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();

        CommandResult commandResult = new AddBookmarkIncomeCommand(validBookmarkIncome).execute(modelStub);

        assertEquals(String.format(AddBookmarkIncomeCommand.MESSAGE_SUCCESS, validBookmarkIncome),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBookmarkIncome), modelStub.bookmarkIncomesAdded);
    }

    @Test
    public void execute_duplicateBookmarkIncome_throwsDuplicateBookmarkTransactionException() {
        ModelStubRejectingDuplicateBookmarkIncome modelStub = new ModelStubRejectingDuplicateBookmarkIncome();
        BookmarkIncome validBookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();
        AddBookmarkIncomeCommand addBookmarkIncomeCommand = new AddBookmarkIncomeCommand(validBookmarkIncome);
        assertThrows(CommandException.class, () -> addBookmarkIncomeCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        BookmarkIncome bubbleTea = new BookmarkTransactionBuilder().withTitle("Bubble Tea").buildBookmarkIncome();
        BookmarkIncome tuitionFees = new BookmarkTransactionBuilder().withTitle("Tuition Fees").buildBookmarkIncome();
        AddBookmarkIncomeCommand addBubbleTeaCommand = new AddBookmarkIncomeCommand(bubbleTea);
        AddBookmarkIncomeCommand addTuitionFeesCommand = new AddBookmarkIncomeCommand(tuitionFees);

        // same object -> returns true
        assertTrue(addBubbleTeaCommand.equals(addBubbleTeaCommand));

        // same values -> returns true
        AddBookmarkIncomeCommand addBubbleTeaCommandCopy = new AddBookmarkIncomeCommand(bubbleTea);
        assertTrue(addBubbleTeaCommand.equals(addBubbleTeaCommandCopy));

        // different types -> returns false
        assertFalse(addBubbleTeaCommand.equals(1));

        // null -> returns false
        assertFalse(addBubbleTeaCommand.equals(null));

        // different transaction -> returns false
        assertFalse(addBubbleTeaCommand.equals(addTuitionFeesCommand));
    }

    /**
     * A model stub that always accepts the bookmark income being added.
     */
    private class ModelStubAcceptingBookmarkIncomeAdded extends ModelStub {
        final ArrayList<BookmarkIncome> bookmarkIncomesAdded = new ArrayList<>();

        @Override
        public void addBookmarkIncome(BookmarkIncome bookmarkIncome) {
            requireNonNull(bookmarkIncome);
            bookmarkIncomesAdded.add(bookmarkIncome);
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            return new FinanceTracker();
        }
    }

    /**
     * A model stub that always throws a {@code DuplicateBookmarkTransactionException}
     * when a {@code BookmarkIncome} is added.
     */
    private class ModelStubRejectingDuplicateBookmarkIncome extends ModelStub {
        @Override
        public void addBookmarkIncome(BookmarkIncome bookmarkIncome) {
            throw new DuplicateBookmarkTransactionException("Cannot add duplicate bookmark income.");
        }
    }
}
