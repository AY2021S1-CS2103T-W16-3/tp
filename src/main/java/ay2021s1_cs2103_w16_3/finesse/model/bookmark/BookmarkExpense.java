package ay2021s1_cs2103_w16_3.finesse.model.bookmark;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

public class BookmarkExpense extends BookmarkTransaction<Expense> {

    public BookmarkExpense(Title title, Amount amount, Set<Category> categories) {
        super(title, amount, categories);
    }

    /**
     * Returns true if the titles of both bookmark expenses are the same.
     */
    public boolean hasSameTitle(Object other) {
        return other instanceof BookmarkExpense && getTitle().equals(((BookmarkExpense) other).getTitle());
    }

    @Override
    public Expense convert(Date expenseDate) {
        Title title = getTitle();
        Amount amount = getAmount();
        Set<Category> categories = getCategories();

        return new Expense(title, amount, expenseDate, categories);
    }

    /**
     * Returns true if both bookmark expenses have the same identity and data fields.
     * This defines a stronger notion of equality between two bookmark expenses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BookmarkExpense)) {
            return false;
        }

        BookmarkExpense otherBookmarkExpense = (BookmarkExpense) other;
        return getTitle().equals(otherBookmarkExpense.getTitle())
                && getAmount().equals(otherBookmarkExpense.getAmount())
                && getCategories().equals(otherBookmarkExpense.getCategories());
    }
}
