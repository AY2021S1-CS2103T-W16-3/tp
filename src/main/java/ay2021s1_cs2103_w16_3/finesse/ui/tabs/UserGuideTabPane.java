package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
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

    // Links
    private static final String GITHUB_PAGES_DOMAIN = "https://ay2021s1-cs2103t-w16-3.github.io";
    private static final String USER_GUIDE_URL = "https://ay2021s1-cs2103t-w16-3.github.io/tp/UserGuide.html";
    // Note: Does not work if '.html' is appended for some reason.
    private static final String NO_EXTERNAL_SITE_PAGE_URL =
            "https://ay2021s1-cs2103t-w16-3.github.io/tp/NoExternalSite";

    // Logging messages
    private static final String WEB_ENGINE_EXTERNAL_SITE_REQUEST_BLOCKED =
            "Request to load page on external site blocked";
    private static final String WEB_ENGINE_WORKER_STATE_CANCELLED = "Page load cancelled: ";
    private static final String WEB_ENGINE_WORKER_STATE_FAILED = "Unable to load page (no internet connection): ";
    private static final String WEB_ENGINE_WORKER_STATE_RUNNING = "Loading page: ";
    private static final String WEB_ENGINE_WORKER_STATE_SCHEDULED = "Page load scheduled: ";
    private static final String WEB_ENGINE_WORKER_STATE_SUCCEEDED = "Page successfully loaded: ";

    private final Logger logger = LogsCenter.getLogger(getClass());

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
        // Disable right click.
        webView.setContextMenuEnabled(false);

        WebEngine webEngine = webView.getEngine();

        webEngine.locationProperty().addListener((observableValue, oldUrl, newUrl) -> {
            if (!newUrl.startsWith(GITHUB_PAGES_DOMAIN)) {
                logger.info(WEB_ENGINE_EXTERNAL_SITE_REQUEST_BLOCKED);

                // Block requests to external sites.
                Platform.runLater(() -> {
                    // Load the 'No External Site' page.
                    webEngine.load(NO_EXTERNAL_SITE_PAGE_URL);
                });
            }
        });

        webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
            String location = webEngine.getLocation();
            switch (newState) {
            case CANCELLED:
                logger.info(WEB_ENGINE_WORKER_STATE_CANCELLED + location);
                break;
            case FAILED:
                logger.warning(WEB_ENGINE_WORKER_STATE_FAILED + location);
                break;
            case RUNNING:
                logger.info(WEB_ENGINE_WORKER_STATE_RUNNING + location);
                break;
            case SCHEDULED:
                logger.info(WEB_ENGINE_WORKER_STATE_SCHEDULED + location);
                break;
            case SUCCEEDED:
                logger.info(WEB_ENGINE_WORKER_STATE_SUCCEEDED + location);
                break;
            default:
            }
        });

        webEngine.load(USER_GUIDE_URL);
    }

    /**
     * Refreshes the WebView.
     */
    public void refreshPage() {
        // Cannot simply reload the page because if the page has not loaded a single time,
        // reloading does not work.
        String currentPage = webView.getEngine().getLocation();
        webView.getEngine().load(currentPage);
    }
}
