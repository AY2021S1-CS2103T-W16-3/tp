package ay2021s1_cs2103_w16_3.finesse.ui;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.logic.Logic;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.ui.tabs.AnalyticsTabPane;
import ay2021s1_cs2103_w16_3.finesse.ui.tabs.ExpenseTabPane;
import ay2021s1_cs2103_w16_3.finesse.ui.tabs.IncomeTabPane;
import ay2021s1_cs2103_w16_3.finesse.ui.tabs.OverviewTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private static final String WELCOME_MESSAGE = "Welcome to Fine$$e - your personal finance tracker."
            + "\nPlease enter the command \"help\" to view the user guide on the various commands you can use.";
    private static final String USERGUIDE_URL = "https://ay2021s1-cs2103t-w16-3.github.io/tp/UserGuide.html";
    private static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL + "."
            + "\nPlease copy the url and paste it in your favourite browser to view all valid commands.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Logic logic;
    private final UiState uiState;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;

    @FXML
    private StackPane commandBoxPlaceholder;
    @FXML
    private Button commandBoxButton;
    @FXML
    private Button helpButton;
    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private StackPane statusbarPlaceholder;
    @FXML
    private Tab overviewTab;
    @FXML
    private Tab incomeTab;
    @FXML
    private Tab expenseTab;
    @FXML
    private Tab analyticsTab;
    @FXML
    private TabPane tabPane;
    @FXML
    private Text expenseLimit;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage}, {@code Logic} and {@code UiState}.
     */
    public MainWindow(Stage primaryStage, Logic logic, UiState uiState) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.uiState = uiState;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplay.setFeedbackToUser(WELCOME_MESSAGE);
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFinanceTrackerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Initialize the contents of each tab.
     */
    public void initializeTabs() {
        OverviewTabPane overviewTabPane =
                new OverviewTabPane(logic.getFilteredTransactionList(), logic.getMonthlyBudget());
        overviewTab.setContent(overviewTabPane.getRoot());

        IncomeTabPane incomeTabPane =
                new IncomeTabPane(logic.getFilteredIncomeList(), logic.getFilteredFrequentIncomeList());
        incomeTab.setContent(incomeTabPane.getRoot());

        ExpenseTabPane expenseTabPane =
                new ExpenseTabPane(logic.getFilteredExpenseList(), logic.getFilteredFrequentExpenseList());
        expenseTab.setContent(expenseTabPane.getRoot());

        AnalyticsTabPane analyticsTabPane = new AnalyticsTabPane(logic.getMonthlyBudget());
        analyticsTab.setContent(analyticsTabPane.getRoot());

        SelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(overviewTab);

        // Set the bottom anchor after the tabs have been initialized.
        AnchorPane.setBottomAnchor(tabPane, 0.0);

        // Update UI state on tab change.
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldTabIndex, newTabIndex) ->
                uiState.setCurrentTab(UiState.Tab.values()[newTabIndex.intValue()]));
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Displays the help message in the result display.
     */
    @FXML
    public void handleHelp() {
        resultDisplay.setFeedbackToUser(HELP_MESSAGE);
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see ay2021s1_cs2103_w16_3.finesse.logic.Logic#execute(String, UiState)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText, uiState);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isCalculateBudgetInfo()) {
                logic.calculateBudgetInfo();
            }

            Optional<UiState.Tab> tabToSwitchTo = commandResult.getTabToSwitchTo();
            tabToSwitchTo.ifPresent(this::switchTabs);

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Programmatically switches UI tab based on the specified tab.
     *
     * @param tab The tab to switch to.
     * @throws NullPointerException If the tab is {@code null}.
     */
    private void switchTabs(UiState.Tab tab) {
        requireNonNull(tab);
        tabPane.getSelectionModel().select(tab.getTabIndex().getZeroBased());
        if (tab.equals(UiState.Tab.OVERVIEW)) {
            logic.calculateBudgetInfo();
        }
    }
}
