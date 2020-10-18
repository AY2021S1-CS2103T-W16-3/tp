package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

public class FrequentExpense extends Expense {

    public FrequentExpense(Title title, Amount amount, Date date, Set<Category> categories) {
        super(title, amount, date, categories);
    }

    /**
     * Converts a frequent expense to a expense with new date.
     * @param newDate Date of when the frequent expense is converted to an expense.
     * @return a new {@code Expense} object.
     */
    public Expense convertFrequentExpenseToExpense(Date newDate) {
        Title title = super.getTitle();
        Amount amount = super.getAmount();
        Set<Category> categories = super.getCategories();
        return new Expense(title, amount, newDate, categories);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FrequentExpense)) {
            return false;
        }

        FrequentExpense otherFrequentExpense = (FrequentExpense) other;
        return otherFrequentExpense.getTitle().equals(getTitle())
                && otherFrequentExpense.getAmount().equals(getAmount())
                && otherFrequentExpense.getDate().equals(getDate())
                && otherFrequentExpense.getCategories().equals(getCategories());
    }

}
