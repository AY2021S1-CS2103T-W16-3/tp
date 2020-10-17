package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Edits an income identified using its displayed index from the finance tracker.
 */
public class EditIncomeCommand extends EditCommand {

    public static final String MESSAGE_EDIT_INCOME_SUCCESS = "Edited Income: %1$s";

    public EditIncomeCommand(EditCommand superCommand) {
        super(superCommand.getTargetIndex(), superCommand.getEditTransactionDescriptor());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Income> lastShownList = model.getFilteredIncomeList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INCOME_DISPLAYED_INDEX);
        }

        Income incomeToEdit = lastShownList.get(getTargetIndex().getZeroBased());
        Income editedIncome = createEditedIncome(incomeToEdit, getEditTransactionDescriptor());

        model.setTransaction(incomeToEdit, editedIncome);
        model.updateFilteredIncomeList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_INCOME_SUCCESS, editedIncome));
    }

    /**
     * Creates and returns an {@code Income} with the details of {@code incomeToEdit}
     * edited with {@code editIncomeDescriptor}.
     */
    private static Income createEditedIncome(Income incomeToEdit,
                                               EditTransactionDescriptor editIncomeDescriptor) {
        assert incomeToEdit != null;

        Title updatedTitle = editIncomeDescriptor.getTitle().orElse(incomeToEdit.getTitle());
        Amount updatedAmount = editIncomeDescriptor.getAmount().orElse(incomeToEdit.getAmount());
        Date updatedDate = editIncomeDescriptor.getDate().orElse(incomeToEdit.getDate());
        Set<Category> updatedCategories = editIncomeDescriptor.getCategories()
                .orElse(incomeToEdit.getCategories());

        return new Income(updatedTitle, updatedAmount, updatedDate, updatedCategories);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditIncomeCommand)) {
            return false;
        }

        // state check
        EditIncomeCommand e = (EditIncomeCommand) other;
        return getTargetIndex().equals(e.getTargetIndex())
                && getEditTransactionDescriptor().equals(e.getEditTransactionDescriptor());
    }
}
