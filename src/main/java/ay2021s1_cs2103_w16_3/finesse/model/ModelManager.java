package ay2021s1_cs2103_w16_3.finesse.model;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the finance tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FinanceTracker financeTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Transaction> filteredTransactions;
    private final FilteredList<Transaction> filteredExpenses;
    private final FilteredList<Transaction> filteredIncomes;
    private final FilteredList<FrequentExpense> filteredFrequentExpenses;
    private final FilteredList<FrequentIncome> filteredFrequentIncomes;
    private final MonthlyBudget monthlyBudget;

    /**
     * Initializes a ModelManager with the given financeTracker and userPrefs.
     */
    public ModelManager(ReadOnlyFinanceTracker financeTracker, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(financeTracker, userPrefs);

        logger.fine("Initializing with finance tracker: " + financeTracker + " and user prefs " + userPrefs);

        this.financeTracker = new FinanceTracker(financeTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTransactions = new FilteredList<>(this.financeTracker.getTransactionList());
        filteredExpenses = new FilteredList<>(this.financeTracker.getTransactionList(), PREDICATE_SHOW_ALL_EXPENSES);
        filteredIncomes = new FilteredList<>(this.financeTracker.getTransactionList(), PREDICATE_SHOW_ALL_INCOMES);
        filteredFrequentExpenses = new FilteredList<>(this.financeTracker.getFrequentExpenseList());
        filteredFrequentIncomes = new FilteredList<>(this.financeTracker.getFrequentIncomeList());
        monthlyBudget = this.financeTracker.getMonthlyBudget();
    }

    public ModelManager() {
        this(new FinanceTracker(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFinanceTrackerFilePath() {
        return userPrefs.getFinanceTrackerFilePath();
    }

    @Override
    public void setFinanceTrackerFilePath(Path financeTrackerFilePath) {
        requireNonNull(financeTrackerFilePath);
        userPrefs.setFinanceTrackerFilePath(financeTrackerFilePath);
    }

    //=========== FinanceTracker ================================================================================

    @Override
    public void setFinanceTracker(ReadOnlyFinanceTracker financeTracker) {
        this.financeTracker.resetData(financeTracker);
    }

    @Override
    public ReadOnlyFinanceTracker getFinanceTracker() {
        return financeTracker;
    }

    @Override
    public void deleteTransaction(Transaction target) {
        financeTracker.removeTransaction(target);
    }

    @Override
    public void addExpense(Expense expense) {
        financeTracker.addTransaction(expense);
        updateFilteredExpenseList(PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    @Override
    public void addIncome(Income income) {
        financeTracker.addTransaction(income);
        updateFilteredIncomeList(PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        financeTracker.setTransaction(target, editedTransaction);
    }

    //=========== Frequent Transaction ================================================================================

    @Override
    public void addFrequentExpense(FrequentExpense frequentExpense) {
        financeTracker.addFrequentExpense(frequentExpense);
    }

    @Override
    public void addFrequentIncome(FrequentIncome frequentIncome) {
        financeTracker.addFrequentIncome(frequentIncome);
    }

    @Override
    public void deleteFrequentExpense(FrequentExpense frequentExpense) {
        financeTracker.removeFrequentExpense(frequentExpense);
    }

    @Override
    public void setFrequentExpense(FrequentExpense target, FrequentExpense editedFrequentExpense) {
        requireAllNonNull(target, editedFrequentExpense);

        financeTracker.setFrequentExpense(target, editedFrequentExpense);
    }

    @Override
    public void setFrequentIncome(FrequentIncome target, FrequentIncome editedFrequentIncome) {
        requireAllNonNull(target, editedFrequentIncome);

        financeTracker.setFrequentIncome(target, editedFrequentIncome);
    }

    @Override
    public void updateFilteredFrequentExpenseList(Predicate<FrequentExpense> predicate) {
        requireNonNull(predicate);

        filteredFrequentExpenses.setPredicate(predicate);
    }

    @Override
    public void updateFilteredFrequentIncomeList(Predicate<FrequentIncome> predicate) {
        requireNonNull(predicate);

        filteredFrequentIncomes.setPredicate(predicate);
    }

    //=========== Budget ===========================================================================================
    @Override
    public MonthlyBudget getMonthlyBudget() {
        return monthlyBudget;
    }

    @Override
    public void setExpenseLimit(Amount limit) {
        monthlyBudget.setMonthlyExpenseLimit(limit);
    }

    @Override
    public void setSavingsGoal(Amount goal) {
        monthlyBudget.setMonthlySavingsGoal(goal);
    }

    //=========== Filtered Transaction List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Transaction} backed by the internal transaction list of
     * {@code versionedFinanceTracker}.
     */
    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return filteredTransactions;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal expense list of
     * {@code versionedFinanceTracker}.
     */
    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        ObservableList<Expense> newFilteredExpenses = FXCollections.observableArrayList();
        filteredExpenses.forEach(e -> newFilteredExpenses.add((Expense) e));
        return FXCollections.unmodifiableObservableList(newFilteredExpenses);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Income} backed by the internal income list of
     * {@code versionedFinanceTracker}.
     */
    @Override
    public ObservableList<Income> getFilteredIncomeList() {
        ObservableList<Income> newFilteredIncomes = FXCollections.observableArrayList();
        filteredIncomes.forEach(i -> newFilteredIncomes.add((Income) i));
        return FXCollections.unmodifiableObservableList(newFilteredIncomes);
    }

    /**
     * Returns an unmodifiable view of the list of {@code FrequentExpense} backed by the internal frequent expense
     * list of {@code versionedFinanceTracker}.
     */
    @Override
    public ObservableList<FrequentExpense> getFilteredFrequentExpenseList() {
        return filteredFrequentExpenses;
    }

    /**
     * Returns an unmodifiable view of the list of {@code FrequentIncome} backed by the internal frequent income list of
     * {@code versionedFinanceTracker}.
     */
    @Override
    public ObservableList<FrequentIncome> getFilteredFrequentIncomeList() {
        return filteredFrequentIncomes;
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public void updateFilteredExpenseList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(t -> predicate.test(t) && PREDICATE_SHOW_ALL_EXPENSES.test(t));
    }

    @Override
    public void updateFilteredIncomeList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredIncomes.setPredicate(t -> predicate.test(t) && PREDICATE_SHOW_ALL_INCOMES.test(t));
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return financeTracker.equals(other.financeTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredTransactions.equals(other.filteredTransactions)
                && filteredExpenses.equals(other.filteredExpenses)
                && filteredIncomes.equals(other.filteredIncomes);
    }
}
