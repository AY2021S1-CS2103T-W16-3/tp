package ay2021s1_cs2103_w16_3.finesse.ui;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;


public class SavingsGoalPanel extends UiPart<Region> {
    private static final String FXML = "SavingsGoalPanel.fxml";
    private static final String header = "Monthly Savings Goal: $200.00";
    private static final String content = "You are so close to reaching your "
            + "monthly savings goal! $100.00 more "
            + "to go. You got this!";
    private final Image savingsPanelPicture = new Image(this.getClass()
            .getResourceAsStream("/images/SavingsImage.png"));


    @FXML
    private ImageView savingsPicture;
    @FXML
    private Label savingsGoalHeader;
    @FXML
    private Label savingsGoalContent;

    /**
     * Constructor of SavingsGoalPanel.
     */
    public SavingsGoalPanel(MonthlyBudget monthlyBudget) {
        super(FXML);
        savingsPicture.setImage(savingsPanelPicture);
        savingsGoalHeader.setText(header);
        StringBinding expenseLimitBinding = Bindings.createStringBinding(() ->
                        String.format(
                                "Monthly Expense Limit: $%s",
                                monthlyBudget.getMonthlyExpenseLimit().getValue().toString()),
                monthlyBudget.getMonthlyExpenseLimit().getObservableValue());
        savingsGoalContent.textProperty().bind(expenseLimitBinding);
    }
}
