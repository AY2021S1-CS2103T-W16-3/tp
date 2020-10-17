package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.ALICE;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.BOB;
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
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, ALICE));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(ALICE, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.setTransaction(ALICE, ALICE));
    }

    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(ALICE);
        transactionList.setTransaction(ALICE, ALICE);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(ALICE);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionHasSameIdentity_success() {
        transactionList.add(ALICE);
        Transaction editedAlice = new TransactionBuilder(ALICE).withCategories(VALID_CATEGORY_HUSBAND).build();
        transactionList.setTransaction(ALICE, editedAlice);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(editedAlice);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionHasDifferentIdentity_success() {
        transactionList.add(ALICE);
        transactionList.setTransaction(ALICE, BOB);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BOB);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(ALICE));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(ALICE);
        transactionList.remove(ALICE);
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
        transactionList.add(ALICE);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BOB);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((List<Transaction>) null));
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(ALICE);
        List<Transaction> transactionList = Collections.singletonList(BOB);
        this.transactionList.setTransactions(transactionList);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BOB);
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
