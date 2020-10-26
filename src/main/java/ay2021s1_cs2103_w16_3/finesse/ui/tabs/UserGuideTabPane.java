package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * Tab pane that displays the user guide.
 */
public class UserGuideTabPane extends UiPart<StackPane> {
    private static final String FXML = "UserGuideTabPane.fxml";
    private static final String USER_GUIDE_URL = "https://ay2021s1-cs2103t-w16-3.github.io/tp/UserGuide.html";

    @FXML
    private WebView webView;

    /**
     * Constructs a {@code UserGuideTabPane}.
     */
    public UserGuideTabPane() {
        super(FXML);
        initialiseUserGuide();
    }

    private void initialiseUserGuide() {
        webView.getEngine().load(USER_GUIDE_URL);
    }
}
