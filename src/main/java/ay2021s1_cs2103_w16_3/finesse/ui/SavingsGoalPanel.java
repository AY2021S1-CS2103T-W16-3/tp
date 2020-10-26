package ay2021s1_cs2103_w16_3.finesse.ui;

import java.util.concurrent.atomic.AtomicBoolean;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

public class SavingsGoalPanel extends UiPart<Region> {
    private static final String FXML = "SavingsGoalPanel.fxml";
    private final Image savingsPanelPicture = new Image(this.getClass()
            .getResourceAsStream("/images/SavingsImage.png"));

    @FXML
    private Label monthlyExpenseLimit;
    @FXML
    private Label monthlySavingsGoal;
    @FXML
    private Label budgetStatus;
    @FXML
    private Label savingsStatus;

    /**
     * Constructor of SavingsGoalPanel.
     */
    public SavingsGoalPanel(MonthlyBudget monthlyBudget) {
        super(FXML);

        AtomicBoolean isBudgetDeficit = new AtomicBoolean();
        AtomicBoolean isSavingsDeficit = new AtomicBoolean();

        StringBinding expenseLimitBinding = Bindings.createStringBinding(() ->
                        String.format(
                                "Monthly Expense Limit: %s",
                                monthlyBudget.getMonthlyExpenseLimit().getAmount().toString()),
                monthlyBudget.getMonthlyExpenseLimit().getObservableAmount());

        StringBinding savingsGoalBinding = Bindings.createStringBinding(() ->
                        String.format(
                                "Monthly Savings Goal: %s",
                                monthlyBudget.getMonthlySavingsGoal().getAmount().toString()),
                monthlyBudget.getMonthlySavingsGoal().getObservableAmount());

        StringBinding budgetStatusBinding = Bindings.createStringBinding(() -> {
            Amount budgetDeficitAmount = monthlyBudget.getBudgetDeficit().getAmount();
            if (budgetDeficitAmount.compareTo(new Amount("0")) > 0) {
                isBudgetDeficit.set(true);
                return String.format("Budget Deficit: %s", budgetDeficitAmount.toString());
            } else {
                return String.format("Remaining Budget: %s",
                        monthlyBudget.getRemainingBudget().getAmount().toString());
            }
        });

        StringBinding savingsStatusBinding = Bindings.createStringBinding(() -> {
            Amount savingsDeficitAmount = monthlyBudget.getSavingsDeficit().getAmount();
            if (savingsDeficitAmount.compareTo(new Amount("0")) > 0) {
                isSavingsDeficit.set(true);
                return String.format("Savings Deficit: %s", savingsDeficitAmount.toString());
            } else {
                return String.format("Current Savings: %s",
                monthlyBudget.getCurrentSavings().getAmount().toString());
            }
        });

        monthlyExpenseLimit.textProperty().bind(expenseLimitBinding);
        monthlySavingsGoal.textProperty().bind(savingsGoalBinding);
        budgetStatus.textProperty().bind(budgetStatusBinding);
        savingsStatus.textProperty().bind(savingsStatusBinding);

        if (isBudgetDeficit.get()) {
            budgetStatus.setStyle("-fx-text-fill: red");
        }
        if (isSavingsDeficit.get()) {
            savingsStatus.setStyle("-fx-text-fill: red");
        }
    }
}
