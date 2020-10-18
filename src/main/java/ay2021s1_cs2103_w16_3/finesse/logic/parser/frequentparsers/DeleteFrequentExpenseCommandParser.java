package ay2021s1_cs2103_w16_3.finesse.logic.parser.frequentparsers;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.DeleteFrequentExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;

/**
 * Parser input arguments and creates a new DeleteFrequentExpenseCommand object
 */
public class DeleteFrequentExpenseCommandParser implements Parser<DeleteFrequentExpenseCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFrequentExpenseCommand
     * and returns a DeleteFrequentExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFrequentExpenseCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteFrequentExpenseCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFrequentExpenseCommand.MESSAGE_USAGE), pe);
        }
    }
}
