package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction.TRANSACTION_COMPARATOR;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INTERNSHIP_2;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalTransactions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.exceptions.TransactionNotFoundException;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;

public class TransactionListTest {

    private final TransactionList transactionList = new TransactionList();

    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, BUBBLE_TEA));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(BUBBLE_TEA, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.setTransaction(BUBBLE_TEA, BUBBLE_TEA));
    }

    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(BUBBLE_TEA);
        transactionList.setTransaction(BUBBLE_TEA, BUBBLE_TEA);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BUBBLE_TEA);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionHasSameIdentity_success() {
        transactionList.add(BUBBLE_TEA);
        Transaction editedBubbleTea = new TransactionBuilder(BUBBLE_TEA).withCategories(VALID_CATEGORY_WORK).build();
        transactionList.setTransaction(BUBBLE_TEA, editedBubbleTea);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(editedBubbleTea);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionHasDifferentIdentity_success() {
        transactionList.add(BUBBLE_TEA);
        transactionList.setTransaction(BUBBLE_TEA, INTERNSHIP_2);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(INTERNSHIP_2);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(BUBBLE_TEA));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(BUBBLE_TEA);
        transactionList.remove(BUBBLE_TEA);
        TransactionList expectedTransactionList = new TransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> transactionList.setTransactions((TransactionList) null));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedTransactionList() {
        transactionList.add(BUBBLE_TEA);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(INTERNSHIP_2);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((List<Transaction>) null));
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(BUBBLE_TEA);
        List<Transaction> transactionList = Collections.singletonList(INTERNSHIP_2);
        this.transactionList.setTransactions(transactionList);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(INTERNSHIP_2);
        assertEquals(expectedTransactionList, this.transactionList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> transactionList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_iteratesTransactionsInOrder_success() {
        List<Transaction> transactions = getTypicalTransactions();
        transactions.sort(TRANSACTION_COMPARATOR);
        transactionList.setTransactions(transactions);
        Iterator<Transaction> iterator = transactionList.iterator();
        transactions.stream().forEach(transaction -> assertEquals(transaction, iterator.next()));
    }

    @Test
    public void equals_distinctTransactionListsWithSameAttributes_returnsTrue() {
        List<Transaction> transactions = getTypicalTransactions();
        TransactionList firstTransactionList = new TransactionList();
        TransactionList secondTransactionList = new TransactionList();

        firstTransactionList.setTransactions(transactions);
        secondTransactionList.setTransactions(transactions);

        assertNotSame(firstTransactionList, secondTransactionList);
        assertEquals(firstTransactionList, secondTransactionList);
    }

    @Test
    public void hashCode_distinctTransactionListsWithSameAttributes_returnsTrue() {
        List<Transaction> transactions = getTypicalTransactions();
        TransactionList firstTransactionList = new TransactionList();
        TransactionList secondTransactionList = new TransactionList();

        firstTransactionList.setTransactions(transactions);
        secondTransactionList.setTransactions(transactions);

        assertNotSame(firstTransactionList, secondTransactionList);
        assertEquals(firstTransactionList.hashCode(), secondTransactionList.hashCode());
    }
}
