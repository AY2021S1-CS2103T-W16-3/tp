package ay2021s1_cs2103_w16_3.finesse.model.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.BookmarkTransactionNotFoundException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.DuplicateBookmarkTransactionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of bookmark incomes that does not allow nulls.
 * The removal of a bookmark income uses BookmarkIncome#equals(Object) so as to ensure that the bookmark income
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class BookmarkIncomeList implements Iterable<BookmarkIncome> {
    private final ObservableList<BookmarkIncome> internalBookmarkIncomeList = FXCollections.observableArrayList();
    private final ObservableList<BookmarkIncome> internalUnmodifiableBookmarkIncomeList =
            FXCollections.unmodifiableObservableList(internalBookmarkIncomeList);

    /**
     * Adds a bookmark income to the list.
     */
    public void add(BookmarkIncome toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBookmarkTransactionException("income");
        }
        internalBookmarkIncomeList.add(toAdd);
    }

    /**
     * Returns true if the bookmark income list contains a bookmark income with the same title as the given argument.
     */
    public boolean contains(BookmarkIncome toCheck) {
        requireNonNull(toCheck);
        return internalBookmarkIncomeList.stream().anyMatch(toCheck::hasSameTitle);
    }

    /**
     * Replaces the bookmark income {@code target} in the list with {@code editedBookmarkIncome}.
     * {@code target} must exist in the list.
     * The bookmark income identity of {@code editedBookmarkIncome} must not be the same as another existing
     * bookmark income in the list.
     */
    public void setBookmarkIncome(BookmarkIncome target, BookmarkIncome editedBookmarkIncome) {
        requireAllNonNull(target, editedBookmarkIncome);

        int index = internalBookmarkIncomeList.indexOf(target);
        if (index == -1) {
            throw new BookmarkTransactionNotFoundException();
        }

        internalBookmarkIncomeList.set(index, editedBookmarkIncome);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<BookmarkIncome> asUnmodifiableObservableList() {
        return internalUnmodifiableBookmarkIncomeList;
    }

    /**
     * Removes the equivalent bookmark income from the list.
     * The bookmark income must exist in the list.
     */
    public void remove(BookmarkIncome toRemove) {
        requireNonNull(toRemove);
        if (!(internalBookmarkIncomeList.remove(toRemove))) {
            throw new BookmarkTransactionNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code bookmarkIncomes}.
     */
    public void setBookmarkIncomes(List<BookmarkIncome> bookmarkIncomes) {
        requireAllNonNull(bookmarkIncomes);

        internalBookmarkIncomeList.setAll(bookmarkIncomes);
    }

    public void setBookmarkIncomes(BookmarkIncomeList replacement) {
        requireNonNull(replacement);
        internalBookmarkIncomeList.setAll(replacement.internalBookmarkIncomeList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BookmarkIncomeList)
                && internalBookmarkIncomeList.equals(((BookmarkIncomeList) other).internalBookmarkIncomeList);
    }

    @Override
    public int hashCode() {
        return internalBookmarkIncomeList.hashCode();
    }

    @Override
    public Iterator<BookmarkIncome> iterator() {
        return internalBookmarkIncomeList.iterator();
    }

}
