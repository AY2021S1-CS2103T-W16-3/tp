package ay2021s1_cs2103_w16_3.finesse.testutil;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD_BEVERAGE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_INTERNSHIP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.BookmarkTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    // Expenses
    public static final Expense BUBBLE_TEA = new TransactionBuilder().withTitle("Bubble Tea")
            .withDate("14/10/2020").withAmount("$4.80").withCategories("Food & Beverage").buildExpense();
    public static final Expense TUITION_FEES = new TransactionBuilder().withTitle("Tuition Fees")
            .withDate("05/10/2020").withAmount("4221").withCategories("NUS", "GIRO").buildExpense();
    public static final Expense CARLS_JR = new TransactionBuilder().withTitle("Carl's Jr.").withAmount("11.60")
            .withDate("06/10/2020").withCategories("Food & Beverage").buildExpense();
    public static final Expense EZLINK_TOPUP = new TransactionBuilder().withTitle("EZ-Link Top-up").withAmount("20")
            .withDate("06/10/2020").withCategories("Transport").buildExpense();
    public static final Expense AIMA = new TransactionBuilder().withTitle("Artificial Intelligence: A Modern Approach")
            .withAmount("139.00").withDate("06/10/2020").buildExpense();
    public static final Expense PEN_REFILLS = new TransactionBuilder().withTitle("Pen Refills").withAmount("6")
            .withDate("06/10/2020").buildExpense();
    public static final Expense MOVIE = new TransactionBuilder().withTitle("Movie").withAmount("9")
            .withDate("06/10/2020").buildExpense();

    // Incomes
    public static final Income INTERNSHIP = new TransactionBuilder().withTitle("Internship")
            .withDate("06/10/2020").withAmount("$560").withCategories("Work").buildIncome();
    public static final Income TEACHING_ASSISTANT = new TransactionBuilder().withTitle("Teaching Assistant")
            .withDate("05/10/2020").withAmount("1920").withCategories("NUS", "GIRO").buildIncome();
    public static final Income STARTUP_FUNDING = new TransactionBuilder().withTitle("Start-up Funding")
            .withDate("20/09/2020").withAmount("10000.00").withCategories("NUS Enterprise").buildIncome();
    public static final Income ALLOWANCE = new TransactionBuilder().withTitle("Allowance")
            .withDate("06/10/2020").withAmount("$100.00").buildIncome();
    public static final Income ANG_PAO = new TransactionBuilder().withTitle("Ang Pao")
            .withDate("06/10/2020").withAmount("88").buildIncome();
    public static final Income GST_VOUCHER = new TransactionBuilder().withTitle("GST Voucher")
            .withDate("06/10/2020").withAmount("300").buildIncome();
    public static final Income HACKATHON_WINNINGS = new TransactionBuilder().withTitle("Hackathon Winnings")
            .withDate("22/09/2020").withAmount("1000").buildIncome();

    // Bookmark Expenses
    public static final BookmarkExpense PHONE_BILL = new BookmarkTransactionBuilder().withTitle("Phone Bill")
            .withAmount("60").buildBookmarkExpense();
    public static final BookmarkExpense SPOTIFY_SUBSCRIPTION = new BookmarkTransactionBuilder()
            .withTitle("Spotify Subscription").withAmount("9.90").buildBookmarkExpense();
    public static final BookmarkExpense NETFLIX_SUBSCRIPTION = new BookmarkTransactionBuilder()
            .withTitle("Netflix Subscription").withAmount("20").buildBookmarkExpense();
    public static final BookmarkExpense TIMES_MAGAZINE_SUBSCRIPTION = new BookmarkTransactionBuilder()
            .withTitle("Times Magazine Subscription").withAmount("12").buildBookmarkExpense();

    // Bookmark Incomes
    public static final BookmarkIncome PART_TIME = new BookmarkTransactionBuilder().withTitle("Part Time")
            .withAmount("450").withCategories("Work").buildBookmarkIncome();
    public static final BookmarkIncome INVESTING = new BookmarkTransactionBuilder().withTitle("Investing")
            .withAmount("50").buildBookmarkIncome();

    // Manually added
    public static final Expense TUITION_FEES_2 = new TransactionBuilder().withTitle("Tuition Fees")
            .withDate("05/10/2020").withAmount("4221").withCategories("NUS", "GIRO").buildExpense();
    public static final Income TEACHING_ASSISTANT_2 = new TransactionBuilder().withTitle("Teaching Assistant")
            .withDate("05/10/2020").withAmount("1920").withCategories("NUS", "GIRO").buildIncome();

    // Manually added - Transaction's details found in {@code CommandTestUtil}
    public static final Expense BUBBLE_TEA_2 = new TransactionBuilder().withTitle(VALID_TITLE_BUBBLE_TEA)
            .withAmount(VALID_AMOUNT_BUBBLE_TEA).withDate(VALID_DATE_BUBBLE_TEA)
            .withCategories(VALID_CATEGORY_FOOD_BEVERAGE).buildExpense();
    public static final Income INTERNSHIP_2 = new TransactionBuilder().withTitle(VALID_TITLE_INTERNSHIP)
            .withAmount(VALID_AMOUNT_INTERNSHIP).withDate(VALID_DATE_INTERNSHIP)
            .withCategories(VALID_CATEGORY_WORK, VALID_CATEGORY_FOOD_BEVERAGE).buildIncome();

    private TypicalTransactions() {} // prevents instantiation

    /**
     * Returns an {@code FinanceTracker} with all the typical transactions.
     */
    public static FinanceTracker getTypicalFinanceTracker() {
        FinanceTracker ft = new FinanceTracker();
        getTypicalExpenses().forEach(ft::addTransaction);
        getTypicalIncomes().forEach(ft::addTransaction);
        getTypicalBookmarkExpenses().forEach(ft::addBookmarkExpense);
        getTypicalBookmarkIncome().forEach(ft::addBookmarkIncome);
        return ft;
    }

    public static List<Transaction> getTypicalTransactions() {
        return Stream.concat(getTypicalExpenses().stream(), getTypicalIncomes().stream())
                .collect(Collectors.toList());
    }

    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(BUBBLE_TEA, TUITION_FEES, CARLS_JR,
                EZLINK_TOPUP, AIMA, PEN_REFILLS, MOVIE));
    }

    public static List<Income> getTypicalIncomes() {
        return new ArrayList<>(Arrays.asList(INTERNSHIP, TEACHING_ASSISTANT, STARTUP_FUNDING,
                ALLOWANCE, ANG_PAO, GST_VOUCHER, HACKATHON_WINNINGS));
    }

    public static List<BookmarkExpense> getTypicalBookmarkExpenses() {
        return new ArrayList<>(Arrays.asList(PHONE_BILL, SPOTIFY_SUBSCRIPTION, NETFLIX_SUBSCRIPTION,
                TIMES_MAGAZINE_SUBSCRIPTION));
    }

    public static List<BookmarkIncome> getTypicalBookmarkIncome() {
        return new ArrayList<>(Arrays.asList(PART_TIME, INVESTING));
    }

}
