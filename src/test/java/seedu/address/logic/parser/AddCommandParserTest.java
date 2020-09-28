package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTransactions.AMY;
import static seedu.address.testutil.TypicalTransactions.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Amount;
import seedu.address.model.person.Date;
import seedu.address.model.person.Name;
import seedu.address.model.person.Transaction;
import seedu.address.model.tag.Category;
import seedu.address.testutil.TransactionBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Transaction expectedTransaction = new TransactionBuilder(BOB).withCategories(VALID_CATEGORY_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + CATEGORY_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + CATEGORY_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple amounts - last amount accepted
        assertParseSuccess(parser, NAME_DESC_BOB + AMOUNT_DESC_AMY + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + CATEGORY_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple dates - last date accepted
        assertParseSuccess(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_AMY + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + CATEGORY_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + CATEGORY_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple categories - all accepted
        Transaction expectedTransactionMultipleTags = new TransactionBuilder(BOB)
                .withCategories(VALID_CATEGORY_FRIEND, VALID_CATEGORY_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB
                + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND, new AddCommand(expectedTransactionMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero categories
        Transaction expectedTransaction = new TransactionBuilder(AMY).withCategories().build();
        assertParseSuccess(parser, NAME_DESC_AMY + AMOUNT_DESC_AMY + DATE_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedTransaction));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_AMOUNT_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + VALID_DATE_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_AMOUNT_BOB + VALID_DATE_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB
                + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_AMOUNT_DESC + DATE_DESC_BOB + ADDRESS_DESC_BOB
                + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND, Amount.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + INVALID_DATE_DESC + ADDRESS_DESC_BOB
                + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND, Date.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB + INVALID_ADDRESS_DESC
                + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_CATEGORY_DESC + VALID_CATEGORY_FRIEND, Category.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_BOB + DATE_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
