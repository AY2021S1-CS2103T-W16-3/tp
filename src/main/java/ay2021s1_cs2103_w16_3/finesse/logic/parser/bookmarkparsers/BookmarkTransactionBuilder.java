package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers;

import java.util.HashSet;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.util.SampleDataUtil;

/**
 * A utility class to help with building BookmarkExpense and BookmarkIncome objects.
 */
public class BookmarkTransactionBuilder {
    public static final String DEFAULT_TITLE = "Phone Bill";
    public static final String DEFAULT_AMOUNT = "$60.00";

    private Title title;
    private Amount amount;
    private Set<Category> categories;

    /**
     * Creates a {@code BookmarkTransactionBuilder} with the given {@code Title}, {@code Amount} and {@code Category}.
     */
    public BookmarkTransactionBuilder(Title title, Amount amount, Set<Category> categories) {
        this.title = title;
        this.amount = amount;
        this.categories = categories;
    }

    /**
     * Creates a {@code BookmarkTransactionBuilder} with the default details.
     */
    public BookmarkTransactionBuilder() {
        title = new Title(DEFAULT_TITLE);
        amount = new Amount(DEFAULT_AMOUNT);
        categories = new HashSet<>();
    }

    /**
     * Initializes the BookmarkTransactionBuilder with the data of {@code bookmarkTransactionToCopy}.
     */
    public <T extends Transaction> BookmarkTransactionBuilder(BookmarkTransaction<T> bookmarkTransactionToCopy) {
        title = bookmarkTransactionToCopy.getTitle();
        amount = bookmarkTransactionToCopy.getAmount();
        categories = new HashSet<>(bookmarkTransactionToCopy.getCategories());
    }

    /**
     * Sets the {@code Title} of the {@code BookmarkTransaction} that we are building.
     */
    public BookmarkTransactionBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code BookmarkTransaction}
     * that we are building.
     */
    public BookmarkTransactionBuilder withCategories(String ... categories) {
        this.categories = SampleDataUtil.getCategoriesSet(categories);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code BookmarkTransaction} that we are building.
     */
    public BookmarkTransactionBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    public BookmarkExpense buildBookmarkExpense() {
        return new BookmarkExpense(title, amount, categories);
    }

    public BookmarkIncome buildBookmarkIncome() {
        return new BookmarkIncome(title, amount, categories);
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object.
        if (other == this) {
            return true;
        }

        // instanceof handles nulls.
        if (!(other instanceof BookmarkTransactionBuilder)) {
            return false;
        }

        BookmarkTransactionBuilder otherBookmarkTransactionBuilder = (BookmarkTransactionBuilder) other;
        return title.equals(otherBookmarkTransactionBuilder.title)
                && amount.equals(otherBookmarkTransactionBuilder.amount)
                && categories.equals(otherBookmarkTransactionBuilder.categories);
    }
}
