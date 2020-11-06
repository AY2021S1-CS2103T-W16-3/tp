package ay2021s1_cs2103_w16_3.finesse.logic;

import java.nio.file.Path;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.logic.time.exceptions.TemporalException;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;
import javafx.collections.ObservableList;

/**
 * API of the Logic component.
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @param uiState The current state of the UI.
     * @return The result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     * @throws TemporalException If irregularities in the system time are detected.
     */
    CommandResult execute(String commandText, UiState uiState)
            throws CommandException, ParseException, TemporalException;

    /** Calculates the budget information in the finance tracker. */
    void calculateBudgetInfo();

    /**
     * Returns the FinanceTracker.
     *
     * @see ay2021s1_cs2103_w16_3.finesse.model.Model#getFinanceTracker()
     */
    ReadOnlyFinanceTracker getFinanceTracker();

    /** Returns an unmodifiable view of the filtered list of transactions. */
    ObservableList<Transaction> getFilteredTransactionList();

    /** Returns an unmodifiable view of the filtered list of expenses. */
    ObservableList<Expense> getFilteredExpenseList();

    /** Returns an unmodifiable view of the filtered list of incomes. */
    ObservableList<Income> getFilteredIncomeList();

    /** Returns an unmodifiable view of the filtered list of bookmark expenses. */
    ObservableList<BookmarkExpense> getFilteredBookmarkExpenseList();

    /** Returns an unmodifiable view of the filtered list of bookmark incomes. */
    ObservableList<BookmarkIncome> getFilteredBookmarkIncomeList();
    /** Returns the monthly budget. */
    MonthlyBudget getMonthlyBudget();

    /**
     * Returns the user prefs' finance tracker file path.
     */
    Path getFinanceTrackerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
