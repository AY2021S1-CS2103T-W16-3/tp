package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;

/**
 * Converts a specified frequent expense to an expense and adds it to the finance tracker.
 */
public class ConvertFrequentExpenseCommand extends Command {
    public static final String COMMAND_WORD = "convert-frequent-expense";
    public static final String COMMAND_ALIAS = "convertfe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Converts the specified frequent expense and adds"
            + " it as an expense to the finance tracker.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DATE + "03/10/2020 ";

    public static final String MESSAGE_CONVERT_FREQUENT_EXPENSE_SUCCESS = "Frequent expense has been converted "
            + "and successfully added to finance tracker: %1$s";

    private final Index targetIndex;
    private final Date date;

    /**
     * @param targetIndex Index of the frequent expense in the filtered frequent expense list to convert.
     * @param newDate Date of converting frequent expense to an expense and adding it to the finance tracker.
     */
    public ConvertFrequentExpenseCommand(Index targetIndex, Date newDate) {
        this.targetIndex = targetIndex;
        this.date = newDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<FrequentExpense> lastShownList = model.getFilteredFrequentExpenseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FREQUENT_EXPENSE_DISPLAYED_INDEX);
        }

        FrequentExpense frequentExpenseToBeConverted = lastShownList.get(targetIndex.getZeroBased());
        Expense newExpenseToAdd = frequentExpenseToBeConverted.convert(date);
        model.addExpense(newExpenseToAdd);
        return new CommandResult(String.format(MESSAGE_CONVERT_FREQUENT_EXPENSE_SUCCESS, newExpenseToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConvertFrequentExpenseCommand)) {
            return false;
        }

        ConvertFrequentExpenseCommand e = (ConvertFrequentExpenseCommand) other;
        return targetIndex.equals(e.targetIndex) && date.equals(e.date);
    }
}
