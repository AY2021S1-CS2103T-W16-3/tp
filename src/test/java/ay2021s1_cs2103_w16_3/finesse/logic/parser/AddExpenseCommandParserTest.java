package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_FOOD_BEVERAGE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DATE_DESC_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DATE_DESC_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD_BEVERAGE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.BUBBLE_TEA_2;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INTERNSHIP_2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

public class AddExpenseCommandParserTest {
    private static final String CURRENT_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    private AddExpenseCommandParser parser = new AddExpenseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Expense expectedExpense = new TransactionBuilder(INTERNSHIP_2)
                .withCategories(VALID_CATEGORY_FOOD_BEVERAGE).buildExpense();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_INTERNSHIP + AMOUNT_DESC_INTERNSHIP
                + DATE_DESC_INTERNSHIP + CATEGORY_DESC_FOOD_BEVERAGE, new AddExpenseCommand(expectedExpense));

        // multiple categories - all accepted
        Expense expectedExpenseMultipleCategories = new TransactionBuilder(INTERNSHIP_2)
                .withCategories(VALID_CATEGORY_FOOD_BEVERAGE, VALID_CATEGORY_WORK).buildExpense();
        assertParseSuccess(parser, TITLE_DESC_INTERNSHIP + AMOUNT_DESC_INTERNSHIP + DATE_DESC_INTERNSHIP
                + CATEGORY_DESC_WORK + CATEGORY_DESC_FOOD_BEVERAGE,
                new AddExpenseCommand(expectedExpenseMultipleCategories));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero categories
        Expense expectedExpenseWithNoCategories = new TransactionBuilder(BUBBLE_TEA_2).withCategories().buildExpense();
        assertParseSuccess(parser, TITLE_DESC_BUBBLE_TEA + AMOUNT_DESC_BUBBLE_TEA + DATE_DESC_BUBBLE_TEA,
                new AddExpenseCommand(expectedExpenseWithNoCategories));

        // arguments has no date
        Expense expectedExpenseWithCurrentDate = new TransactionBuilder().withTitle(VALID_TITLE_INTERNSHIP)
                .withAmount(VALID_AMOUNT_INTERNSHIP).withCategories(VALID_CATEGORY_WORK).withDate(CURRENT_DATE)
                .buildExpense();
        assertParseSuccess(parser, TITLE_DESC_INTERNSHIP + AMOUNT_DESC_INTERNSHIP + CATEGORY_DESC_WORK,
                new AddExpenseCommand(expectedExpenseWithCurrentDate));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_INTERNSHIP + AMOUNT_DESC_INTERNSHIP + DATE_DESC_INTERNSHIP,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, TITLE_DESC_INTERNSHIP + VALID_AMOUNT_INTERNSHIP + DATE_DESC_INTERNSHIP,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_INTERNSHIP + VALID_AMOUNT_INTERNSHIP + VALID_DATE_INTERNSHIP,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + AMOUNT_DESC_INTERNSHIP + DATE_DESC_INTERNSHIP
                + CATEGORY_DESC_WORK + CATEGORY_DESC_FOOD_BEVERAGE, Title.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, TITLE_DESC_INTERNSHIP + INVALID_AMOUNT_DESC + DATE_DESC_INTERNSHIP
                + CATEGORY_DESC_WORK + CATEGORY_DESC_FOOD_BEVERAGE, Amount.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, TITLE_DESC_INTERNSHIP + AMOUNT_DESC_INTERNSHIP + INVALID_DATE_DESC
                + CATEGORY_DESC_WORK + CATEGORY_DESC_FOOD_BEVERAGE, Date.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, TITLE_DESC_INTERNSHIP + AMOUNT_DESC_INTERNSHIP + DATE_DESC_INTERNSHIP
                + INVALID_CATEGORY_DESC + VALID_CATEGORY_FOOD_BEVERAGE, Category.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + AMOUNT_DESC_INTERNSHIP + INVALID_DATE_DESC,
                Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_INTERNSHIP + AMOUNT_DESC_INTERNSHIP
                + DATE_DESC_INTERNSHIP + CATEGORY_DESC_WORK + CATEGORY_DESC_FOOD_BEVERAGE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // multiple titles
        assertParseFailure(parser, TITLE_DESC_BUBBLE_TEA + TITLE_DESC_INTERNSHIP + AMOUNT_DESC_INTERNSHIP
                        + DATE_DESC_INTERNSHIP + CATEGORY_DESC_FOOD_BEVERAGE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_TITLE_CONSTRAINTS));

        // multiple amounts
        assertParseFailure(parser, TITLE_DESC_INTERNSHIP + AMOUNT_DESC_BUBBLE_TEA + AMOUNT_DESC_INTERNSHIP
                        + DATE_DESC_INTERNSHIP + CATEGORY_DESC_FOOD_BEVERAGE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_AMOUNT_CONSTRAINTS));

        // multiple dates
        assertParseFailure(parser, TITLE_DESC_INTERNSHIP + AMOUNT_DESC_INTERNSHIP + DATE_DESC_BUBBLE_TEA
                        + DATE_DESC_INTERNSHIP + CATEGORY_DESC_FOOD_BEVERAGE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_DATE_CONSTRAINTS));
    }
}
