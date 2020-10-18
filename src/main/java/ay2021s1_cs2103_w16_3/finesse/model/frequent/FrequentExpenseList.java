package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.exceptions.FrequentExpenseNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

public class FrequentExpenseList {
    private final ObservableList<FrequentExpense> internalFrequentExpenseList = FXCollections.observableArrayList();
    private final ObservableList<FrequentExpense> internalUnmodifiableFrequentExpenseList =
            FXCollections.unmodifiableObservableList(internalFrequentExpenseList);

    /**
     * Adds a frequent expense to the list.
     */
    public void add(FrequentExpense toAdd) {
        requireNonNull(toAdd);
        internalFrequentExpenseList.add(toAdd);
    }

    /**
     * Removes the equivalent frequent expense from the list.
     * The frequent expense must exist in the list.
     */
    public void remove(FrequentExpense toRemove) {
        requireNonNull(toRemove);
        if (!(internalFrequentExpenseList.remove(toRemove))) {
            throw new FrequentExpenseNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<FrequentExpense> asUnmodifiableObservableList() {
        return internalUnmodifiableFrequentExpenseList;
    }

    /**
     * Replaces the contents of this list with {@code frequentExpenses}.
     */
    public void setFrequentExpenses(List<FrequentExpense> frequentExpenses) {
        requireAllNonNull(frequentExpenses);

        internalFrequentExpenseList.setAll(frequentExpenses);
    }

    public Iterator<FrequentExpense> iterator() {
        return internalFrequentExpenseList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FrequentExpenseList // instanceof handles nulls
                && internalFrequentExpenseList.equals(((FrequentExpenseList) other).internalFrequentExpenseList));
    }

    @Override
    public int hashCode() {
        return internalFrequentExpenseList.hashCode();
    }
}
