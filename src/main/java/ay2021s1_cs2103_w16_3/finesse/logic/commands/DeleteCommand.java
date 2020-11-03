package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;

/**
 * Deletes a transaction identified using its displayed index from the finance tracker
 * depending on the tab the user is on.
 *
 * Base class for DeleteExpenseCommand and DeleteIncomeCommand.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the transaction identified by the index number used in "
            + "the displayed transaction list on the current tab.\n"
            + "When on Income tab: Deletes from the currently displayed income list.\n"
            + "When on Expenses tab: Deletes from the currently displayed expenses list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Transaction: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    protected Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("This method should not be called.");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
