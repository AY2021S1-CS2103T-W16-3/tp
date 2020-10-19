package ay2021s1_cs2103_w16_3.finesse.testutil;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;

public class TypicalFrequentExpenses {
    public static final FrequentExpense PHONE_BILL = new FrequentExpenseBuilder().withTitle("Phone Bill")
            .withDate("07/10/2020").withAmount("$60.00").withCategories("Utilities").build();
    public static final FrequentExpense SPOTIFY_SUBSCRIPTION = new FrequentExpenseBuilder()
            .withTitle("Spotify Subscription").withDate("07/10/2020").withAmount("$9.90")
            .withCategories("Miscellaneous").build();
}
