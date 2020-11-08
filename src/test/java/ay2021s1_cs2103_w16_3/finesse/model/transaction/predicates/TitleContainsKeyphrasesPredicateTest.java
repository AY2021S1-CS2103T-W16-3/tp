package ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;

public class TitleContainsKeyphrasesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondPredicateKeyphraseList = Arrays.asList("first", "second");

        TitleContainsKeyphrasesPredicate firstPredicate =
                new TitleContainsKeyphrasesPredicate(firstPredicateKeyphraseList);
        TitleContainsKeyphrasesPredicate secondPredicate =
                new TitleContainsKeyphrasesPredicate(secondPredicateKeyphraseList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TitleContainsKeyphrasesPredicate firstPredicateCopy =
                new TitleContainsKeyphrasesPredicate(firstPredicateKeyphraseList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different transaction -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_titleContainsKeyphrases_returnsTrue() {
        // One keyphrase
        TitleContainsKeyphrasesPredicate predicate =
                new TitleContainsKeyphrasesPredicate(Collections.singletonList("Damith Seth"));
        assertTrue(predicate.test(new TransactionBuilder().withTitle("Damith Seth").buildExpense()));

        // Partial keyphrase
        predicate = new TitleContainsKeyphrasesPredicate(Arrays.asList("Dam"));
        assertTrue(predicate.test(new TransactionBuilder().withTitle("Damith Seth").buildExpense()));

        // Multiple keyphrases
        predicate = new TitleContainsKeyphrasesPredicate(Arrays.asList("Damith", "Seth"));
        assertTrue(predicate.test(new TransactionBuilder().withTitle("Damith Seth").buildExpense()));

        // Only one matching keyphrase
        predicate = new TitleContainsKeyphrasesPredicate(Arrays.asList("Seth", "Aaron"));
        assertTrue(predicate.test(new TransactionBuilder().withTitle("Damith Aaron").buildExpense()));

        // Mixed-case keyphrases
        predicate = new TitleContainsKeyphrasesPredicate(Arrays.asList("dAMiTh", "SEtH"));
        assertTrue(predicate.test(new TransactionBuilder().withTitle("Damith Seth").buildExpense()));
    }

    @Test
    public void test_titleDoesNotContainKeyphrases_returnsFalse() {
        // Zero keyphrases
        TitleContainsKeyphrasesPredicate predicate = new TitleContainsKeyphrasesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withTitle("Damith").buildExpense()));

        // Non-matching keyphrase
        predicate = new TitleContainsKeyphrasesPredicate(Arrays.asList("Aaron"));
        assertFalse(predicate.test(new TransactionBuilder().withTitle("Damith Seth").buildExpense()));

    }
}
