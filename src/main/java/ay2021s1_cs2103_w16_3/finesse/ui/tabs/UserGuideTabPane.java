package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Tab pane that displays the user guide.
 */
public class UserGuideTabPane extends UiPart<StackPane> {
    private static final String FXML = "UserGuideTabPane.fxml";

    private static final String GITHUB_PAGES_DOMAIN = "https://ay2021s1-cs2103t-w16-3.github.io";
    private static final String USER_GUIDE_URL = "https://ay2021s1-cs2103t-w16-3.github.io/tp/UserGuide.html";
    private static final String ERROR_PAGE_URL = "https://ay2021s1-cs2103t-w16-3.github.io";

    @FXML
    private WebView webView;

    /**
     * Constructs a {@code UserGuideTabPane}.
     */
    public UserGuideTabPane() {
        super(FXML);
        initializeUserGuide();
    }

    /**
     * Initializes the {@code WebEngine} that is backing the {@code WebView}.
     */
    private void initializeUserGuide() {
        WebEngine webEngine = webView.getEngine();

        webEngine.locationProperty().addListener((observableValue, oldUrl, newUrl) -> {
            if (!newUrl.contains(GITHUB_PAGES_DOMAIN)) {
                // Block requests to external sites.
                Platform.runLater(() -> {
                    // Load the error page.
                    webEngine.load(ERROR_PAGE_URL);
                });
            }
        });

        webEngine.load(USER_GUIDE_URL);
    }
}
