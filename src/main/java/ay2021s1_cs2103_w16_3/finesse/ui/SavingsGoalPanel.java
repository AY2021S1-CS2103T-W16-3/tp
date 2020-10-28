package ay2021s1_cs2103_w16_3.finesse.ui;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class SavingsGoalPanel extends UiPart<Region> {

    private static final String FXML = "SavingsGoalPanel.fxml";

    @FXML
    private StackPane savingsGoalDataPane;

    /**
     * Constructor of {@code SavingsGoalPanel}
     */
    public SavingsGoalPanel(MonthlyBudget monthlyBudget) {
        super(FXML);
        savingsGoalDataPane.getChildren().add(new SavingsGoalCard(monthlyBudget).getRoot());
    }
}
