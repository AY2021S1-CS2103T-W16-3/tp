package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

/**
 * Tab pane that displays analytics.
 */
public class AnalyticsTabPane extends UiPart<Canvas> {

    private static final String FXML = "AnalyticsTabPane.fxml";

    @FXML
    private StackPane pane;

    @FXML
    private BarChart<String,Number> analytics;

    /**
     * Creates an {@code AnalyticsTabPane}.
     */
    public AnalyticsTabPane(MonthlyBudget monthlyBudget) {
        super(FXML);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        analytics = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("cat", 1));

        analytics.getData().add(series);

        pane.getChildren().add(analytics);
    }

    // TODO: Add visualizations.


}
