package ay2021s1_cs2103_w16_3.finesse.testutil;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;

public class TypicalFrequentExpenses {
    public static final FrequentExpense PHONE_BILL = new FrequentExpenseBuilder().withTitle("Phone Bill")
            .withAmount("$60.00").withCategories("Utilities").buildFrequentExpense();
    public static final FrequentExpense SPOTIFY_SUBSCRIPTION = new FrequentExpenseBuilder()
            .withTitle("Spotify Subscription").withAmount("$9.90")
            .withCategories("Miscellaneous").buildFrequentExpense();
}
