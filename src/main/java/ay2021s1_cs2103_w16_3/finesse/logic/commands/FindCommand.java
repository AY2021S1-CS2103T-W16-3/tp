package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT_FROM;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT_TO;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE_FROM;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE_TO;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;
import java.util.function.Predicate;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Finds and lists all transactions in the finance tracker whose title contains any of the argument keywords
 * depending on the tab the user is on.
 * Keyword matching is case insensitive.
 *
 * Base class for FindExpenseCommand, FindIncomeCommand and FindTransactionCommand.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all transactions on the current tab "
            + "that match any of the specified search parameters "
            + "and displays them as a list with index numbers.\n"
            + "When on Overview tab: Searches all transactions.\n"
            + "When on Income tab: Searches all incomes.\n"
            + "When on Expenses tab: Searches all expenses.\n"
            + "Parameters: [" + PREFIX_TITLE + "TITLE_KEYPHRASE...] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_CATEGORY + "CATEGORY...] "
            + "[" + PREFIX_AMOUNT_FROM + "AMOUNT_FROM] "
            + "[" + PREFIX_AMOUNT_TO + "AMOUNT_TO] "
            + "[" + PREFIX_DATE_FROM + "DATE_FROM] "
            + "[" + PREFIX_DATE_TO + "DATE_TO]\n"
            + "Please refer to the user guide for more details on each search parameter.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Bubble Tea "
            + PREFIX_AMOUNT_FROM + "5 "
            + PREFIX_DATE_FROM + "01/10/2020 "
            + PREFIX_DATE_TO + "31/10/2020";

    private final List<Predicate<Transaction>> predicates;

    public FindCommand(List<Predicate<Transaction>> predicates) {
        this.predicates = predicates;
    }

    protected List<Predicate<Transaction>> getPredicates() {
        return predicates;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicates.equals(((FindCommand) other).predicates)); // state check
    }
}
