package ay2021s1_cs2103_w16_3.finesse.logic.parser.budgetparsers;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.budget.SetExpenseLimitCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ArgumentMultimap;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ArgumentTokenizer;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;

/**
 * Parses input arguments and creates a new SetExpenseLimitCommand object.
 */
public class SetExpenseLimitCommandParser implements Parser<SetExpenseLimitCommand> {

    public static final String MESSAGE_CONSTRAINTS = "Only one amount should be input as the expense limit.";
    /**
     * Parses the given {@code String} of arguments in the context of the SetExpenseLimitCommand
     * and returns a SetExpenseLimitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetExpenseLimitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap;
        try {
            argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetExpenseLimitCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_AMOUNT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetExpenseLimitCommand.MESSAGE_USAGE));
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_AMOUNT)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_CONSTRAINTS));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        return new SetExpenseLimitCommand(amount);
    }

}
