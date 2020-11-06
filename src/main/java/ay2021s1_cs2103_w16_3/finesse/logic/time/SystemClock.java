package ay2021s1_cs2103_w16_3.finesse.logic.time;

/**
 * A clock that relies on the system time.
 */
public class SystemClock implements Clock {
    /**
     * Returns the current system time in milliseconds.
     *
     * @return The difference, measured in milliseconds, between the current system
     *         time and midnight, January 1, 1970 UTC.
     */
    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
