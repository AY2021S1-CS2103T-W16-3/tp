package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INCOMES_LISTED_OVERVIEW;
import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.model.Model;

/**
 * Finds and lists all incomes in the finance tracker whose title contains any of the argument keywords.
 * Used when the user is on the Income tab.
 * Keyword matching is case insensitive.
 */
public class FindIncomeCommand extends FindCommand {

    public FindIncomeCommand(FindCommand superCommand) {
        super(superCommand.getPredicate());
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredIncomeList(getPredicate());
        return new CommandResult(
                String.format(MESSAGE_INCOMES_LISTED_OVERVIEW,
                        model.getFilteredIncomeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindIncomeCommand // instanceof handles nulls
                && getPredicate().equals(((FindIncomeCommand) other).getPredicate())); // state check
    }
}
