package ay2021s1_cs2103_w16_3.finesse.logic.commands.budget;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

public class SetSavingsGoalCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void constructor_nullAmount_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetSavingsGoalCommand(null));
    }

    @Test
    public void execute_validAmount_success() {
        Amount amountToSet = new Amount("500");
        SetSavingsGoalCommand setSavingsGoalCommand = new SetSavingsGoalCommand(amountToSet);

        String expectedMessage = String.format(SetSavingsGoalCommand.MESSAGE_SUCCESS, amountToSet);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.setSavingsGoal(amountToSet);

        assertCommandSuccess(setSavingsGoalCommand, model, expectedMessage, expectedModel, true, Tab.OVERVIEW);
    }

    @Test
    public void equals() {
        SetSavingsGoalCommand setSavingsGoalCommand = new SetSavingsGoalCommand(new Amount("5"));

        // Same values -> returns true
        assertTrue(setSavingsGoalCommand.equals(new SetSavingsGoalCommand(new Amount("5"))));

        // Different amounts -> returns false
        assertFalse(setSavingsGoalCommand.equals(new SetSavingsGoalCommand(new Amount("10"))));

        // Same object -> returns true
        assertTrue(setSavingsGoalCommand.equals(setSavingsGoalCommand));

        // null -> returns false
        assertFalse(setSavingsGoalCommand.equals(null));

        // Different types -> returns false
        assertFalse(setSavingsGoalCommand.equals(5));
    }
}
