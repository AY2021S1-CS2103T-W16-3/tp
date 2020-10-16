package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.*;

/**
 * Edits an expense identified using its displayed index from the finance tracker.
 */
public class EditExpenseCommand extends EditCommand {

    public static final String MESSAGE_EDIT_EXPENSE_SUCCESS = "Edited Expense: %1$s";

    public EditExpenseCommand(EditCommand superCommand) {
        super(superCommand.getTargetIndex(), superCommand.getEditTransactionDescriptor());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredExpenseList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense expenseToEdit = (Expense) lastShownList.get(getTargetIndex().getZeroBased());
        Expense editedExpense = createEditedExpense(expenseToEdit, getEditTransactionDescriptor());

        model.setTransaction(expenseToEdit, editedExpense);
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense));
    }

    /**
     * Creates and returns an {@code Expense} with the details of {@code expenseToEdit}
     * edited with {@code editExpenseDescriptor}.
     */
    private static Expense createEditedExpense(Expense expenseToEdit,
                                               EditTransactionDescriptor editExpenseDescriptor) {
        assert expenseToEdit != null;

        Title updatedTitle = editExpenseDescriptor.getTitle().orElse(expenseToEdit.getTitle());
        Amount updatedAmount = editExpenseDescriptor.getAmount().orElse(expenseToEdit.getAmount());
        Date updatedDate = editExpenseDescriptor.getDate().orElse(expenseToEdit.getDate());
        Set<Category> updatedCategories = editExpenseDescriptor.getCategories()
                .orElse(expenseToEdit.getCategories());

        return new Expense(updatedTitle, updatedAmount, updatedDate, updatedCategories);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExpenseCommand)) {
            return false;
        }

        // state check
        EditExpenseCommand e = (EditExpenseCommand) other;
        return getTargetIndex().equals(e.getTargetIndex())
                && getEditTransactionDescriptor().equals(e.getEditTransactionDescriptor());
    }
}
