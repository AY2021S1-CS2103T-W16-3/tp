package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;

/**
 * Deletes a frequent expense identified using it's displayed index from the finance tracker.
 */
public class DeleteFrequentExpenseCommand extends Command {
    public static final String COMMAND_WORD = "deletefrequent-expense";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the frequent expense identified by the index number used in the displayed "
            + "frequent expense list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_FREQUENT_EXPENSE_SUCCESS = "Deleted Frequent Expense: %1$s";

    private final Index targetIndex;

    public DeleteFrequentExpenseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute (Model model) throws CommandException {
        requireNonNull(model);
        List<FrequentExpense> lastShownList = model.getFilteredFrequentExpenseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        FrequentExpense frequentExpenseToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFrequentExpense(frequentExpenseToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_FREQUENT_EXPENSE_SUCCESS, frequentExpenseToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFrequentExpenseCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteFrequentExpenseCommand) other).targetIndex)); // state check
    }
}
