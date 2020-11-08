package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.AIMA;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.CARLS_JR;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.GST_VOUCHER;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PEN_REFILLS;
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
import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTransactionCommand}.
 */
public class FindTransactionCommandTest {
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
        FindTransactionCommand firstFindTransactionCommand = new FindTransactionCommand(firstSuperCommand);
        FindCommand secondSuperCommand = new FindCommand(secondPredicateList);
        FindTransactionCommand secondFindTransactionCommand = new FindTransactionCommand(secondSuperCommand);

        // same object -> returns true
        assertTrue(firstFindTransactionCommand.equals(firstFindTransactionCommand));

        List<Predicate<Transaction>> firstPredicateListCopy = new ArrayList<>();
        firstPredicateListCopy.add(firstPredicate);
        // same values -> returns true
        FindTransactionCommand firstFindTransactionCommandCopy = new FindTransactionCommand(firstSuperCommand);
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
        TitleContainsKeyphrasesPredicate predicate = preparePredicate(" ");
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(predicate);
        FindCommandStub superCommand = new FindCommandStub(predicateList);
        FindTransactionCommand findTransactionCommand = new FindTransactionCommand(superCommand);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(findTransactionCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_multipleKeywords_multipleTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 6);
        TitleContainsKeyphrasesPredicate predicate = preparePredicate("gst carl's artificial internship pen teaching");
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(predicate);
        FindCommandStub superCommand = new FindCommandStub(predicateList);
        FindTransactionCommand findTransactionCommand = new FindTransactionCommand(superCommand);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(findTransactionCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                new TransactionBuilder(AIMA).buildExpense(),
                new TransactionBuilder(CARLS_JR).buildExpense(),
                new TransactionBuilder(GST_VOUCHER).buildIncome(),
                new TransactionBuilder(INTERNSHIP).buildIncome(),
                new TransactionBuilder(PEN_REFILLS).buildExpense(),
                new TransactionBuilder(TEACHING_ASSISTANT).buildIncome()
        ), model.getFilteredTransactionList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeyphrasesPredicate}.
     */
    private TitleContainsKeyphrasesPredicate preparePredicate(String userInput) {
        return new TitleContainsKeyphrasesPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
