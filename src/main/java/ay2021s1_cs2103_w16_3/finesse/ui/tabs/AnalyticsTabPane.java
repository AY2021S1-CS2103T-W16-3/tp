package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import ay2021s1_cs2103_w16_3.finesse.ui.visualizations.BarChartDemoViz;
import io.data2viz.viz.JFxVizRenderer;
import io.data2viz.viz.Viz;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

/**
 * Tab pane that displays analytics.
 */
public class AnalyticsTabPane extends UiPart<Canvas> {
    private static final String FXML = "AnalyticsTabPane.fxml";

    @FXML
    private Canvas baseCanvas;

    /**
     * Creates an {@code AnalyticsTabPane}.
     */
    public AnalyticsTabPane() {
        super(FXML);
        // Need to figure out how to get the resized width and height upon initialization by FXML.
        baseCanvas.setWidth(500);
        baseCanvas.setHeight(500);
        Viz viz = BarChartDemoViz.createBarChartViz();
        JFxVizRenderer jFxVizRenderer = new JFxVizRenderer(baseCanvas, viz);
        jFxVizRenderer.render();
    }
}
