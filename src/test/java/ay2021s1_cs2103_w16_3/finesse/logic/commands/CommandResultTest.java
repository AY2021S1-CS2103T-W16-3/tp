package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, null)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different calculateBudgetInfo value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false)));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, true)));

        // different tabToSwitchTo value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", Tab.ANALYTICS)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, true).hashCode());

        // different tabToSwitchTo value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, false, Tab.INCOME));
    }

    @Test
    public void getters_returnSameValue() {
        String feedback = "Hello world!";
        boolean calculateBudgetInfo = true;
        boolean showHelp = true;
        boolean exit = true;
        Tab tabToSwitchTo = Tab.ANALYTICS;
        CommandResult commandResult = new CommandResult(feedback, calculateBudgetInfo, showHelp, exit, tabToSwitchTo);

        assertEquals(feedback, commandResult.getFeedbackToUser());
        assertEquals(calculateBudgetInfo, commandResult.isCalculateBudgetInfo());
        assertEquals(showHelp, commandResult.isShowHelp());
        assertEquals(exit, commandResult.isExit());
        assertEquals(Optional.of(tabToSwitchTo), commandResult.getTabToSwitchTo());
    }
}
