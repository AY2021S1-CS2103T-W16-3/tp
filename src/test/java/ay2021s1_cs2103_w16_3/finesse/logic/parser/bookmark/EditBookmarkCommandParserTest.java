package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_FOOD_BEVERAGE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_MISCELLANEOUS;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DATE_DESC_SPOTIFY_SUBSCRIPTION;
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
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.EditBookmarkCommandParser;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditBookmarkTransactionDescriptorBuilder;


public class EditBookmarkCommandParserTest {

    private static final String CATEGORY_EMPTY = " " + PREFIX_CATEGORY;
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookmarkCommand.MESSAGE_USAGE);
    private EditBookmarkCommandParser parser = new EditBookmarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_PHONE_BILL, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditBookmarkCommand.MESSAGE_NOT_EDITED);

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

        // while parsing {@code PREFIX_CATEGORY} alone will reset the categories of the {@code BookmarkExpense} being
        // edited, parsing it together with a valid category results in error
        assertParseFailure(parser, "1" + CATEGORY_DESC_FOOD_BEVERAGE + CATEGORY_DESC_WORK + CATEGORY_EMPTY,
                Category.MESSAGE_EMPTY_CATEGORY);
        assertParseFailure(parser, "1" + CATEGORY_DESC_FOOD_BEVERAGE + CATEGORY_EMPTY + CATEGORY_DESC_WORK,
                Category.MESSAGE_EMPTY_CATEGORY);
        assertParseFailure(parser, "1" + CATEGORY_EMPTY + CATEGORY_DESC_FOOD_BEVERAGE + CATEGORY_DESC_WORK,
                Category.MESSAGE_EMPTY_CATEGORY);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_AMOUNT_DESC
                + CATEGORY_DESC_UTILITIES, Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidFieldPresent_failure() {
        assertParseFailure(parser, "1" + TITLE_DESC_SPOTIFY_SUBSCRIPTION + AMOUNT_DESC_SPOTIFY_SUBSCRIPTION
                + DATE_DESC_SPOTIFY_SUBSCRIPTION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookmarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_PHONE_BILL + CATEGORY_DESC_UTILITIES
                + TITLE_DESC_SPOTIFY_SUBSCRIPTION + CATEGORY_DESC_MISCELLANEOUS;

        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).withAmount(VALID_AMOUNT_PHONE_BILL)
                .withCategories(VALID_CATEGORY_UTILITIES, VALID_CATEGORY_MISCELLANEOUS).build();
        EditBookmarkCommand expectedCommand = new EditBookmarkCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_PHONE_BILL;

        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder()
                .withAmount(VALID_AMOUNT_PHONE_BILL).build();
        EditBookmarkCommand expectedCommand = new EditBookmarkCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_SPOTIFY_SUBSCRIPTION;
        System.out.println(userInput);
        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).build();
        EditBookmarkCommand expectedCommand = new EditBookmarkCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amounts
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_PHONE_BILL;
        descriptor = new EditBookmarkTransactionDescriptorBuilder().withAmount(VALID_AMOUNT_PHONE_BILL).build();
        expectedCommand = new EditBookmarkCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // categories
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_UTILITIES;
        descriptor = new EditBookmarkTransactionDescriptorBuilder().withCategories(VALID_CATEGORY_UTILITIES).build();
        expectedCommand = new EditBookmarkCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // multiple titles
        assertParseFailure(parser, "1" + TITLE_DESC_PHONE_BILL + TITLE_DESC_SPOTIFY_SUBSCRIPTION
                        + AMOUNT_DESC_PHONE_BILL + CATEGORY_DESC_UTILITIES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkTransaction.MESSAGE_TITLE_CONSTRAINTS));

        // multiple amounts
        assertParseFailure(parser, "1" + TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL
                        + AMOUNT_DESC_SPOTIFY_SUBSCRIPTION + CATEGORY_DESC_UTILITIES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkTransaction.MESSAGE_AMOUNT_CONSTRAINTS));
    }

    @Test
    public void parse_resetCategories_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + CATEGORY_EMPTY;

        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder().withCategories()
                .build();
        EditBookmarkCommand expectedCommand = new EditBookmarkCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
