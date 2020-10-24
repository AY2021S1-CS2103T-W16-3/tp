package ay2021s1_cs2103_w16_3.finesse.model.budget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TransactionList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the monthly budget in the finance tracker.
 */
public class MonthlyBudget {
    private static final Amount ZERO_AMOUNT = new Amount("0");
    private static final int THIS_MONTH_INDEX = 0;

    private ObservableAmount monthlyExpenseLimit;
    private ObservableAmount monthlySavingsGoal;
    private ObservableAmount remainingBudget;
    private ObservableAmount currentSavings;
    private ObservableAmount budgetDeficit;
    private ObservableAmount savingsDeficit;
    private ObservableList<BigDecimal> monthlyExpenses;
    private ObservableList<BigDecimal> monthlyIncomes;
    private ObservableList<BigDecimal> monthlySavings;

    /**
     * Creates a {@code MonthlyBudget} with an expense limit and savings goal of $0.
     */
    public MonthlyBudget() {
        monthlyExpenseLimit = new ObservableAmount();
        monthlySavingsGoal = new ObservableAmount();
        remainingBudget = new ObservableAmount();
        currentSavings = new ObservableAmount();
        budgetDeficit = new ObservableAmount();
        savingsDeficit = new ObservableAmount();
        monthlyExpenses = FXCollections.observableArrayList();
        monthlyIncomes = FXCollections.observableArrayList();
        monthlySavings = FXCollections.observableArrayList();
    }

    public ObservableAmount getMonthlyExpenseLimit() {
        return monthlyExpenseLimit;
    }

    public void setMonthlyExpenseLimit(Amount limit) {
        monthlyExpenseLimit.setValue(limit);
    }

    public ObservableAmount getMonthlySavingsGoal() {
        return monthlySavingsGoal;
    }

    public void setMonthlySavingsGoal(Amount goal) {
        monthlySavingsGoal.setValue(goal);
    }

    public ObservableAmount getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(Amount budget) {
        remainingBudget.setValue(budget);
    }

    public ObservableAmount getCurrentSavings() {
        return currentSavings;
    }

    public void setCurrentSavings(Amount savings) {
        currentSavings.setValue(savings);
    }

    public ObservableAmount getBudgetDeficit() {
        return budgetDeficit;
    }

    public void setBudgetDeficit(Amount deficit) {
        budgetDeficit.setValue(deficit);
    }

    public ObservableAmount getSavingsDeficit() {
        return savingsDeficit;
    }

    public void setSavingsDeficit(Amount deficit) {
        savingsDeficit.setValue(deficit);
    }

    public ObservableList<BigDecimal> getMonthlyExpenses() {
        return FXCollections.unmodifiableObservableList(monthlyExpenses);
    }

    public ObservableList<BigDecimal> getMonthlyIncomes() {
        return FXCollections.unmodifiableObservableList(monthlyIncomes);
    }

    public ObservableList<BigDecimal> getMonthlySavings() {
        return FXCollections.unmodifiableObservableList(monthlySavings);
    }

    /**
     * Calculates all information related to the user's savings.
     * These are:
     * 1. Expenses for the past number of months specified by the user
     * 2. Incomes for the past number of months specified by user
     * 3. Savings for the past number of months specified by user
     *
     * @param transactions The current list of transactions.
     */
    public void calculateBudgetInfo(TransactionList transactions, int numOfMonths) {
        this.monthlyExpenses.clear();
        this.monthlyIncomes.clear();
        int thisMonth = LocalDate.now().getMonthValue();
        BigDecimal[] monthlyExpenses = new BigDecimal[numOfMonths];
        BigDecimal[] monthlyIncomes = new BigDecimal[numOfMonths];
        Arrays.fill(monthlyExpenses, BigDecimal.ZERO);
        Arrays.fill(monthlyIncomes, BigDecimal.ZERO);
        for (Transaction transaction: transactions) {
            int monthsBeforeToday = thisMonth - transaction.getDateValue().getMonthValue();
            if (monthsBeforeToday < numOfMonths) {
                if (transaction instanceof Expense) {
                    monthlyExpenses[monthsBeforeToday] =
                            monthlyExpenses[monthsBeforeToday].add(transaction.getAmount().getValue());
                } else {
                    monthlyIncomes[monthsBeforeToday] =
                            monthlyIncomes[monthsBeforeToday].add(transaction.getAmount().getValue());
                }
            }
        }
        this.monthlyExpenses.addAll(Arrays.asList(monthlyExpenses));
        this.monthlyIncomes.addAll(Arrays.asList(monthlyIncomes));
        calculateBudget();
        calculateSavings();
    }

    /**
     * Calculates the user's remaining budget for the month.
     * Steps:
     * 1. Set the remaining budget to the user's monthly expense limit.
     * 2. For each expense in the list of transactions, deduct its amount from the remaining budget.
     * 3. The minimum remaining budget is capped at 0.
     */
    public void calculateBudget() {
        BigDecimal expenseLimit = monthlyExpenseLimit.getAmount().getValue();
        BigDecimal remainingBudget = expenseLimit.subtract(monthlyExpenses.get(THIS_MONTH_INDEX));
        if (remainingBudget.signum() < 0) {
            setRemainingBudget(ZERO_AMOUNT);
            setBudgetDeficit(new Amount(remainingBudget.negate().toString()));
        } else {
            setRemainingBudget(new Amount(remainingBudget.toString()));
            setBudgetDeficit(ZERO_AMOUNT);
        }
    }

    /**
     * Calculates the user's current savings for the month.
     * Steps:
     * 1. Set the remaining budget to the user's monthly expense limit.
     * 2. For each expense in the list of transactions, deduct its amount from the remaining budget.
     * 3. The minimum remaining budget is capped at 0.
     */
    public void calculateSavings() {
        monthlySavings.clear();
        for (int i = 0; i < monthlyIncomes.size(); i++) {
            BigDecimal savings = monthlyIncomes.get(i).subtract(monthlyExpenses.get(THIS_MONTH_INDEX));
            monthlySavings.add(savings);
        }
        BigDecimal thisMonthSavings = monthlySavings.get(0);
        if (thisMonthSavings.signum() < 0) {
            setCurrentSavings(ZERO_AMOUNT);
            setSavingsDeficit(new Amount(thisMonthSavings.negate().toString()));
        } else {
            setCurrentSavings(new Amount(thisMonthSavings.toString()));
            setSavingsDeficit(ZERO_AMOUNT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MonthlyBudget // instanceof handles nulls
                && monthlyExpenseLimit.equals(((MonthlyBudget) other).monthlyExpenseLimit));
    }
}
