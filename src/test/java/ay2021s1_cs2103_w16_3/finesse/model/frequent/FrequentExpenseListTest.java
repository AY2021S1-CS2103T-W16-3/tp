package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalFrequentExpenses.PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalFrequentExpenses.SPOTIFY_SUBSCRIPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.exceptions.FrequentExpenseNotFoundException;
import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentExpenseBuilder;

public class FrequentExpenseListTest {

    private final FrequentExpenseList frequentExpenseList = new FrequentExpenseList();

    @Test
    public void add_nullFrequentExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> frequentExpenseList.add(null));
    }

    @Test
    public void setFrequentExpense_nullTargetFrequentExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> frequentExpenseList.setFrequentExpense(null, PHONE_BILL));
    }

    @Test
    public void setFrequentExpense_nullEditedTFrequentExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> frequentExpenseList.setFrequentExpense(PHONE_BILL, null));
    }

    @Test
    public void setFrequentExpense_targetFrequentExpenseNotInList_throwsTransactionNotFoundException() {
        assertThrows(FrequentExpenseNotFoundException.class,
                () -> frequentExpenseList.setFrequentExpense(PHONE_BILL, PHONE_BILL));
    }

    @Test
    public void setFrequentExpense_editedFrequentExpenseIsSameFrequentExpense_success() {
        frequentExpenseList.add(PHONE_BILL);
        frequentExpenseList.setFrequentExpense(PHONE_BILL, PHONE_BILL);
        FrequentExpenseList expectedTransactionList = new FrequentExpenseList();
        expectedTransactionList.add(PHONE_BILL);
        assertEquals(expectedTransactionList, frequentExpenseList);
    }

    @Test
    public void setFrequentExpense_editedFrequentExpenseHasSameIdentity_success() {
        frequentExpenseList.add(PHONE_BILL);
        FrequentExpense editedPhone = new FrequentExpenseBuilder(PHONE_BILL).withCategories(VALID_CATEGORY_UTILITIES)
                .build();
        frequentExpenseList.setFrequentExpense(PHONE_BILL, editedPhone);
        FrequentExpenseList expectedTransactionList = new FrequentExpenseList();
        expectedTransactionList.add(editedPhone);
        assertEquals(expectedTransactionList, frequentExpenseList);
    }

    @Test
    public void setFrequentExpense_editedFrequentExpenseHasDifferentIdentity_success() {
        frequentExpenseList.add(PHONE_BILL);
        frequentExpenseList.setFrequentExpense(PHONE_BILL, SPOTIFY_SUBSCRIPTION);
        FrequentExpenseList expectedTransactionList = new FrequentExpenseList();
        expectedTransactionList.add(SPOTIFY_SUBSCRIPTION);
        assertEquals(expectedTransactionList, frequentExpenseList);
    }

    @Test
    public void remove_nullFrequentExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> frequentExpenseList.remove(null));
    }

    @Test
    public void remove_frequentExpenseDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(FrequentExpenseNotFoundException.class, () -> frequentExpenseList.remove(PHONE_BILL));
    }

    @Test
    public void remove_existingFrequentExpense_removesFrequentExpense() {
        frequentExpenseList.add(PHONE_BILL);
        frequentExpenseList.remove(PHONE_BILL);
        FrequentExpenseList expectedTransactionList = new FrequentExpenseList();
        assertEquals(expectedTransactionList, frequentExpenseList);
    }

    @Test
    public void setFrequentExpenses_nullFrequentExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> frequentExpenseList.setFrequentExpenses((FrequentExpenseList) null));
    }

    @Test
    public void setFrequentExpenses_frequentExpenseList_replacesOwnListWithProvidedFrequentExpenseList() {
        frequentExpenseList.add(PHONE_BILL);
        FrequentExpenseList expectedTransactionList = new FrequentExpenseList();
        expectedTransactionList.add(SPOTIFY_SUBSCRIPTION);
        frequentExpenseList.setFrequentExpenses(expectedTransactionList);
        assertEquals(expectedTransactionList, frequentExpenseList);
    }

    @Test
    public void setFrequentExpenses_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> frequentExpenseList.setFrequentExpenses((List<FrequentExpense>) null));
    }

    @Test
    public void setFrequentExpenses_list_replacesOwnListWithProvidedList() {
        frequentExpenseList.add(PHONE_BILL);
        List<FrequentExpense> transactionList = Collections.singletonList(SPOTIFY_SUBSCRIPTION);
        this.frequentExpenseList.setFrequentExpenses(transactionList);
        FrequentExpenseList expectedTransactionList = new FrequentExpenseList();
        expectedTransactionList.add(SPOTIFY_SUBSCRIPTION);
        assertEquals(expectedTransactionList, this.frequentExpenseList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> frequentExpenseList.asUnmodifiableObservableList().remove(0));
    }

}
