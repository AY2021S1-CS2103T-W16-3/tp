package ay2021s1_cs2103_w16_3.finesse.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Contains utility methods for populating {@code FinanceTracker} with sample data.
 */
public class SampleDataUtil {
    private static final Logger logger = LogsCenter.getLogger(SampleDataUtil.class);

    public static Transaction[] getSampleTransactions() {
        return new Transaction[] {
            new Expense(new Title("Bubble Tea"), new Amount("4.80"), new Date("14/10/2020"),
                getCategoriesSet("Food & Beverage")),
            new Expense(new Title("Tuition Fees"), new Amount("4221"), new Date("22/09/2020"),
                getCategoriesSet("NUS", "GIRO")),
            new Expense(new Title("Artificial Intelligence: A Modern Approach"), new Amount("139"),
                new Date("15/08/2020"), getCategoriesSet("Textbook")),
            new Income(new Title("Internship"), new Amount("560"), new Date("04/08/2020"),
                getCategoriesSet("Work")),
            new Income(new Title("Teaching Assistant"), new Amount("1920"), new Date("18/10/2020"),
                getCategoriesSet("CS1101S", "CS2103T")),
            new Income(new Title("Start-up Funding"), new Amount("10000"), new Date("01/10/2020"),
                getCategoriesSet("NUS Enterprise"))
        };
    }

    public static ReadOnlyFinanceTracker getSampleFinanceTracker() {
        logger.info("Populating Fine$$e with sample data...");
        FinanceTracker sampleAb = new FinanceTracker();
        for (Transaction sampleTransaction : getSampleTransactions()) {
            sampleAb.addTransaction(sampleTransaction);
        }
        return sampleAb;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getCategoriesSet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }

}
