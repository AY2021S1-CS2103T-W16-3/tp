package ay2021s1_cs2103_w16_3.finesse.model.budget;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
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
    private static final String[] MONTHS = new DateFormatSymbols().getMonths();
    private static final int NUM_OF_MONTHS = 12;

    private ObservableAmount monthlyExpenseLimit;
    private ObservableAmount monthlySavingsGoal;
    private ObservableAmount remainingBudget;
    private ObservableAmount currentSavings;
    private ObservableList<BigDecimal> monthlyExpenses;
    private ObservableList<BigDecimal> monthlyIncomes;
    private ObservableList<BigDecimal> monthlySavings;
    private ObservableList<String> months;

    /**
     * Creates a {@code MonthlyBudget} with an expense limit and savings goal of $0.
     */
    public MonthlyBudget() {
        monthlyExpenseLimit = new ObservableAmount();
        monthlySavingsGoal = new ObservableAmount();
        remainingBudget = new ObservableAmount();
        currentSavings = new ObservableAmount();
        monthlyExpenses = FXCollections.observableArrayList();
        monthlyIncomes = FXCollections.observableArrayList();
        monthlySavings = FXCollections.observableArrayList();
        months = FXCollections.observableArrayList();
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

    public ObservableList<BigDecimal> getMonthlyExpenses() {
        return FXCollections.unmodifiableObservableList(monthlyExpenses);
    }

    public ObservableList<BigDecimal> getMonthlyIncomes() {
        return FXCollections.unmodifiableObservableList(monthlyIncomes);
    }

    public ObservableList<BigDecimal> getMonthlySavings() {
        return FXCollections.unmodifiableObservableList(monthlySavings);
    }

    public ObservableList<String> getMonths() {
        return FXCollections.unmodifiableObservableList(months);
    }

    /**
     * Calculates all information related to the user's savings.
     * These are:
     * 1. Expenses for the past number of months specified by the user
     * 2. Incomes for the past number of months specified by user
     * 3. Savings for the past number of months specified by user
     * 4. The months to be displayed according to the number of months specified by user
     *
     * @param transactions The current list of transactions.
     * @param numOfMonths The number of months of data the user wishes to display.
     */
    public void calculateBudgetInfo(TransactionList transactions, int numOfMonths) {
        this.monthlyExpenses.clear();
        this.monthlyIncomes.clear();
        this.months.clear();

        YearMonth today = YearMonth.now();
        int thisMonthValue = today.getMonthValue();
        BigDecimal[] monthlyExpenses = new BigDecimal[numOfMonths];
        BigDecimal[] monthlyIncomes = new BigDecimal[numOfMonths];
        Arrays.fill(monthlyExpenses, BigDecimal.ZERO);
        Arrays.fill(monthlyIncomes, BigDecimal.ZERO);

        for (Transaction transaction: transactions) {
            int monthsBeforeToday = (int) ChronoUnit.MONTHS.between(YearMonth.from(transaction.getDateValue()), today);
            if (monthsBeforeToday < numOfMonths) {
                if (transaction instanceof Expense) {
                    monthlyExpenses[numOfMonths - 1 - monthsBeforeToday] =
                            monthlyExpenses[numOfMonths - 1 - monthsBeforeToday]
                                    .add(transaction.getAmount().getValue());
                } else {
                    monthlyIncomes[numOfMonths - 1 - monthsBeforeToday] =
                            monthlyIncomes[numOfMonths - 1 - monthsBeforeToday]
                                    .add(transaction.getAmount().getValue());
                }
            }
        }

        this.monthlyExpenses.addAll(Arrays.asList(monthlyExpenses));
        this.monthlyIncomes.addAll(Arrays.asList(monthlyIncomes));

        for (int i = numOfMonths; i > 0; i--) {
            int monthIndex = thisMonthValue - i < 0 ? NUM_OF_MONTHS - (thisMonthValue - i) : thisMonthValue - i;
            months.add(MONTHS[monthIndex]);
        }

        calculateBudget();
        calculateSavings();
    }

    /**
     * Calculates the user's remaining budget for the month
     * by deducting this month's expenses from the monthly expense limit.
     * If the result is positive, the result is set as the remaining budget.
     * If the result is negative, the magnitude of the result is set as the budget deficit.
     */
    public void calculateBudget() {
        BigDecimal expenseLimit = monthlyExpenseLimit.getAmount().getValue();
        BigDecimal remainingBudget = expenseLimit.subtract(monthlyExpenses.get(monthlyExpenses.size() - 1));
        setRemainingBudget(Amount.of(remainingBudget));
    }

    /**
     * Calculates the user's current savings for the month
     * by deducting this month's expenses from this month's incomes.
     * If the result is positive, the result is set as the current savings.
     * If the result is negative, the magnitude of the result is set as the savings deficit.
     */
    public void calculateSavings() {
        monthlySavings.clear();
        for (int i = 0; i < monthlyIncomes.size(); i++) {
            BigDecimal savings = monthlyIncomes.get(i).subtract(monthlyExpenses.get(i));
            monthlySavings.add(savings);
        }
        BigDecimal thisMonthSavings = monthlySavings.get(monthlyIncomes.size() - 1);
        setCurrentSavings(Amount.of(thisMonthSavings));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MonthlyBudget // instanceof handles nulls
                && monthlyExpenseLimit.equals(((MonthlyBudget) other).monthlyExpenseLimit));
    }
}
