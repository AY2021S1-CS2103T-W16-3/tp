package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_TAB_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ClearCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ExitCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.HelpCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;

/**
 * Parses user input.
 */
public class FinanceTrackerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput Full user input string.
     * @param uiState Current state of the UI.
     * @return The command based on the user input.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput, UiState uiState) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddExpenseCommand.COMMAND_WORD:
        case AddExpenseCommand.COMMAND_ALIAS:
            return new AddExpenseCommandParser().parse(arguments);

        case AddIncomeCommand.COMMAND_WORD:
        case AddIncomeCommand.COMMAND_ALIAS:
            return new AddIncomeCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            final DeleteCommand baseCommand = new DeleteCommandParser().parse(arguments);
            switch (uiState.getCurrentTab()) {
            case EXPENSES:
                return new DeleteExpenseCommand(baseCommand);
            case INCOME:
                return new DeleteIncomeCommand(baseCommand);
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        UiState.Tab.EXPENSES, UiState.Tab.INCOME));
            }

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListExpenseCommand.COMMAND_WORD:
        case ListExpenseCommand.COMMAND_ALIAS:
            return new ListExpenseCommand();

        case ListIncomeCommand.COMMAND_WORD:
        case ListIncomeCommand.COMMAND_ALIAS:
            return new ListIncomeCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private String commandInvalidTabMessage(String command, UiState.Tab... tabs) {
        return String.format(MESSAGE_INVALID_TAB_FORMAT, command,
                Stream.of(tabs).map(UiState.Tab::toString).collect(Collectors.joining(", ")));
    }

}
