package ay2021s1_cs2103_w16_3.finesse.logic.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.time.exceptions.TemporalException;

public class TimekeeperTest {
    @Test
    public void checkIn_laterTime_doesNotThrowTemporalException() throws TemporalException {
        long currentSystemTime = System.currentTimeMillis();
        ClockStub adjustableClock = new ClockStub(currentSystemTime);
        Timekeeper timekeeper = new Timekeeper(adjustableClock);

        long firstTime = adjustableClock.getCurrentTime();
        timekeeper.checkIn();
        adjustableClock.setCurrentTime(currentSystemTime + 1);
        long secondTime = adjustableClock.getCurrentTime();
        timekeeper.checkIn();

        assertTrue(firstTime < secondTime);
    }

    @Test
    public void checkIn_sameTime_doesNotThrowTemporalException() throws TemporalException {
        long currentSystemTime = System.currentTimeMillis();
        ClockStub adjustableClock = new ClockStub(currentSystemTime);
        Timekeeper timekeeper = new Timekeeper(adjustableClock);

        long firstTime = adjustableClock.getCurrentTime();
        timekeeper.checkIn();
        long secondTime = adjustableClock.getCurrentTime();
        timekeeper.checkIn();

        assertEquals(firstTime, secondTime);
    }

    @Test
    public void checkIn_earlierTime_throwsTemporalException() throws TemporalException {
        long currentSystemTime = System.currentTimeMillis();
        ClockStub adjustableClock = new ClockStub(currentSystemTime);
        Timekeeper timekeeper = new Timekeeper(adjustableClock);

        long firstTime = adjustableClock.getCurrentTime();
        timekeeper.checkIn();
        adjustableClock.setCurrentTime(currentSystemTime - 1);
        long secondTime = adjustableClock.getCurrentTime();
        assertThrows(TemporalException.class, () -> timekeeper.checkIn());

        assertTrue(firstTime > secondTime);
    }

    /**
     * A class representing a clock with the ability to manipulate time for testing purposes.
     */
    private static class ClockStub implements Clock {
        /** The current time. */
        private long currentTime;

        /**
         * Constructs a new {@code ClockStub} with the specified current time.
         *
         * @param currentTime The specified current time.
         */
        public ClockStub(long currentTime) {
            this.currentTime = currentTime;
        }

        /**
         * Sets the current time of the clock.
         *
         * @param currentTime The specified current time.
         */
        public void setCurrentTime(long currentTime) {
            this.currentTime = currentTime;
        }

        /**
         * Returns the current time.
         *
         * @return The current time.
         */
        @Override
        public long getCurrentTime() {
            return currentTime;
        }
    }
}
