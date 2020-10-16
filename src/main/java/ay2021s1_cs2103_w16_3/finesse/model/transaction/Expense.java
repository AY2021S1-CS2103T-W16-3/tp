package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;

public class Expense extends Transaction {

    public Expense(Title title, Amount amount, Date date, Set<Category> categories) {
        super(title, amount, date, categories);
    }

    /**
     * Returns true if both expenses have the same identity and data fields.
     * This defines a stronger notion of equality between two expenses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getTitle().equals(getTitle())
                && otherExpense.getAmount().equals(getAmount())
                && otherExpense.getDate().equals(getDate())
                && otherExpense.getCategories().equals(getCategories());
    }

}
