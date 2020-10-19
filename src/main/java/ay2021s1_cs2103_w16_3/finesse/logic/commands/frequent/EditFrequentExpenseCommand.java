package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static ay2021s1_cs2103_w16_3.finesse.model.Model.PREDICATE_SHOW_ALL_FREQUENT_EXPENSES;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.commons.util.CollectionUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

/**
 * Edits the details of an existing frequent expense using its displayed index from the frequent expense list
 * in the expense tab.
 */
public class EditFrequentExpenseCommand extends Command {
    public static final String COMMAND_WORD = "edit-frequent-expense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the frequent expense identified "
            + "by the index number used in the displayed frequent expense list on the expense tab. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5 ";

    public static final String MESSAGE_EDIT_FREQUENT_EXPENSE_SUCCESS = "Edited Frequent Expense: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index targetIndex;
    private final EditFrequentExpenseDescriptor editFrequentExpenseDescriptor;

    /**
     * @param targetIndex Index of the frequent expense in the filtered frequent expense list to edit.
     * @param editFrequentExpenseDescriptor Details to edit the frequent expense with.
     */
    public EditFrequentExpenseCommand(Index targetIndex, EditFrequentExpenseDescriptor editFrequentExpenseDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editFrequentExpenseDescriptor);

        this.targetIndex = targetIndex;
        this.editFrequentExpenseDescriptor = editFrequentExpenseDescriptor;
    }

    protected Index getTargetIndex() {
        return targetIndex;
    }

    protected EditFrequentExpenseDescriptor getEditFrequentExpenseDescriptor() {
        return editFrequentExpenseDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<FrequentExpense> lastShownList = model.getFilteredFrequentExpenseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX);
        }

        FrequentExpense frequentExpenseToEdit = lastShownList.get(targetIndex.getZeroBased());
        FrequentExpense editedFrequentExpense = createdEditedFrequentExpense(frequentExpenseToEdit,
                editFrequentExpenseDescriptor);

        model.setFrequentExpense(frequentExpenseToEdit, editedFrequentExpense);
        model.updateFilteredFrequentExpenseList(PREDICATE_SHOW_ALL_FREQUENT_EXPENSES);
        return new CommandResult(String.format(MESSAGE_EDIT_FREQUENT_EXPENSE_SUCCESS, editedFrequentExpense));
    }

    /**
     * Creates and returns a {@code FrequentExpense} with the details of {@code frequentExpenseToEdit}
     * edited with {@code editFrequentExpenseDescriptor}.
     */
    private static FrequentExpense createdEditedFrequentExpense(FrequentExpense frequentExpenseToEdit,
                                                       EditFrequentExpenseDescriptor editFrequentExpenseDescriptor) {
        assert frequentExpenseToEdit != null;

        Title updatedTitle = editFrequentExpenseDescriptor.getTitle().orElse(frequentExpenseToEdit.getTitle());
        Amount updatedAmount = editFrequentExpenseDescriptor.getAmount().orElse(frequentExpenseToEdit.getAmount());
        Set<Category> updatedCategories = editFrequentExpenseDescriptor.getCategories()
                .orElse(frequentExpenseToEdit.getCategories());

        return new FrequentExpense(updatedTitle, updatedAmount, updatedCategories);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditFrequentExpenseCommand)) {
            return false;
        }

        // state check
        EditFrequentExpenseCommand e = (EditFrequentExpenseCommand) other;
        return targetIndex.equals(e.targetIndex)
                && editFrequentExpenseDescriptor.equals(e.editFrequentExpenseDescriptor);
    }

    /**
     * Stores the details to edit the frequent expense with. Each non-empty field value will replace the
     * corresponding field value of the frequent expense.
     */
    public static class EditFrequentExpenseDescriptor {
        private Title title;
        private Amount amount;
        private Set<Category> categories;

        public EditFrequentExpenseDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code categories} is used internally.
         */
        public EditFrequentExpenseDescriptor(EditFrequentExpenseDescriptor toCopy) {
            setTitle(toCopy.title);
            setAmount(toCopy.amount);
            setCategories(toCopy.categories);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, amount, categories);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setCategories(Set<Category> categories) {
            this.categories = (categories != null) ? new HashSet<>(categories) : null;
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Optional<Set<Category>> getCategories() {
            return (categories != null) ? Optional.of(Collections.unmodifiableSet(categories)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFrequentExpenseDescriptor)) {
                return false;
            }

            // state check
            EditFrequentExpenseDescriptor e = (EditFrequentExpenseDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getAmount().equals(e.getAmount())
                    && getCategories().equals(e.getCategories());
        }
    }


}
