package ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;

public class HasExactAmountPredicateTest {

    @Test
    public void equals() {
        Amount firstPredicateAmount = new Amount("1");
        Amount secondPredicateAmount = new Amount("2");

        HasExactAmountPredicate firstPredicate = new HasExactAmountPredicate(firstPredicateAmount);
        HasExactAmountPredicate secondPredicate = new HasExactAmountPredicate(secondPredicateAmount);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        HasExactAmountPredicate firstPredicateCopy = new HasExactAmountPredicate(firstPredicateAmount);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different transaction -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_amountMatches_returnsTrue() {
        HasExactAmountPredicate predicate = new HasExactAmountPredicate(new Amount("50"));
        assertTrue(predicate.test(new TransactionBuilder().withAmount("50.00").buildExpense()));
    }

    @Test
    public void test_amountDoesNotMatch_returnsFalse() {
        HasExactAmountPredicate predicate = new HasExactAmountPredicate(new Amount("51"));
        assertFalse(predicate.test(new TransactionBuilder().withAmount("50.00").buildExpense()));
    }
}
