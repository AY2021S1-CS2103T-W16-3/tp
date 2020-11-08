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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs.FindCommandStub;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.TitleContainsKeyphrasesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindExpenseCommand}.
 */
public class FindExpenseCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void equals() {
        TitleContainsKeyphrasesPredicate firstPredicate =
                new TitleContainsKeyphrasesPredicate(Collections.singletonList("first"));
        TitleContainsKeyphrasesPredicate secondPredicate =
                new TitleContainsKeyphrasesPredicate(Collections.singletonList("second"));
        List<Predicate<Transaction>> firstPredicateList = new ArrayList<>();
        firstPredicateList.add(firstPredicate);
        List<Predicate<Transaction>> secondPredicateList = new ArrayList<>();
        secondPredicateList.add(secondPredicate);

        FindCommand firstSuperCommand = new FindCommand(firstPredicateList);
        FindExpenseCommand firstFindExpenseCommand = new FindExpenseCommand(firstSuperCommand);
        FindCommand secondSuperCommand = new FindCommand(secondPredicateList);
        FindExpenseCommand secondFindExpenseCommand = new FindExpenseCommand(secondSuperCommand);

        // same object -> returns true
        assertTrue(firstFindExpenseCommand.equals(firstFindExpenseCommand));

        List<Predicate<Transaction>> firstPredicateListCopy = new ArrayList<>();
        firstPredicateListCopy.add(firstPredicate);
        // same values -> returns true
        FindExpenseCommand firstFindExpenseCommandCopy = new FindExpenseCommand(firstSuperCommand);
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
        TitleContainsKeyphrasesPredicate predicate = preparePredicate(" ");
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(predicate);
        FindCommandStub superCommand = new FindCommandStub(predicateList);
        FindExpenseCommand findExpenseCommand = new FindExpenseCommand(superCommand);
        expectedModel.updateFilteredExpenseList(predicate);
        assertCommandSuccess(findExpenseCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredExpenseList());
    }

    @Test
    public void execute_multipleKeywords_multipleExpensesFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 3);
        TitleContainsKeyphrasesPredicate predicate = preparePredicate("carl's artificial pen");
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(predicate);
        FindCommandStub superCommand = new FindCommandStub(predicateList);
        FindExpenseCommand findExpenseCommand = new FindExpenseCommand(superCommand);
        expectedModel.updateFilteredExpenseList(predicate);
        assertCommandSuccess(findExpenseCommand, model, expectedMessage, expectedModel);
        // Ordered by date, then by title.
        assertEquals(Arrays.asList(AIMA, CARLS_JR, PEN_REFILLS), model.getFilteredExpenseList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeyphrasesPredicate}.
     */
    private TitleContainsKeyphrasesPredicate preparePredicate(String userInput) {
        return new TitleContainsKeyphrasesPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
