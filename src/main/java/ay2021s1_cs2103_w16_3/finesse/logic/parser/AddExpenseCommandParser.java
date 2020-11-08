package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;

/**
 * Parses input arguments and creates a new AddExpenseCommand object
 */
public class AddExpenseCommandParser implements Parser<AddExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddExpenseCommand
     * and returns an AddExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExpenseCommand parse(String args) throws ParseException {
        TransactionBuilder transactionBuilder = ParserUtil.parseTransactionBuilder(args,
                AddExpenseCommand.MESSAGE_USAGE);
        Expense expense = transactionBuilder.buildExpense();
        return new AddExpenseCommand(expense);
    }

}
