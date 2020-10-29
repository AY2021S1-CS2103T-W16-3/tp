package ay2021s1_cs2103_w16_3.finesse.ui;

import java.util.concurrent.atomic.AtomicBoolean;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * A UI component displaying all the information from monthly budget.
 */
public class SavingsGoalCard extends UiPart<Region> {
    private static final String FXML = "SavingsGoalCard.fxml";
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
    @FXML
    private ImageView savingsPicture;

    /**
     * Creates a {@code }SavingsGoalCard} using the information from the given {@code MonthlyBudget}.
     */
    public SavingsGoalCard(MonthlyBudget monthlyBudget) {
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

        StringBinding budgetStatusBinding = Bindings.createStringBinding(() ->
                        String.format(monthlyBudget.getRemainingBudget().getAmount().isNonNegative()
                                        ? "Remaining Budget: %s"
                                        : "Budget Deficit: %s",
                                monthlyBudget.getRemainingBudget().getAmount().toString()),
                monthlyBudget.getRemainingBudget().getObservableAmount());

        StringBinding savingsStatusBinding = Bindings.createStringBinding(() ->
                        String.format(monthlyBudget.getCurrentSavings().getAmount().isNonNegative()
                                        ? "Current Savings: %s"
                                        : "Savings Deficit: %s",
                                monthlyBudget.getCurrentSavings().getAmount().toString()),
                monthlyBudget.getCurrentSavings().getObservableAmount());

        monthlyExpenseLimit.textProperty().bind(expenseLimitBinding);
        monthlySavingsGoal.textProperty().bind(savingsGoalBinding);
        budgetStatus.textProperty().bind(budgetStatusBinding);
        savingsStatus.textProperty().bind(savingsStatusBinding);

        if (!monthlyBudget.getRemainingBudget().getAmount().isNonNegative()) {
            budgetStatus.setStyle("-fx-text-fill: red");
        }
        if (!monthlyBudget.getCurrentSavings().getAmount().isNonNegative()) {
            savingsStatus.setStyle("-fx-text-fill: red");
        }

        savingsPicture.setImage(savingsPanelPicture);
    }
}
