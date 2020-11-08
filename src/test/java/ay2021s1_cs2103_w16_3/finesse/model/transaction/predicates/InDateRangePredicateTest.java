package ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;

public class InDateRangePredicateTest {

    @Test
    public void equals() {
        Date firstPredicateDate = new Date("01/10/2020");
        Date secondPredicateDate = new Date("02/10/2020");

        InDateRangePredicate firstPredicate =
                new InDateRangePredicate(Optional.of(firstPredicateDate), Optional.ofNullable(null));
        InDateRangePredicate secondPredicate =
                new InDateRangePredicate(Optional.ofNullable(null), Optional.of(secondPredicateDate));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InDateRangePredicate firstPredicateCopy =
                new InDateRangePredicate(Optional.of(firstPredicateDate), Optional.ofNullable(null));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different transaction -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_lowerBoundOnly() {
        InDateRangePredicate predicate = new InDateRangePredicate(
                Optional.of(new Date("01/10/2020")), Optional.ofNullable((null)));
        // same as lower bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withDate("01/10/2020").buildExpense()));
        // more than lower bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withDate("15/10/2020").buildExpense()));
        // less than lower bound -> returns false
        assertFalse(predicate.test(new TransactionBuilder().withDate("22/09/2020").buildExpense()));
    }

    @Test
    public void test_upperBoundOnly() {
        InDateRangePredicate predicate = new InDateRangePredicate(
                Optional.ofNullable((null)), Optional.of(new Date("15/10/2020")));
        // same as upper bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withDate("15/10/2020").buildExpense()));
        // less than upper bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withDate("01/10/2020").buildExpense()));
        // more than upper bound -> returns false
        assertFalse(predicate.test(new TransactionBuilder().withDate("20/10/2020").buildExpense()));
    }

    @Test
    public void test_upperAndLowerBound() {
        InDateRangePredicate predicate = new InDateRangePredicate(
                Optional.of(new Date("01/10/2020")), Optional.of(new Date("15/10/2020")));
        // same as lower bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withDate("01/10/2020").buildExpense()));
        // same as upper bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withDate("15/10/2020").buildExpense()));
        // between lower and upper bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withDate("15/10/2020").buildExpense()));
        // less than lower bound -> returns false
        assertFalse(predicate.test(new TransactionBuilder().withDate("22/09/2020").buildExpense()));
        // more than upper bound -> returns false
        assertFalse(predicate.test(new TransactionBuilder().withDate("20/10/2020").buildExpense()));
    }
}
