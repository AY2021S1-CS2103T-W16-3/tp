package ay2021s1_cs2103_w16_3.finesse.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 815.0;
    private static final double DEFAULT_WIDTH = 1240.0;
    private static final boolean DEFAULT_FULLSCREEN = false;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;
    private final boolean isFullscreen;

    /**
     * Constructs a {@code GuiSettings} with the default height, width and position.
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        isFullscreen = DEFAULT_FULLSCREEN;
    }

    /**
     * Constructs a {@code GuiSettings} with the specified height, width, position, and whether it is fullscreen.
     */
    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition, boolean isFullscreen) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.isFullscreen = isFullscreen;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return windowWidth == o.windowWidth
                && windowHeight == o.windowHeight
                && Objects.equals(windowCoordinates, o.windowCoordinates)
                && isFullscreen == o.isFullscreen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, isFullscreen);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width: " + windowWidth + "\n");
        sb.append("Height: " + windowHeight + "\n");
        sb.append("Position: " + windowCoordinates + "\n");
        sb.append("Is Fullscreen: " + (isFullscreen ? "Yes" : "No"));
        return sb.toString();
    }
}
