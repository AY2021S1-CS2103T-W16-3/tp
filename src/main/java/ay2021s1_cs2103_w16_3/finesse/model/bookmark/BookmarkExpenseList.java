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
 * A list of bookmark expenses that does not allow nulls.
 * The removal of a bookmark expense uses BookmarkExpense#equals(Object) so as to ensure that the bookmark expense
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class BookmarkExpenseList implements Iterable<BookmarkExpense> {
    private final ObservableList<BookmarkExpense> internalBookmarkExpenseList = FXCollections.observableArrayList();
    private final ObservableList<BookmarkExpense> internalUnmodifiableBookmarkExpenseList =
            FXCollections.unmodifiableObservableList(internalBookmarkExpenseList);

    /**
     * Adds a bookmark expense to the list.
     */
    public void add(BookmarkExpense toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBookmarkTransactionException("expense");
        }
        internalBookmarkExpenseList.add(toAdd);
    }

    /**
     * Returns true if the bookmark expense list contains a bookmark expense with the same title as the given argument.
     */
    public boolean contains(BookmarkExpense toCheck) {
        requireNonNull(toCheck);
        return internalBookmarkExpenseList.stream().anyMatch(toCheck::hasSameTitle);
    }

    /**
     * Replaces the bookmark expense {@code target} in the list with {@code editedBookmarkExpense}.
     * {@code target} must exist in the list.
     * The bookmark expense identity of {@code editedBookmarkExpense} must not be the same as another existing
     * bookmark expense in the list.
     */
    public void setBookmark(BookmarkExpense target, BookmarkExpense editedBookmarkExpense) {
        requireAllNonNull(target, editedBookmarkExpense);

        int index = internalBookmarkExpenseList.indexOf(target);
        if (index == -1) {
            throw new BookmarkTransactionNotFoundException();
        }

        internalBookmarkExpenseList.set(index, editedBookmarkExpense);
    }

    /**
     * Removes the equivalent bookmark expense from the list.
     * The bookmark expense must exist in the list.
     */
    public void remove(BookmarkExpense toRemove) {
        requireNonNull(toRemove);
        if (!(internalBookmarkExpenseList.remove(toRemove))) {
            throw new BookmarkTransactionNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<BookmarkExpense> asUnmodifiableObservableList() {
        return internalUnmodifiableBookmarkExpenseList;
    }

    /**
     * Replaces the contents of this list with {@code bookmarkExpenses}.
     */
    public void setBookmarkExpenses(List<BookmarkExpense> bookmarkExpenses) {
        requireAllNonNull(bookmarkExpenses);

        internalBookmarkExpenseList.setAll(bookmarkExpenses);
    }

    public void setBookmarkExpenses(BookmarkExpenseList replacement) {
        requireNonNull(replacement);
        internalBookmarkExpenseList.setAll(replacement.internalBookmarkExpenseList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookmarkExpenseList // instanceof handles nulls
                && internalBookmarkExpenseList.equals(((BookmarkExpenseList) other).internalBookmarkExpenseList));
    }

    @Override
    public int hashCode() {
        return internalBookmarkExpenseList.hashCode();
    }

    @Override
    public Iterator<BookmarkExpense> iterator() {
        return internalBookmarkExpenseList.iterator();
    }

}
