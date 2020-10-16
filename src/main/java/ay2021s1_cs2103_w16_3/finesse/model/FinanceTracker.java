package ay2021s1_cs2103_w16_3.finesse.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.ExpenseList;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.IncomeList;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TransactionList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the finance tracker level
 */
public class FinanceTracker implements ReadOnlyFinanceTracker {

    private final TransactionList transactions;
    private final ExpenseList expenses;
    private final IncomeList incomes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        transactions = new TransactionList();
        expenses = new ExpenseList();
        incomes = new IncomeList();
    }

    public FinanceTracker() {}

    /**
     * Creates an FinanceTracker using the Transactions in the {@code toBeCopied}
     */
    public FinanceTracker(ReadOnlyFinanceTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the transaction list with {@code transactions}.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransactions(transactions);
    }

    /**
     * Resets the existing data of this {@code FinanceTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyFinanceTracker newData) {
        requireNonNull(newData);

        setTransactions(newData.getTransactionList());
    }

    //// transaction-level operations

    /**
     * Adds a transaction to the finance tracker.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Replaces the given transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the finance tracker.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireNonNull(editedTransaction);

        transactions.setTransaction(target, editedTransaction);
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the finance tracker.
     */
    public void removeTransaction(Transaction key) {
        transactions.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return transactions.asUnmodifiableObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        ObservableList<Expense> expensesX = FXCollections.observableArrayList();
        transactions.forEach(t -> {
            if (t instanceof Expense) {
                expensesX.add((Expense) t);
            }
        });
        return FXCollections.unmodifiableObservableList(expensesX);
    }

    @Override
    public ObservableList<Income> getIncomeList() {
        ObservableList<Income> incomesX = FXCollections.observableArrayList();
        transactions.forEach(t -> {
            if (t instanceof Income) {
                incomesX.add((Income) t);
            }
        });
        return FXCollections.unmodifiableObservableList(incomesX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinanceTracker // instanceof handles nulls
                && transactions.equals(((FinanceTracker) other).transactions)
                && expenses.equals(((FinanceTracker) other).expenses)
                && incomes.equals(((FinanceTracker) other).incomes));
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions, expenses, incomes);
    }
}
