package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;

public class AddFrequentExpenseCommand extends Command {
    public static final String COMMAND_WORD = "addfrequent-expense";
    public static final String COMMAND_ALIAS = "addfe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the finance tracker. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Phone Bill "
            + PREFIX_AMOUNT + "24 "
            + PREFIX_DATE + "03/10/2020 "
            + PREFIX_CATEGORY + "Utilities";

    public static final String MESSAGE_SUCCESS = "New frequent expense added: %1$s";

    private final FrequentExpense toAdd;

    /**
     * Creates an AddFrequentExpenseCommand to add the specified {@code FrequentExpense}
     */
    public AddFrequentExpenseCommand(FrequentExpense frequentExpense) {
        requireNonNull(frequentExpense);
        toAdd = frequentExpense;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.addFrequentExpense(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
