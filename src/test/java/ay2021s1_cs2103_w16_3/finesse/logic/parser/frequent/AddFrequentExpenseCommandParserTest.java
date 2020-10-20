package ay2021s1_cs2103_w16_3.finesse.logic.parser.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_CATEGORY_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalFrequentTransactions.PHONE_BILL;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.AddFrequentExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.frequentparsers.AddFrequentExpenseCommandParser;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentTransactionBuilder;

public class AddFrequentExpenseCommandParserTest {
    private AddFrequentExpenseCommandParser parser = new AddFrequentExpenseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        FrequentExpense expectedFrequentExpense = new FrequentTransactionBuilder(PHONE_BILL)
                .withCategories(VALID_CATEGORY_UTILITIES).buildFrequentExpense();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL
                + CATEGORY_DESC_CATEGORY_UTILITIES, new AddFrequentExpenseCommand(expectedFrequentExpense));

        // multiple titles - last title accepted
        assertParseSuccess(parser, TITLE_DESC_SPOTIFY_SUBSCRIPTION + TITLE_DESC_PHONE_BILL
                        + AMOUNT_DESC_PHONE_BILL + CATEGORY_DESC_CATEGORY_UTILITIES,
                new AddFrequentExpenseCommand(expectedFrequentExpense));

        // multiple amounts - last amount accepted
        assertParseSuccess(parser, TITLE_DESC_PHONE_BILL + AMOUNT_DESC_SPOTIFY_SUBSCRIPTION
                + AMOUNT_DESC_PHONE_BILL + CATEGORY_DESC_CATEGORY_UTILITIES,
                new AddFrequentExpenseCommand(expectedFrequentExpense));

        // multiple categories - all accepted
        FrequentExpense expectedExpenseMultipleCategories = new FrequentTransactionBuilder(PHONE_BILL)
                .withCategories(VALID_CATEGORY_UTILITIES, VALID_CATEGORY_WORK).buildFrequentExpense();

        assertParseSuccess(parser, TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL
                        + CATEGORY_DESC_CATEGORY_UTILITIES + CATEGORY_DESC_WORK,
                new AddFrequentExpenseCommand(expectedExpenseMultipleCategories));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero categories
        FrequentExpense expectedFrequentExpense = new FrequentTransactionBuilder(PHONE_BILL).withCategories()
                .buildFrequentExpense();
        assertParseSuccess(parser, TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL,
                new AddFrequentExpenseCommand(expectedFrequentExpense));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFrequentExpenseCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_PHONE_BILL + AMOUNT_DESC_PHONE_BILL, expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, TITLE_DESC_PHONE_BILL + VALID_AMOUNT_PHONE_BILL, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_PHONE_BILL + VALID_AMOUNT_PHONE_BILL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + AMOUNT_DESC_PHONE_BILL
                + CATEGORY_DESC_CATEGORY_UTILITIES + CATEGORY_DESC_WORK, Title.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, TITLE_DESC_PHONE_BILL + INVALID_AMOUNT_DESC
                + CATEGORY_DESC_CATEGORY_UTILITIES + CATEGORY_DESC_WORK, Amount.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL
                + INVALID_CATEGORY_DESC + CATEGORY_DESC_CATEGORY_UTILITIES, Category.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + AMOUNT_DESC_PHONE_BILL + INVALID_DATE_DESC,
                Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_PHONE_BILL
                        + CATEGORY_DESC_CATEGORY_UTILITIES + CATEGORY_DESC_WORK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFrequentExpenseCommand.MESSAGE_USAGE));
    }
}
