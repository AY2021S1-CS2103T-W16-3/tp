package ay2021s1_cs2103_w16_3.finesse.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DisabledWindow extends UiPart<Parent> {
    private static final String FXML = "DisabledWindow.fxml";
    private static final String CREDITS_LABEL = "Image courtesy of InnerSloth and UnitedWorldMedia";
    private static final String DETAILS_LABEL = "A regression in the system time was detected. "
            + "In order to safeguard against loss of data, Fine$$e has been disabled. "
            + "Please ensure that your system time is correct, then restart Fine$$e.";
    private final Image image = new Image(this.getClass()
            .getResourceAsStream("/images/sus.png"));

    @FXML
    private Label creditsLabel;
    @FXML
    private Label detailsLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private Pane imageContainer;

    /**
     * Creates a {@code DisabledWindow}.
     */
    public DisabledWindow() {
        super(FXML);

        // Initialize label messages.
        creditsLabel.setText(CREDITS_LABEL);
        detailsLabel.setText(DETAILS_LABEL);

        // Set up image.
        imageView.setImage(image);
        imageView.layoutXProperty().bind(imageContainer.widthProperty()
                .subtract(Bindings.min(imageView.fitHeightProperty(), imageView.fitWidthProperty()))
                .divide(3));
    }
}
