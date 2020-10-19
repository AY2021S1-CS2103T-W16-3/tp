package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequentcommands;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.AddFrequentExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyUserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentExpenseBuilder;
import javafx.collections.ObservableList;

public class AddFrequentExpenseCommandTest {

    @Test
    public void constructor_nullFrequentExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFrequentExpenseCommand(null));
    }

    @Test
    public void execute_transactionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFrequentExpenseAdded modelStub = new ModelStubAcceptingFrequentExpenseAdded();
        FrequentExpense validExpense = new FrequentExpenseBuilder().buildFrequentExpense();

        CommandResult commandResult = new AddFrequentExpenseCommand(validExpense).execute(modelStub);

        assertEquals(String.format(AddFrequentExpenseCommand.MESSAGE_SUCCESS, validExpense),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validExpense), modelStub.frequentExpensesAdded);
    }

    @Test
    public void equals() {
        FrequentExpense bubbleTea = new FrequentExpenseBuilder().withTitle("Bubble Tea").buildFrequentExpense();
        FrequentExpense tuitionFees = new FrequentExpenseBuilder().withTitle("Tuition Fees").buildFrequentExpense();
        AddFrequentExpenseCommand addBubbleTeaCommand = new AddFrequentExpenseCommand(bubbleTea);
        AddFrequentExpenseCommand addTuitionFeesCommand = new AddFrequentExpenseCommand(tuitionFees);

        // same object -> returns true
        assertTrue(addBubbleTeaCommand.equals(addBubbleTeaCommand));

        // same values -> returns true
        AddFrequentExpenseCommand addBubbleTeaCommandCopy = new AddFrequentExpenseCommand(bubbleTea);
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
        public void deleteFrequentExpense(FrequentExpense target) {
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
        public void addFrequentExpense(FrequentExpense frequentExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(Transaction target, Transaction editedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFrequentExpense(FrequentExpense target, FrequentExpense editedFrequentExpense) {
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
        public ObservableList<FrequentExpense> getFilteredFrequentExpenseList() {
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
        public void updateFilteredFrequentExpenseList(Predicate<FrequentExpense> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingFrequentExpenseAdded extends ModelStub {
        final ArrayList<FrequentExpense> frequentExpensesAdded = new ArrayList<>();

        @Override
        public void addFrequentExpense(FrequentExpense expense) {
            requireNonNull(expense);
            frequentExpensesAdded.add(expense);
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            return new FinanceTracker();
        }
    }

}
