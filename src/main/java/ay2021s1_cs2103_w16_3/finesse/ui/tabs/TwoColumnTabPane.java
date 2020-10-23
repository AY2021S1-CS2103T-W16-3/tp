package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * An abstract tab pane with two panels arranged in columns.
 */
public abstract class TwoColumnTabPane extends UiPart<Region> {
    private static final String FXML = "TwoColumnTabPane.fxml";

    @FXML
    private Label mainPanelLabel;
    @FXML
    private Label sidePanelLabel;
    @FXML
    private StackPane mainPanelPlaceholder;
    @FXML
    private StackPane sidePanelPlaceholder;

    /**
     * Creates a {@code TwoColumnTabPane} with the specified attributes.
     *
     * @param mainPanelLabelStr The label of the main panel.
     * @param sidePanelLabelStr The label of the side panel.
     * @param mainPanel The main panel to be displayed.
     * @param sidePanel The side panel to be displayed.
     */
    public TwoColumnTabPane(String mainPanelLabelStr, String sidePanelLabelStr, UiPart<Region> mainPanel,
                            UiPart<Region> sidePanel) {
        super(FXML);
        mainPanelLabel.setText(mainPanelLabelStr);
        sidePanelLabel.setText(sidePanelLabelStr);
        mainPanelPlaceholder.getChildren().add(mainPanel.getRoot());
        sidePanelPlaceholder.getChildren().add(sidePanel.getRoot());
    }
}
