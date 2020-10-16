package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;

public class Income extends Transaction {

    public Income(Title title, Amount amount, Date date, Set<Category> categories) {
        super(title, amount, date, categories);
    }

    /**
     * Returns true if both incomes have the same identity and data fields.
     * This defines a stronger notion of equality between two incomes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Income)) {
            return false;
        }

        Income otherIncome = (Income) other;
        return otherIncome.getTitle().equals(getTitle())
                && otherIncome.getAmount().equals(getAmount())
                && otherIncome.getDate().equals(getDate())
                && otherIncome.getCategories().equals(getCategories());
    }

}
