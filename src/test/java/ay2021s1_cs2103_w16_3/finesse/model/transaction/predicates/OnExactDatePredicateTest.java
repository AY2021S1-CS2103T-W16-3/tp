package ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;

public class OnExactDatePredicateTest {

    @Test
    public void equals() {
        Date firstPredicateDate = new Date("01/10/2020");
        Date secondPredicateDate = new Date("02/10/2020");

        OnExactDatePredicate firstPredicate = new OnExactDatePredicate(firstPredicateDate);
        OnExactDatePredicate secondPredicate = new OnExactDatePredicate(secondPredicateDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OnExactDatePredicate firstPredicateCopy = new OnExactDatePredicate(firstPredicateDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different transaction -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateMatches_returnsTrue() {
        OnExactDatePredicate predicate = new OnExactDatePredicate(new Date("01/10/2020"));
        assertTrue(predicate.test(new TransactionBuilder().withDate("01/10/2020").buildExpense()));
    }

    @Test
    public void test_dateDoesNotMatch_returnsFalse() {
        OnExactDatePredicate predicate = new OnExactDatePredicate(new Date("01/10/2020"));
        assertFalse(predicate.test(new TransactionBuilder().withDate("10/01/2020").buildExpense()));
    }
}
