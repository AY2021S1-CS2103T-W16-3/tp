package ay2021s1_cs2103_w16_3.finesse.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;

/**
 * A utility class containing a list of {@code FrequentTransaction} objects to be used in tests.
 */
public class TypicalFrequentTransactions {

    // Frequent Expenses
    public static final FrequentExpense PHONE_BILL = new FrequentTransactionBuilder().withTitle("Phone Bill")
            .withAmount("60").buildFrequentExpense();
    public static final FrequentExpense SPOTIFY_SUBSCRIPTION = new FrequentTransactionBuilder()
            .withTitle("Spotify Subscription").withAmount("9.90").buildFrequentExpense();
    public static final FrequentExpense NETFLIX_SUBSCRIPTION = new FrequentTransactionBuilder()
            .withTitle("Netflix Subscription").withAmount("20").buildFrequentExpense();
    public static final FrequentExpense TIMES_MAGAZINE_SUBSCRIPTION = new FrequentTransactionBuilder()
            .withTitle("Times Magazine Subscription").withAmount("12").buildFrequentExpense();

    private TypicalFrequentTransactions() {} // prevents instantiation

    public static FinanceTracker getTypicalFinanceTracker() {
        FinanceTracker ft = new FinanceTracker();
        getTypicalFrequentExpenses().forEach(ft::addFrequentExpense);
        return ft;
    }

    public static List<FrequentExpense> getTypicalFrequentExpenses() {
        return new ArrayList<>(Arrays.asList(PHONE_BILL, SPOTIFY_SUBSCRIPTION, NETFLIX_SUBSCRIPTION,
                TIMES_MAGAZINE_SUBSCRIPTION));
    }
}
