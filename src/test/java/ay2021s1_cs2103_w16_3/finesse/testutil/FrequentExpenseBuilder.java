package ay2021s1_cs2103_w16_3.finesse.testutil;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building FrequentExpense objects.
 */
public class FrequentExpenseBuilder {
    public static final String DEFAULT_TITLE = "Phone Bill";
    public static final String DEFAULT_AMOUNT = "$60.00";
    public static final String DEFAULT_DATE = "07/10/2020";

    private Title title;
    private Amount amount;
    private Date date;
    private Set<Category> categories;

    /**
     * Creates a {@code FrequentExpenseBuilder} with the default details.
     */
    public FrequentExpenseBuilder() {
        title = new Title(DEFAULT_TITLE);
        amount = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        categories = new HashSet<>();
    }

    /**
     * Initializes the FrequentExpenseBuilder with the data of {@code frequentExpenseToCopy}.
     */
    public FrequentExpenseBuilder(FrequentExpense frequentExpenseToCopy) {
        title = frequentExpenseToCopy.getTitle();
        amount = frequentExpenseToCopy.getAmount();
        date = frequentExpenseToCopy.getDate();
        categories = new HashSet<>(frequentExpenseToCopy.getCategories());
    }

    /**
     * Sets the {@code Title} of the {@code FrequentExpense} that we are building.
     */
    public FrequentExpenseBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code FrequentExpense} that we are
     * building.
     */
    public FrequentExpenseBuilder withCategories(String ... categories) {
        this.categories = SampleDataUtil.getCategoriesSet(categories);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code FrequentExpesnse} that we are building.
     */
    public FrequentExpenseBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code FrequentExpense} that we are building.
     */
    public FrequentExpenseBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public FrequentExpense build() {
        return new FrequentExpense(title, amount, date, categories);
    }

}

