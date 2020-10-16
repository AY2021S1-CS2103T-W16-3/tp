package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;

/**
 * Deletes an expense identified using its displayed index from the finance tracker.
 */
public class DeleteExpenseCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_EXPENSE_SUCCESS = "Deleted Expense: %1$s";

    public DeleteExpenseCommand(DeleteCommand superCommand) {
        super(superCommand.getTargetIndex());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenseList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense expenseToDelete = lastShownList.get(getTargetIndex().getZeroBased());
        model.deleteExpense(expenseToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteExpenseCommand // instanceof handles nulls
                && getTargetIndex().equals(((DeleteExpenseCommand) other).getTargetIndex())); // state check
    }
}
