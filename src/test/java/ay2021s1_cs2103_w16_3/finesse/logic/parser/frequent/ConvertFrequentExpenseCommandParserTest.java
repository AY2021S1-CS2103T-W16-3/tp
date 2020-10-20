package ay2021s1_cs2103_w16_3.finesse.logic.parser.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DATE_DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND_FREQUENT_EXPENSE;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.ConvertFrequentExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.frequentparsers.ConvertFrequentExpenseCommandParser;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;

public class ConvertFrequentExpenseCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertFrequentExpenseCommand.MESSAGE_USAGE);

    private ConvertFrequentExpenseCommandParser parser = new ConvertFrequentExpenseCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DATE_DESC_SPOTIFY_SUBSCRIPTION, MESSAGE_INVALID_FORMAT);

        // no date specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

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
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FREQUENT_EXPENSE;
        Date convertedDate = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);

        String userInput = targetIndex.getOneBased() + DATE_DESC_SPOTIFY_SUBSCRIPTION;

        ConvertFrequentExpenseCommand expectedConvertFrequentExpenseCommand =
                new ConvertFrequentExpenseCommand(targetIndex, convertedDate);

        assertParseSuccess(parser, userInput, expectedConvertFrequentExpenseCommand);
    }
}
