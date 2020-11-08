package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.TabCommand.MESSAGE_SWITCH_TABS_SUCCESS;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

public class TabCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
    }

    @Test
    public void execute_switchToOverviewTab_success() {
        String formattedSuccessMessage = String.format(MESSAGE_SWITCH_TABS_SUCCESS, Tab.OVERVIEW);
        CommandResult expectedCommandResult = new CommandResult(formattedSuccessMessage, Tab.OVERVIEW);
        assertCommandSuccess(new TabCommand(Index.fromOneBased(1)), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_switchToIncomeTab_success() {
        String formattedSuccessMessage = String.format(MESSAGE_SWITCH_TABS_SUCCESS, Tab.INCOME);
        CommandResult expectedCommandResult = new CommandResult(formattedSuccessMessage, Tab.INCOME);
        assertCommandSuccess(new TabCommand(Index.fromOneBased(2)), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_switchToExpensesTab_success() {
        String formattedSuccessMessage = String.format(MESSAGE_SWITCH_TABS_SUCCESS, Tab.EXPENSES);
        CommandResult expectedCommandResult = new CommandResult(formattedSuccessMessage, Tab.EXPENSES);
        assertCommandSuccess(new TabCommand(Index.fromOneBased(3)), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_switchToAnalyticsTab_success() {
        String formattedSuccessMessage = String.format(MESSAGE_SWITCH_TABS_SUCCESS, Tab.ANALYTICS);
        CommandResult expectedCommandResult = new CommandResult(formattedSuccessMessage, Tab.ANALYTICS);
        assertCommandSuccess(new TabCommand(Index.fromOneBased(4)), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        assertCommandFailure(new TabCommand(Index.fromOneBased(5)), model, TabCommand.MESSAGE_TAB_DOES_NOT_EXIST);
    }

    @Test
    public void equals() {
        TabCommand tabCommand = new TabCommand(Index.fromOneBased(1));

        // Same object -> returns true
        assertTrue(tabCommand.equals(tabCommand));

        // Same index -> returns true
        assertTrue(tabCommand.equals(new TabCommand(Index.fromOneBased(1))));

        // Different index -> returns false
        assertFalse(tabCommand.equals(new TabCommand(Index.fromOneBased(3))));

        // Different types -> returns false
        assertFalse(tabCommand.equals(5));

        // null -> returns false
        assertFalse(tabCommand.equals(null));
    }
}
