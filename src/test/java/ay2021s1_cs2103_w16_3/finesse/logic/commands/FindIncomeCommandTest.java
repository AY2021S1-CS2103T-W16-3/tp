package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INCOMES_LISTED_OVERVIEW;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.CARL;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.ELLE;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.FIONA;
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
 * Contains integration tests (interaction with the Model) for {@code FindIncomeCommand}.
 */
public class FindIncomeCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void equals() {
        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("first"));
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommandStub firstSuperCommand = new FindCommandStub(firstPredicate);
        FindIncomeCommand firstFindIncomeCommand = new FindIncomeCommand(firstSuperCommand);
        FindCommandStub secondSuperCommand = new FindCommandStub(secondPredicate);
        FindIncomeCommand secondFindIncomeCommand = new FindIncomeCommand(secondSuperCommand);

        // same object -> returns true
        assertTrue(firstFindIncomeCommand.equals(firstFindIncomeCommand));

        // same values -> returns true
        FindCommandStub firstSuperCommandCopy = new FindCommandStub(firstPredicate);
        FindIncomeCommand firstFindIncomeCommandCopy = new FindIncomeCommand(firstSuperCommandCopy);
        assertTrue(firstFindIncomeCommand.equals(firstFindIncomeCommandCopy));

        // different types -> returns false
        assertFalse(firstFindIncomeCommand.equals(1));

        // null -> returns false
        assertFalse(firstFindIncomeCommand.equals(null));

        // different predicates -> returns false
        assertFalse(firstFindIncomeCommand.equals(secondFindIncomeCommand));
    }

    @Test
    public void execute_zeroKeywords_noIncomesFound() {
        String expectedMessage = String.format(MESSAGE_INCOMES_LISTED_OVERVIEW, 0);
        TitleContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommandStub superCommand = new FindCommandStub(predicate);
        FindIncomeCommand findIncomeCommand = new FindIncomeCommand(superCommand);
        expectedModel.updateFilteredIncomeList(predicate);
        assertCommandSuccess(findIncomeCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredIncomeList());
    }

    @Test
    public void execute_multipleKeywords_multipleIncomesFound() {
        String expectedMessage = String.format(MESSAGE_INCOMES_LISTED_OVERVIEW, 3);
        TitleContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommandStub superCommand = new FindCommandStub(predicate);
        FindIncomeCommand findIncomeCommand = new FindIncomeCommand(superCommand);
        expectedModel.updateFilteredIncomeList(predicate);
        assertCommandSuccess(findIncomeCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredIncomeList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private TitleContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
