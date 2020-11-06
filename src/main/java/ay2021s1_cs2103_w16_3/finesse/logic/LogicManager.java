package ay2021s1_cs2103_w16_3.finesse.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.FinanceTrackerParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.logic.time.Timekeeper;
import ay2021s1_cs2103_w16_3.finesse.logic.time.exceptions.TemporalException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.storage.Storage;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final FinanceTrackerParser financeTrackerParser;
    private final Timekeeper timekeeper;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        financeTrackerParser = new FinanceTrackerParser();
        timekeeper = new Timekeeper();
    }

    @Override
    public CommandResult execute(String commandText, UiState uiState)
            throws CommandException, ParseException, TemporalException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        timekeeper.checkIn();

        CommandResult commandResult;
        Command command = financeTrackerParser.parseCommand(commandText, uiState);
        commandResult = command.execute(model);

        try {
            storage.saveFinanceTracker(model.getFinanceTracker());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public void calculateBudgetInfo() {
        model.calculateBudgetInfo();
    }

    @Override
    public ReadOnlyFinanceTracker getFinanceTracker() {
        return model.getFinanceTracker();
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
    }

    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return model.getFilteredExpenseList();
    }

    @Override
    public ObservableList<Income> getFilteredIncomeList() {
        return model.getFilteredIncomeList();
    }

    @Override
    public ObservableList<BookmarkExpense> getFilteredBookmarkExpenseList() {
        return model.getFilteredBookmarkExpenseList();
    }

    @Override
    public ObservableList<BookmarkIncome> getFilteredBookmarkIncomeList() {
        return model.getFilteredBookmarkIncomeList();
    }

    public MonthlyBudget getMonthlyBudget() {
        return model.getMonthlyBudget();
    }

    @Override
    public Path getFinanceTrackerFilePath() {
        return model.getFinanceTrackerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
