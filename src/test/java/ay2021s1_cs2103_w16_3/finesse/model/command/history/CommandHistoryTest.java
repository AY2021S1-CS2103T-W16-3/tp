package ay2021s1_cs2103_w16_3.finesse.model.command.history;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class CommandHistoryTest {
    @Test
    public void constructor_invalidMaxCommands_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CommandHistory(0));
        assertThrows(IllegalArgumentException.class, () -> new CommandHistory(-10));
    }

    @Test
    public void addAndNavigateCommands() {
        CommandHistory commandHistory = new CommandHistory(5);
        IntStream.range(1, 9).mapToObj(i -> "Command " + i).forEach(commandHistory::addCommand);

        // Check that the order of command history is maintained backwards.
        assertEquals("Command 8", commandHistory.navigateUp());
        assertEquals("Command 7", commandHistory.navigateUp());
        assertEquals("Command 6", commandHistory.navigateUp());
        assertEquals("Command 5", commandHistory.navigateUp());
        assertEquals("Command 4", commandHistory.navigateUp());
        // Check that the earliest commands are evicted.
        assertEquals("Command 4", commandHistory.navigateUp());
        // Check that the order of command history is maintained forwards.
        assertEquals("Command 5", commandHistory.navigateDown());
        assertEquals("Command 6", commandHistory.navigateDown());
        assertEquals("Command 7", commandHistory.navigateDown());
        assertEquals("Command 8", commandHistory.navigateDown());
        // Check that navigating past the most recent command returns null.
        assertNull(commandHistory.navigateDown());
    }

    @Test
    public void navigate_emptyCommandHistory_returnsEmptyString() {
        CommandHistory commandHistory = new CommandHistory(5);

        // Check that the empty string is returned when the command history is empty.
        assertEquals("", commandHistory.navigateUp());
        assertEquals("", commandHistory.navigateDown());
    }
}
