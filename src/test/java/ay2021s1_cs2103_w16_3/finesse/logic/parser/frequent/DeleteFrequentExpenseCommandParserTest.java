package ay2021s1_cs2103_w16_3.finesse.logic.parser.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.DeleteFrequentExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.frequentparsers.DeleteFrequentExpenseCommandParser;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteFrequentExpenseCommandParserTest {
    private DeleteFrequentExpenseCommandParser parser = new DeleteFrequentExpenseCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteFrequentExpenseCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteFrequentExpenseCommand.MESSAGE_USAGE));
    }
}
