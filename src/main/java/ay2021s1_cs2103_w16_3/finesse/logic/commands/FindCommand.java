package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import ay2021s1_cs2103_w16_3.finesse.commons.core.Messages;
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
            + "whose titles contain any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "When on Overview tab: Searches all transactions.\n"
            + "When on Income tab: Searches all incomes.\n"
            + "When on Expenses tab: Searches all expenses.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final List<Predicate<Transaction>> predicates;

    public FindCommand(List<Predicate<Transaction>> predicates) {
        this.predicates = predicates;
    }

    protected List<Predicate<Transaction>> getPredicates() {
        return predicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicates);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicates.equals(((FindCommand) other).predicates)); // state check
    }
}
