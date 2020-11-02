package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.TitleContainsKeyphrasesPredicate;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_throwsCommandException() {
        assertCommandFailure(new FindCommand(List.of(preparePredicate(""))), model,
                MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

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

        FindCommand findFirstCommand = new FindCommand(firstPredicateList);
        FindCommand findSecondCommand = new FindCommand(secondPredicateList);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        List<Predicate<Transaction>> firstPredicateListCopy = new ArrayList<>();
        firstPredicateListCopy.add(firstPredicate);
        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicateListCopy);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicates -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
    
    /**
     * Parses {@code userInput} into a {@code TitleContainsKeyphrasesPredicate}.
     */
    private TitleContainsKeyphrasesPredicate preparePredicate(String userInput) {
        return new TitleContainsKeyphrasesPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
