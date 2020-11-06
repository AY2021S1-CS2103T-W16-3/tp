package ay2021s1_cs2103_w16_3.finesse.logic.time.exceptions;

/**
 * Represents a disruption in the flow of time.
 */
public class TemporalException extends Exception {
    /**
     * Constructs a new {@code TemporalException} with the specified message.
     * @param message The message to use for this exception, may be {@code null}.
     */
    public TemporalException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code TemporalException} with the specified message and cause.
     *
     * @param message The message to use for this exception, may be {@code null}.
     * @param cause The cause of the exception, may be {@code null}.
     */
    public TemporalException(String message, Throwable cause) {
        super(message, cause);
    }
}
