package ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;

public class HasCategoriesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateCategoryList = Collections.singletonList("first");
        List<String> secondPredicateCategoryList = Arrays.asList("first", "second");

        HasCategoriesPredicate firstPredicate = new HasCategoriesPredicate(firstPredicateCategoryList);
        HasCategoriesPredicate secondPredicate = new HasCategoriesPredicate(secondPredicateCategoryList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        HasCategoriesPredicate firstPredicateCopy = new HasCategoriesPredicate(firstPredicateCategoryList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different transaction -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_categoriesContainMatch_returnsTrue() {
        // One category
        HasCategoriesPredicate predicate = new HasCategoriesPredicate(Collections.singletonList("Food"));
        assertTrue(predicate.test(new TransactionBuilder().withCategories("Food", "Drink").buildExpense()));

        // Multiple categories
        predicate = new HasCategoriesPredicate(Arrays.asList("Food", "Drink"));
        assertTrue(predicate.test(new TransactionBuilder().withCategories("Food", "Drink").buildExpense()));

        // Only one matching category
        predicate = new HasCategoriesPredicate(Arrays.asList("Food", "Dronk"));
        assertTrue(predicate.test(new TransactionBuilder().withCategories("Food", "Drink").buildExpense()));

        // Mixed-case keywords
        predicate = new HasCategoriesPredicate(Arrays.asList("fOoD", "dRiNk"));
        assertTrue(predicate.test(new TransactionBuilder().withCategories("Food", "Drink").buildExpense()));
    }

    @Test
    public void test_categoriesDoNotMatch_returnsFalse() {
        // Zero categories
        HasCategoriesPredicate predicate = new HasCategoriesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withCategories("Food", "Drink").buildExpense()));

        // Non-matching category
        predicate = new HasCategoriesPredicate(Arrays.asList("Dronk"));
        assertFalse(predicate.test(new TransactionBuilder().withCategories("Food", "Drink").buildExpense()));

        // Category matches title, but does not match category
        predicate = new HasCategoriesPredicate(
                Arrays.asList("Bubble Tea"));
        assertFalse(predicate.test(new TransactionBuilder().withTitle("Bubble Tea").buildExpense()));
    }
}
