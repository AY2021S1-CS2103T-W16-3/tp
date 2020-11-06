package ay2021s1_cs2103_w16_3.finesse.logic.time.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TemporalExceptionTest {
    private static final String TEMPORAL_EXCEPTION_MESSAGE = "This is a TemporalException.";

    @Test
    public void constructor_throwTemporalException_temporalExceptionThrown() {
        assertThrows(TemporalException.class, () -> {
            throw new TemporalException(TEMPORAL_EXCEPTION_MESSAGE);
        });
        assertThrows(TemporalException.class, () -> {
            throw new TemporalException(TEMPORAL_EXCEPTION_MESSAGE, new Throwable());
        });
    }
}
