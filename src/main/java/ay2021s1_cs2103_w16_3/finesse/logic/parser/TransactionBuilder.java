package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import java.util.HashSet;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.util.SampleDataUtil;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_TITLE = "Bubble Tea";
    public static final String DEFAULT_AMOUNT = "$4.80";
    public static final String DEFAULT_DATE = "14/10/2020";

    private Title title;
    private Amount amount;
    private Date date;
    private Set<Category> categories;

    /**
     * Creates a {@code TransactionBuilder} with the given {@code Title}, {@code Amount}, {@code Date} and
     * {@code Category}.
     */
    public TransactionBuilder(Title title, Amount amount, Date date, Set<Category> categories) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.categories = categories;
    }

    /**
     * Creates a {@code TransactionBuilder} with the default details.
     */
    public TransactionBuilder() {
        title = new Title(DEFAULT_TITLE);
        amount = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        categories = new HashSet<>();
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        title = transactionToCopy.getTitle();
        amount = transactionToCopy.getAmount();
        date = transactionToCopy.getDate();
        categories = new HashSet<>(transactionToCopy.getCategories());
    }

    /**
     * Sets the {@code Title} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code Transaction} that we are
     * building.
     */
    public TransactionBuilder withCategories(String ... categories) {
        this.categories = SampleDataUtil.getCategoriesSet(categories);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Income buildIncome() {
        return new Income(title, amount, date, categories);
    }

    public Expense buildExpense() {
        return new Expense(title, amount, date, categories);
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object.
        if (other == this) {
            return true;
        }

        // instanceof handles nulls.
        if (!(other instanceof TransactionBuilder)) {
            return false;
        }

        TransactionBuilder otherTransactionBuilder = (TransactionBuilder) other;
        return title.equals(otherTransactionBuilder.title)
                && amount.equals(otherTransactionBuilder.amount)
                && date.equals(otherTransactionBuilder.date)
                && categories.equals(otherTransactionBuilder.categories);
    }

}
