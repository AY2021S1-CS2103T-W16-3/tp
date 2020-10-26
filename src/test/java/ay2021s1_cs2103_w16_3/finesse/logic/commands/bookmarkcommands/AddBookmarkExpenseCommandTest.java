package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmarkcommands;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyUserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.testutil.BookmarkTransactionBuilder;
import javafx.collections.ObservableList;

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
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFinanceTrackerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceTrackerFilePath(Path financeTrackerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceTracker(ReadOnlyFinanceTracker financeTracker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBookmarkExpense(BookmarkExpense target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBookmarkIncome(BookmarkIncome target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIncome(Income income) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBookmarkExpense(BookmarkExpense bookmarkExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBookmarkIncome(BookmarkIncome bookmarkIncome) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(Transaction target, Transaction editedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBookmarkExpense(BookmarkExpense target, BookmarkExpense editedBookmarkExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBookmarkIncome(BookmarkIncome target, BookmarkIncome editedBookmarkIncome) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public MonthlyBudget getMonthlyBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpenseLimit(Amount limit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSavingsGoal(Amount goal) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void calculateBudgetInfo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Income> getFilteredIncomeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<BookmarkExpense> getFilteredBookmarkExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<BookmarkIncome> getFilteredBookmarkIncomeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpenseList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredIncomeList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(List<Predicate<Transaction>> predicates) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpenseList(List<Predicate<Transaction>> predicates) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredIncomeList(List<Predicate<Transaction>> predicates) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBookmarkExpenseList(Predicate<BookmarkExpense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBookmarkIncomeList(Predicate<BookmarkIncome> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the bookmark expense being added.
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

}
