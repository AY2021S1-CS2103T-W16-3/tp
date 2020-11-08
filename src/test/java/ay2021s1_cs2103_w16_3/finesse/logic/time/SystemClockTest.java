package ay2021s1_cs2103_w16_3.finesse.logic.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SystemClockTest {
    /** Time difference acceptance threshold (in milliseconds) for slower systems. */
    private static final int TIME_DIFFERENCE_THRESHOLD = 100;

    private final SystemClock systemClock = new SystemClock();

    @Test
    public void getCurrentTime_sameAsCurrentSystemTime() {
        assertTrue(Math.abs(System.currentTimeMillis() - systemClock.getCurrentTime()) <= TIME_DIFFERENCE_THRESHOLD);
    }
}
