package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
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
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTransactionCommand}.
 */
public class FindTransactionCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void equals() {
        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("first"));
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommandStub firstSuperCommand = new FindCommandStub(firstPredicate);
        FindTransactionCommand firstFindTransactionCommand = new FindTransactionCommand(firstSuperCommand);
        FindCommandStub secondSuperCommand = new FindCommandStub(secondPredicate);
        FindTransactionCommand secondFindTransactionCommand = new FindTransactionCommand(secondSuperCommand);

        // same object -> returns true
        assertTrue(firstFindTransactionCommand.equals(firstFindTransactionCommand));

        // same values -> returns true
        FindCommandStub firstSuperCommandCopy = new FindCommandStub(firstPredicate);
        FindTransactionCommand firstFindTransactionCommandCopy = new FindTransactionCommand(firstSuperCommandCopy);
        assertTrue(firstFindTransactionCommand.equals(firstFindTransactionCommandCopy));

        // different types -> returns false
        assertFalse(firstFindTransactionCommand.equals(1));

        // null -> returns false
        assertFalse(firstFindTransactionCommand.equals(null));

        // different predicates -> returns false
        assertFalse(firstFindTransactionCommand.equals(secondFindTransactionCommand));
    }

    @Test
    public void execute_zeroKeywords_noTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 0);
        TitleContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommandStub superCommand = new FindCommandStub(predicate);
        FindTransactionCommand findTransactionCommand = new FindTransactionCommand(superCommand);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(findTransactionCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_multipleKeywords_multipleTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 6);
        TitleContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommandStub superCommand = new FindCommandStub(predicate);
        FindTransactionCommand findTransactionCommand = new FindTransactionCommand(superCommand);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(findTransactionCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                new TransactionBuilder(CARL).buildExpense(),
                new TransactionBuilder(CARL).buildIncome(),
                new TransactionBuilder(ELLE).buildExpense(),
                new TransactionBuilder(ELLE).buildIncome(),
                new TransactionBuilder(FIONA).buildExpense(),
                new TransactionBuilder(FIONA).buildIncome()
        ), model.getFilteredTransactionList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private TitleContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
