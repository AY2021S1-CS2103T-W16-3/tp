package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Tab pane that displays analytics.
 */
public class AnalyticsTabPane extends UiPart<Canvas> {

    private static final String FXML = "AnalyticsTabPane.fxml";

    @FXML
    private Pane expenseAnalyticsPane;

    @FXML
    private Pane incomeAnalyticsPane;

    @FXML
    private Pane savingsAnalyticsPane;

    @FXML
    private Label expenseAnalyticsLabel;

    @FXML
    private Label incomeAnalyticsLabel;

    @FXML
    private Label savingsAnalyticsLabel;

    @FXML
    private BarChart<String, Number> expenseAnalyticsBarChart;

    @FXML
    private BarChart<String, Number> incomeAnalyticsBarChart;

    @FXML
    private BarChart<String, Number> savingsAnalyticsBarChart;

    /**
     * Creates an {@code AnalyticsTabPane}.
     */
    public AnalyticsTabPane(MonthlyBudget monthlyBudget) {
        super(FXML);

        Axis<String> expenseAnalyticsStringAxis = new CategoryAxis();
        Axis<Number> expenseAnalyticsNumberAxis = new NumberAxis();
        expenseAnalyticsBarChart = new BarChart<>(expenseAnalyticsStringAxis, expenseAnalyticsNumberAxis);
        expenseAnalyticsBarChart.setVisible(false);
        expenseAnalyticsPane.getChildren().add(expenseAnalyticsBarChart);
        expenseAnalyticsLabel.setText("EXPENSE");

        Axis<String> incomeAnalyticsStringAxis = new CategoryAxis();
        Axis<Number> incomeAnalyticsNumberAxis = new NumberAxis();
        incomeAnalyticsBarChart = new BarChart<>(incomeAnalyticsStringAxis, incomeAnalyticsNumberAxis);
        incomeAnalyticsBarChart.setVisible(false);
        incomeAnalyticsPane.getChildren().add(incomeAnalyticsBarChart);
        incomeAnalyticsLabel.setText("INCOME");

        Axis<String> savingsAnalyticsStringAxis = new CategoryAxis();
        Axis<Number> savingsAnalyticsNumberAxis = new NumberAxis();
        savingsAnalyticsBarChart = new BarChart<>(savingsAnalyticsStringAxis, savingsAnalyticsNumberAxis);
        savingsAnalyticsBarChart.setVisible(false);
        savingsAnalyticsPane.getChildren().add(savingsAnalyticsBarChart);
        savingsAnalyticsLabel.setText("SAVINGS");

        populateDataIn(expenseAnalyticsBarChart, List.of("hello", "bye"), List.of(1, 5));
    }

    private void populateData() {
        //populateDataIn(expenseAnalytics);
        //populateDataIn(incomeAnalytics);
        //populateDataIn(savingsAnalytics);
    }

    private void populateDataIn(BarChart<String, Number> barChart, List<String> strings, List<? extends Number> values) {
        assert strings.size() == values.size();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for(int i = 0; i < strings.size(); i++) {
            series.getData().add(new XYChart.Data<>(strings.get(i), values.get(i)));
        }

        barChart.getData().add(series);
    }
}
