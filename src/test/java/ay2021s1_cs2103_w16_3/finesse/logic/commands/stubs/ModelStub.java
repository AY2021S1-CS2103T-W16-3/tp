package ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
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
import javafx.collections.ObservableList;

/**
 * A default model stub that has all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public Path getFinanceTrackerFilePath() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void setFinanceTrackerFilePath(Path financeTrackerFilePath) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void addExpense(Expense expense) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void addIncome(Income income) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void addBookmarkExpense(BookmarkExpense bookmarkExpense) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void addBookmarkIncome(BookmarkIncome bookmarkIncome) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void setFinanceTracker(ReadOnlyFinanceTracker newData) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public ReadOnlyFinanceTracker getFinanceTracker() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void deleteTransaction(Transaction target) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void deleteBookmarkExpense(BookmarkExpense target) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void deleteBookmarkIncome(BookmarkIncome target) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void setBookmarkExpense(BookmarkExpense target, BookmarkExpense editedBookmarkExpense) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void setBookmarkIncome(BookmarkIncome target, BookmarkIncome editedBookmarkIncome) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public MonthlyBudget getMonthlyBudget() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void setExpenseLimit(Amount limit) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void setSavingsGoal(Amount goal) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void calculateBudgetInfo() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public ObservableList<Income> getFilteredIncomeList() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public ObservableList<BookmarkExpense> getFilteredBookmarkExpenseList() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public ObservableList<BookmarkIncome> getFilteredBookmarkIncomeList() {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void updateFilteredTransactionList(List<Predicate<Transaction>> predicates) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void updateFilteredExpenseList(Predicate<Transaction> predicate) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void updateFilteredExpenseList(List<Predicate<Transaction>> predicates) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void updateFilteredIncomeList(Predicate<Transaction> predicate) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void updateFilteredIncomeList(List<Predicate<Transaction>> predicates) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void updateFilteredBookmarkExpenseList(Predicate<BookmarkExpense> predicate) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public void updateFilteredBookmarkIncomeList(Predicate<BookmarkIncome> predicate) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }
}
