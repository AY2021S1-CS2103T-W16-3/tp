package ay2021s1_cs2103_w16_3.finesse.logic.parser.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_FOOD_BEVERAGE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_MISCELLANEOUS;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_MISCELLANEOUS;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST_FREQUENT_EXPENSE;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND_FREQUENT_EXPENSE;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_THIRD_FREQUENT_EXPENSE;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.frequentparsers.EditFrequentExpenseCommandParser;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditFrequentExpenseDescriptorBuilder;

public class EditFrequentExpenseCommandParserTest {

    private static final String CATEGORY_EMPTY = " " + PREFIX_CATEGORY;
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditFrequentExpenseCommand.MESSAGE_USAGE);
    private EditFrequentExpenseCommandParser parser = new EditFrequentExpenseCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_PHONE_BILL, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditFrequentExpenseCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_PHONE_BILL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_PHONE_BILL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid amount
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid category

        // invalid amount followed by valid category
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC + CATEGORY_DESC_UTILITIES,
                Amount.MESSAGE_CONSTRAINTS);

        // valid amount followed by invalid amount. The test case for invalid amount followed by valid amount
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + AMOUNT_DESC_PHONE_BILL + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_CATEGORY} alone will reset the categories of the {@code FrequentExpense} being
        // edited, parsing it together with a valid category results in error
        assertParseFailure(parser, "1" + CATEGORY_DESC_FOOD_BEVERAGE + CATEGORY_DESC_WORK + CATEGORY_EMPTY,
                Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + CATEGORY_DESC_FOOD_BEVERAGE + CATEGORY_EMPTY + CATEGORY_DESC_WORK,
                Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + CATEGORY_EMPTY + CATEGORY_DESC_FOOD_BEVERAGE + CATEGORY_DESC_WORK,
                Category.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_AMOUNT_DESC
                + CATEGORY_DESC_UTILITIES, Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FREQUENT_EXPENSE;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_PHONE_BILL + CATEGORY_DESC_UTILITIES
                + TITLE_DESC_SPOTIFY_SUBSCRIPTION + CATEGORY_DESC_MISCELLANEOUS;

        EditFrequentExpenseCommand.EditFrequentExpenseDescriptor descriptor = new EditFrequentExpenseDescriptorBuilder()
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).withAmount(VALID_AMOUNT_PHONE_BILL)
                .withCategories(VALID_CATEGORY_UTILITIES, VALID_CATEGORY_MISCELLANEOUS).build();
        EditFrequentExpenseCommand expectedCommand = new EditFrequentExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_FREQUENT_EXPENSE;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_PHONE_BILL;

        EditFrequentExpenseCommand.EditFrequentExpenseDescriptor descriptor = new EditFrequentExpenseDescriptorBuilder()
                .withAmount(VALID_AMOUNT_PHONE_BILL).build();
        EditFrequentExpenseCommand expectedCommand = new EditFrequentExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD_FREQUENT_EXPENSE;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_SPOTIFY_SUBSCRIPTION;
        EditFrequentExpenseCommand.EditFrequentExpenseDescriptor descriptor = new EditFrequentExpenseDescriptorBuilder()
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).build();
        EditFrequentExpenseCommand expectedCommand = new EditFrequentExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amounts
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_PHONE_BILL;
        descriptor = new EditFrequentExpenseDescriptorBuilder().withAmount(VALID_AMOUNT_PHONE_BILL).build();
        expectedCommand = new EditFrequentExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // categories
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_UTILITIES;
        descriptor = new EditFrequentExpenseDescriptorBuilder().withCategories(VALID_CATEGORY_UTILITIES).build();
        expectedCommand = new EditFrequentExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetCategories_success() {
        Index targetIndex = INDEX_THIRD_FREQUENT_EXPENSE;
        String userInput = targetIndex.getOneBased() + CATEGORY_EMPTY;

        EditFrequentExpenseCommand.EditFrequentExpenseDescriptor descriptor =
                new EditFrequentExpenseDescriptorBuilder().withCategories().build();
        EditFrequentExpenseCommand expectedCommand = new EditFrequentExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
