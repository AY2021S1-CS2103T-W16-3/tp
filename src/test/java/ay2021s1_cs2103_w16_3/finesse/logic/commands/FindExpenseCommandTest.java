package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.AIMA;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.CARLS_JR;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PEN_REFILLS;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs.FindCommandStub;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TitleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindExpenseCommand}.
 */
public class FindExpenseCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void equals() {
        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("first"));
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommandStub firstSuperCommand = new FindCommandStub(firstPredicate);
        FindExpenseCommand firstFindExpenseCommand = new FindExpenseCommand(firstSuperCommand);
        FindCommandStub secondSuperCommand = new FindCommandStub(secondPredicate);
        FindExpenseCommand secondFindExpenseCommand = new FindExpenseCommand(secondSuperCommand);

        // same object -> returns true
        assertTrue(firstFindExpenseCommand.equals(firstFindExpenseCommand));

        // same values -> returns true
        FindCommandStub firstSuperCommandCopy = new FindCommandStub(firstPredicate);
        FindExpenseCommand firstFindExpenseCommandCopy = new FindExpenseCommand(firstSuperCommandCopy);
        assertTrue(firstFindExpenseCommand.equals(firstFindExpenseCommandCopy));

        // different types -> returns false
        assertFalse(firstFindExpenseCommand.equals(1));

        // null -> returns false
        assertFalse(firstFindExpenseCommand.equals(null));

        // different predicates -> returns false
        assertFalse(firstFindExpenseCommand.equals(secondFindExpenseCommand));
    }

    @Test
    public void execute_zeroKeywords_noExpensesFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 0);
        TitleContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommandStub superCommand = new FindCommandStub(predicate);
        FindExpenseCommand findExpenseCommand = new FindExpenseCommand(superCommand);
        expectedModel.updateFilteredExpenseList(predicate);
        assertCommandSuccess(findExpenseCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredExpenseList());
    }

    @Test
    public void execute_multipleKeywords_multipleExpensesFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 3);
        TitleContainsKeywordsPredicate predicate = preparePredicate("carl's artificial pen");
        FindCommandStub superCommand = new FindCommandStub(predicate);
        FindExpenseCommand findExpenseCommand = new FindExpenseCommand(superCommand);
        expectedModel.updateFilteredExpenseList(predicate);
        assertCommandSuccess(findExpenseCommand, model, expectedMessage, expectedModel);
        // Ordered by date, then by title.
        assertEquals(Arrays.asList(AIMA, CARLS_JR, PEN_REFILLS), model.getFilteredExpenseList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private TitleContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
