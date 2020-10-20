package ay2021s1_cs2103_w16_3.finesse.testutil;

import java.util.HashSet;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.util.SampleDataUtil;

/**
 * A utility class to help with building FrequentExpense objects.
 */
public class FrequentTransactionBuilder {
    public static final String DEFAULT_TITLE = "Phone Bill";
    public static final String DEFAULT_AMOUNT = "$60.00";

    private Title title;
    private Amount amount;
    private Set<Category> categories;

    /**
     * Creates a {@code FrequentTransactionBuilder} with the default details.
     */
    public FrequentTransactionBuilder() {
        title = new Title(DEFAULT_TITLE);
        amount = new Amount(DEFAULT_AMOUNT);
        categories = new HashSet<>();
    }

    /**
     * Initializes the FrequentTransactionBuilder with the data of {@code frequentExpenseToCopy}.
     */
    public FrequentTransactionBuilder(FrequentTransaction<Expense> frequentExpenseToCopy) {
        title = frequentExpenseToCopy.getTitle();
        amount = frequentExpenseToCopy.getAmount();
        categories = new HashSet<>(frequentExpenseToCopy.getCategories());
    }

    /**
     * Sets the {@code Title} of the {@code FrequentExpense} that we are building.
     */
    public FrequentTransactionBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code FrequentExpense} that we are
     * building.
     */
    public FrequentTransactionBuilder withCategories(String ... categories) {
        this.categories = SampleDataUtil.getCategoriesSet(categories);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code FrequentExpesnse} that we are building.
     */
    public FrequentTransactionBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    public FrequentExpense buildFrequentExpense() {
        return new FrequentExpense(title, amount, categories);
    }

}

