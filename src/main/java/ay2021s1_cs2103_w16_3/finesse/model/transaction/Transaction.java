package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;

/**
 * Represents a Transaction in the finance tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction implements Comparable<Transaction> {

    // Identity fields
    private final Title title;
    private final Amount amount;
    private final Date date;

    // Data fields
    private final Set<Category> categories = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Transaction(Title title, Amount amount, Date date, Set<Category> categories) {
        requireAllNonNull(title, amount, date, categories);
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.categories.addAll(categories);
    }

    public Title getTitle() {
        return title;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    @Override // TODO
    public int compareTo(Transaction transaction) {
        return 0;
    }

    /**
     * Returns true if both transactions have the same identity and data fields.
     * This defines a stronger notion of equality between two transactions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return otherTransaction.getTitle().equals(getTitle())
                && otherTransaction.getAmount().equals(getAmount())
                && otherTransaction.getDate().equals(getDate())
                && otherTransaction.getCategories().equals(getCategories());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, amount, date, categories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Date: ")
                .append(getDate())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }


}
