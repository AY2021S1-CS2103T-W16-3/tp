package ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;

public class InAmountRangePredicateTest {

    @Test
    public void equals() {
        Amount firstPredicateAmount = new Amount("1");
        Amount secondPredicateAmount = new Amount("2");

        InAmountRangePredicate firstPredicate =
                new InAmountRangePredicate(Optional.of(firstPredicateAmount), Optional.ofNullable(null));
        InAmountRangePredicate secondPredicate =
                new InAmountRangePredicate(Optional.ofNullable(null), Optional.of(secondPredicateAmount));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InAmountRangePredicate firstPredicateCopy =
                new InAmountRangePredicate(Optional.of(firstPredicateAmount), Optional.ofNullable(null));
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
        InAmountRangePredicate predicate = new InAmountRangePredicate(
                Optional.of(new Amount("50")), Optional.ofNullable((null)));
        // same as lower bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withAmount("50.00").buildExpense()));
        // more than lower bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withAmount("55.00").buildExpense()));
        // less than lower bound -> returns false
        assertFalse(predicate.test(new TransactionBuilder().withAmount("45.00").buildExpense()));
    }

    @Test
    public void test_upperBoundOnly() {
        InAmountRangePredicate predicate = new InAmountRangePredicate(
                Optional.ofNullable((null)), Optional.of(new Amount("50")));
        // same as upper bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withAmount("50.00").buildExpense()));
        // less than upper bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withAmount("45.00").buildExpense()));
        // more than upper bound -> returns false
        assertFalse(predicate.test(new TransactionBuilder().withAmount("55.00").buildExpense()));
    }

    @Test
    public void test_upperAndLowerBound() {
        InAmountRangePredicate predicate = new InAmountRangePredicate(
                Optional.of(new Amount("10")), Optional.of(new Amount("50")));
        // same as lower bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withAmount("10.00").buildExpense()));
        // same as upper bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withAmount("50.00").buildExpense()));
        // between lower and upper bound -> returns true
        assertTrue(predicate.test(new TransactionBuilder().withAmount("45.00").buildExpense()));
        // less than lower bound -> returns false
        assertFalse(predicate.test(new TransactionBuilder().withAmount("5.00").buildExpense()));
        // more than upper bound -> returns false
        assertFalse(predicate.test(new TransactionBuilder().withAmount("55.00").buildExpense()));
    }
}
