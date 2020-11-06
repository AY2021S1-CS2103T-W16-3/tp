package ay2021s1_cs2103_w16_3.finesse.logic.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SystemClockTest {
    private final SystemClock systemClock = new SystemClock();

    @Test
    public void getCurrentTime_sameAsCurrentSystemTime() {
        assertEquals(System.currentTimeMillis(), systemClock.getCurrentTime());
    }
}
