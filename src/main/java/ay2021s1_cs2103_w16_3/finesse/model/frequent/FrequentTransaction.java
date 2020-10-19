package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import java.util.Collections;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

public abstract class FrequentTransaction<T extends Transaction> {
    private final Title title;
    private final Amount amount;
    private final Set<Category> categories;

    protected FrequentTransaction(T base) {
        title = base.getTitle();
        amount = base.getAmount();
        categories = base.getCategories();
    }

    protected FrequentTransaction(Title title, Amount amount, Set<Category> categories) {
        this.title = title;
        this.amount = amount;
        this.categories = categories;
    }

    public Title getTitle() {
        return this.title;
    }

    public Amount getAmount() {
        return this.amount;
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public abstract T convert(Date date);

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Date: ")
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
