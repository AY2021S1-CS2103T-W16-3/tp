package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.model.Model;

/**
 * Finds and lists all transactions in the finance tracker whose title contains any of the argument keywords.
 * Used when the user is on the Overview tab.
 * Keyword matching is case insensitive.
 */
public class FindTransactionCommand extends FindCommand {

    public FindTransactionCommand(FindCommand superCommand) {
        super(superCommand.getPredicate());
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTransactionList(getPredicate());
        return new CommandResult(
                String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTransactionCommand // instanceof handles nulls
                && getPredicate().equals(((FindTransactionCommand) other).getPredicate())); // state check
    }
}
