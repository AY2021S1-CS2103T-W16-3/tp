package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DATE_DESC_BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DATE_DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE_TO;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.ConvertBookmarkCommandParser;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;

public class ConvertBookmarkCommandParserTest {
    private static final String CURRENT_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertBookmarkCommand.MESSAGE_USAGE);

    private ConvertBookmarkCommandParser parser = new ConvertBookmarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DATE_DESC_SPOTIFY_SUBSCRIPTION, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DATE_DESC_SPOTIFY_SUBSCRIPTION, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DATE_DESC_SPOTIFY_SUBSCRIPTION, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);

        // multiple dates
        assertParseFailure(parser, INDEX_SECOND + DATE_DESC_SPOTIFY_SUBSCRIPTION
                        + DATE_DESC_BUBBLE_TEA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertBookmarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        Date convertedDate = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);

        String userInput = targetIndex.getOneBased() + DATE_DESC_SPOTIFY_SUBSCRIPTION;

        ConvertBookmarkCommand expectedConvertBookmarkCommand =
                new ConvertBookmarkCommand(targetIndex, convertedDate);

        assertParseSuccess(parser, userInput, expectedConvertBookmarkCommand);
    }

    @Test
    public void parse_optionalDateFieldMissing_success() {
        Index targetIndex = INDEX_SECOND;
        Date convertedDate = new Date(CURRENT_DATE);

        String userInput = String.valueOf(targetIndex.getOneBased());

        ConvertBookmarkCommand expectedConvertBookmarkCommand =
                new ConvertBookmarkCommand(targetIndex, convertedDate);

        assertParseSuccess(parser, userInput, expectedConvertBookmarkCommand);
    }

    @Test
    public void parse_moreThanOneDateFieldSpecified_throwParseException() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + DATE_DESC_SPOTIFY_SUBSCRIPTION + DATE_DESC_SPOTIFY_SUBSCRIPTION;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Transaction.MESSAGE_DATE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidFieldSpecified_throwParseException() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PREFIX_DATE_TO.toString() + VALID_DATE_SPOTIFY_SUBSCRIPTION;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ConvertBookmarkCommand.MESSAGE_USAGE));
    }
}
