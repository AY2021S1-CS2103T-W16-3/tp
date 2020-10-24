package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

/**
 * Tab pane that displays analytics.
 */
public class AnalyticsTabPane extends UiPart<Canvas> {

    private static final String FXML = "AnalyticsTabPane.fxml";

    @FXML
    private HBox expenseAnalyticsBox;

    @FXML
    private HBox incomeAnalyticsBox;

    @FXML
    private HBox savingsAnalyticsBox;

    @FXML
    private BarChart<String, Number> expenseAnalytics;

    @FXML
    private BarChart<String, Number> incomeAnalytics;

    @FXML
    private BarChart<String, Number> savingsAnalytics;

    /**
     * Creates an {@code AnalyticsTabPane}.
     */
    public AnalyticsTabPane(MonthlyBudget monthlyBudget) {
        super(FXML);

        initialize();
    }

    private void initialize() {
        initializeBarChart(expenseAnalytics);
        initializeBarChart(incomeAnalytics);
        initializeBarChart(savingsAnalytics);

        expenseAnalyticsBox.getChildren().add(expenseAnalytics);
        incomeAnalyticsBox.getChildren().add(incomeAnalytics);
        savingsAnalyticsBox.getChildren().add(savingsAnalytics);
    }

    private void initializeBarChart(BarChart<String, Number> barChart) {
        Axis<String> stringAxis = new CategoryAxis();
        Axis<Number> numberAxis = new NumberAxis();
        barChart = new BarChart<>(stringAxis, numberAxis);
    }

    private void populateData() {
        //populateDataIn(expenseAnalytics);
        //populateDataIn(incomeAnalytics);
        //populateDataIn(savingsAnalytics);
    }

    private void populateDataIn(BarChart<String,Number> barChart, List<String> strings, List<? extends Number> values) {
        assert strings.size() == values.size();

        XYChart.Series<String,Number> series = new XYChart.Series<>();

        for(int i = 0; i < strings.size(); i++) {
            series.getData().add(new XYChart.Data<>(strings.get(i), values.get(i)));
        }

        barChart.getData().add(series);
    }
}
