package ay2021s1_cs2103_w16_3.finesse.model.bookmark;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

public class BookmarkIncome extends BookmarkTransaction<Income> {

    public BookmarkIncome(Title title, Amount amount, Set<Category> categories) {
        super(title, amount, categories);
    }

    /**
     * Returns true if the titles of both bookmark incomes are the same.
     */
    public boolean hasSameTitle(Object other) {
        return other instanceof BookmarkIncome && getTitle().equals(((BookmarkIncome) other).getTitle());
    }

    @Override
    public Income convert(Date date) {
        Title title = getTitle();
        Amount amount = getAmount();
        Set<Category> categories = getCategories();

        return new Income(title, amount, date, categories);
    }

    /**
     * Returns true if both bookmark incomes have the same identity and data fields.
     * This defines a stronger notion of equality between two bookmark incomes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BookmarkIncome)) {
            return false;
        }
        BookmarkIncome otherBookmarkIncome = (BookmarkIncome) other;
        return getTitle().equals(otherBookmarkIncome.getTitle())
                && getAmount().equals(otherBookmarkIncome.getAmount())
                && getCategories().equals(otherBookmarkIncome.getCategories());
    }
}
