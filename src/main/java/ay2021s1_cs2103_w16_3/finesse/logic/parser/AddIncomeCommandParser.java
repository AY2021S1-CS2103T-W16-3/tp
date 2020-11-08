package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

/**
 * Parses input arguments and creates a new AddIncomeCommand object
 */
public class AddIncomeCommandParser implements Parser<AddIncomeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddIncomeCommand
     * and returns an AddIncomeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddIncomeCommand parse(String args) throws ParseException {
        TransactionBuilder transactionBuilder = ParserUtil.parseTransactionBuilder(args,
                AddIncomeCommand.MESSAGE_USAGE);
        Income income = transactionBuilder.buildIncome();
        return new AddIncomeCommand(income);
    }

}
