package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INCOMES_LISTED_OVERVIEW;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.GST_VOUCHER;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.TEACHING_ASSISTANT;
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
 * Contains integration tests (interaction with the Model) for {@code FindIncomeCommand}.
 */
public class FindIncomeCommandTest {
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
        FindIncomeCommand firstFindIncomeCommand = new FindIncomeCommand(firstSuperCommand);
        FindCommand secondSuperCommand = new FindCommand(secondPredicateList);
        FindIncomeCommand secondFindIncomeCommand = new FindIncomeCommand(secondSuperCommand);

        // same object -> returns true
        assertTrue(firstFindIncomeCommand.equals(firstFindIncomeCommand));

        List<Predicate<Transaction>> firstPredicateListCopy = new ArrayList<>();
        firstPredicateListCopy.add(firstPredicate);
        // same values -> returns true
        FindIncomeCommand firstFindIncomeCommandCopy = new FindIncomeCommand(firstSuperCommand);
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
        TitleContainsKeyphrasesPredicate predicate = preparePredicate(" ");
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(predicate);
        FindCommandStub superCommand = new FindCommandStub(predicateList);
        FindIncomeCommand findIncomeCommand = new FindIncomeCommand(superCommand);
        expectedModel.updateFilteredIncomeList(predicate);
        assertCommandSuccess(findIncomeCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredIncomeList());
    }

    @Test
    public void execute_multipleKeywords_multipleIncomesFound() {
        String expectedMessage = String.format(MESSAGE_INCOMES_LISTED_OVERVIEW, 3);
        TitleContainsKeyphrasesPredicate predicate = preparePredicate("gst internship teaching");
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(predicate);
        FindCommandStub superCommand = new FindCommandStub(predicateList);
        FindIncomeCommand findIncomeCommand = new FindIncomeCommand(superCommand);
        expectedModel.updateFilteredIncomeList(predicate);
        assertCommandSuccess(findIncomeCommand, model, expectedMessage, expectedModel);
        // Ordered by date, then by title.
        assertEquals(Arrays.asList(GST_VOUCHER, INTERNSHIP, TEACHING_ASSISTANT), model.getFilteredIncomeList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeyphrasesPredicate}.
     */
    private TitleContainsKeyphrasesPredicate preparePredicate(String userInput) {
        return new TitleContainsKeyphrasesPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
