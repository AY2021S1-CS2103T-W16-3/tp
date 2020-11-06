package ay2021s1_cs2103_w16_3.finesse.logic.time;

import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.logic.time.exceptions.TemporalException;

/**
 * Responsible for detecting temporal disruptions.
 */
public class Timekeeper {
    // Strings
    private static final String TIME_INCONSISTENT =
            "The current time '%s' is earlier than the last observed time '%s'!";
    private static final String TIME_INITIALIZED = "Current time initialized to '%s'";
    private static final String TIME_UPDATED = "Current time updated to '%s'";
    private static final String TIME_IRREGULARITIES_DETECTED = "Irregularities in the flow of time detected.";

    /** Logger for logging time updates. */
    private static final Logger logger = LogsCenter.getLogger(Timekeeper.class);
    /** A clock from which to poll the current time. */
    private final Clock clock;
    /** The last observed time. */
    private long lastObservedTime;

    /**
     * Constructs a new {@code Timekeeper} with the default system time clock
     * and the current system time.
     */
    public Timekeeper() {
        this(new SystemClock());
    }

    /**
     * Constructs a new {@code Timekeeper} with the specified clock and the
     * current time provided by the clock.
     *
     * @param clock The {@code Clock} to poll for the current time.
     */
    public Timekeeper(Clock clock) {
        this.clock = clock;
        lastObservedTime = clock.getCurrentTime();
        logger.info(String.format(TIME_INITIALIZED, lastObservedTime));
    }

    /**
     * Checks in with the {@code Timekeeper}. If the current time is earlier than
     * the last observed time, this method throws a {@code TemporalException}.
     * Otherwise, it updates the last observed time of the {@code Timekeeper}.
     *
     * @throws TemporalException If the current time is earlier than the last
     *                           observed time.
     */
    public void checkIn() throws TemporalException {
        long currentTime = clock.getCurrentTime();
        if (currentTime < lastObservedTime) {
            logger.severe(String.format(TIME_INCONSISTENT, currentTime, lastObservedTime));
            throw new TemporalException(TIME_IRREGULARITIES_DETECTED);
        }
        lastObservedTime = currentTime;
        logger.info(String.format(TIME_UPDATED, lastObservedTime));
    }
}
