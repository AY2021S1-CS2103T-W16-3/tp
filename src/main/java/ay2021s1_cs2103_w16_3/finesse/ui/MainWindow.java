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
import ay2021s1_cs2103_w16_3.finesse.ui.expense.ExpensePanel;
import ay2021s1_cs2103_w16_3.finesse.ui.frequent.FrequentExpensePanel;
import ay2021s1_cs2103_w16_3.finesse.ui.income.IncomePanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
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
    private TransactionListPanel transactionListPanel;
    private FrequentExpensePanel frequentExpensePanel;
    private IncomePanel incomePanel;
    private ExpensePanel expensePanel;
    private ResultDisplay resultDisplay;
    private SavingsGoalPanel savingsGoalPanel;

    @FXML
    private StackPane commandBoxPlaceholder;
    @FXML
    private Button commandBoxButton;
    @FXML
    private StackPane transactionListPanelPlaceholder;
    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private StackPane savingsGoalPlaceholder;
    @FXML
    private StackPane statusbarPlaceholder;
    @FXML
    private Label panelLabel;
    @FXML
    private Label rightPanelLabel;
    @FXML
    private Tab menuHelpTab;
    @FXML
    private Tab menuOverviewTab;
    @FXML
    private Tab menuIncomeTab;
    @FXML
    private Tab menuExpenseTab;
    @FXML
    private Tab menuAnalyticsTab;
    @FXML
    private TabPane tabPane;

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

    public void disableStageResizing() {
        this.primaryStage.setResizable(false);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        incomePanel = new IncomePanel(logic.getFilteredIncomeList());
        transactionListPanelPlaceholder.getChildren().add(incomePanel.getRoot());

        expensePanel = new ExpensePanel(logic.getFilteredExpenseList());
        transactionListPanelPlaceholder.getChildren().add(expensePanel.getRoot());

        frequentExpensePanel = new FrequentExpensePanel(logic.getFilteredFrequentExpenseList());
        savingsGoalPlaceholder.getChildren().add(frequentExpensePanel.getRoot());

        transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        savingsGoalPanel = new SavingsGoalPanel();
        savingsGoalPlaceholder.getChildren().add(savingsGoalPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplay.setFeedbackToUser(WELCOME_MESSAGE);
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFinanceTrackerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        SelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(menuOverviewTab);

        onOverview();
    }

    /**
     * Sets up all the action handlers for the tabs on the tab pane.
     */
    public void setActionHandlers() {
        menuOverviewTab.setOnSelectionChanged(event -> {
            handleOverview();
        });

        menuHelpTab.setOnSelectionChanged(event -> {
            handleTabHelp();
        });

        menuIncomeTab.setOnSelectionChanged(event -> {
            handleIncome();
        });

        menuExpenseTab.setOnSelectionChanged(event -> {
            handleExpense();
        });

        menuAnalyticsTab.setOnSelectionChanged(event -> {
            handleAnalytics();
        });
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
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleTabHelp() {
        if (menuHelpTab.isSelected()) {
            resultDisplay.setFeedbackToUser(HELP_MESSAGE);
        }
    }

    @FXML void handleCommandHelp() {
        resultDisplay.setFeedbackToUser(HELP_MESSAGE);
    }

    /**
     * Opens the income window.
     */
    @FXML
    private void handleIncome() {
        if (menuIncomeTab.isSelected()) {
            panelLabel.setText("Income");

            savingsGoalPanel = new SavingsGoalPanel();
            savingsGoalPlaceholder.getChildren().add(savingsGoalPanel.getRoot());

            incomePanel = new IncomePanel(logic.getFilteredIncomeList());
            transactionListPanelPlaceholder.getChildren().add(incomePanel.getRoot());
            incomePanel.getRoot().toFront();
        }
        onIncome();
        uiState.setCurrentTab(UiState.Tab.INCOME);
    }

    /**
     * Opens the overview window.
     */
    @FXML
    private void handleOverview() {
        if (menuOverviewTab.isSelected()) {
            panelLabel.setText("Overview");
            rightPanelLabel.setText("Savings Summary");

            transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
            transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());
            transactionListPanel.getRoot().toFront();

            savingsGoalPanel = new SavingsGoalPanel();
            savingsGoalPlaceholder.getChildren().add(savingsGoalPanel.getRoot());
        }
        onOverview();
        uiState.setCurrentTab(UiState.Tab.OVERVIEW);
    }

    /**
     * Opens the analytics window.
     */
    @FXML
    private void handleAnalytics() {
        if (menuAnalyticsTab.isSelected()) {
            panelLabel.setText("Analytics");
            transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
            transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());
            transactionListPanel.getRoot().toFront();
        }
        onAnalytics();
        uiState.setCurrentTab(UiState.Tab.ANALYTICS);
    }

    /**
     * Opens the expense window.
     */
    @FXML
    private void handleExpense() {
        if (menuExpenseTab.isSelected()) {
            panelLabel.setText("Expense");
            rightPanelLabel.setText("Frequent Expenses");

            expensePanel = new ExpensePanel(logic.getFilteredExpenseList());
            transactionListPanelPlaceholder.getChildren().add(expensePanel.getRoot());
            expensePanel.getRoot().toFront();

            frequentExpensePanel = new FrequentExpensePanel((logic.getFilteredFrequentExpenseList()));
            savingsGoalPlaceholder.getChildren().add(frequentExpensePanel.getRoot());
        }
        onExpense();
        uiState.setCurrentTab(UiState.Tab.EXPENSES);
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

    public TransactionListPanel getTransactionListPanel() {
        return transactionListPanel;
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
                handleCommandHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
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
        tabPane.getSelectionModel().select(tab.getTabIndex());
    }

    /**
     * Changes the text color of the overview tab to white while the rest remains grey
     */
    private void onOverview() {
        menuOverviewTab.setStyle("-tab-text-color: white");
        menuIncomeTab.setStyle("-tab-text-color: #888888");
        menuExpenseTab.setStyle("-tab-text-color: #888888");
        menuAnalyticsTab.setStyle("-tab-text-color: #888888");
        menuHelpTab.setStyle("-tab-text-color: #888888");
    }

    /**
     * Changes the text color of the income tab to white while the rest remains grey.
     */
    private void onIncome() {
        menuOverviewTab.setStyle("-tab-text-color: #888888");
        menuIncomeTab.setStyle("-tab-text-color: white");
        menuExpenseTab.setStyle("-tab-text-color: #888888");
        menuAnalyticsTab.setStyle("-tab-text-color: #888888");
        menuHelpTab.setStyle("-tab-text-color: #888888");
    }

    /**
     * Changes the text color of the expense tab to white while the rest remains grey.
     */
    private void onExpense() {
        menuOverviewTab.setStyle("-tab-text-color: #888888");
        menuIncomeTab.setStyle("-tab-text-color: #888888");
        menuExpenseTab.setStyle("-tab-text-color: white");
        menuAnalyticsTab.setStyle("-tab-text-color: #888888");
        menuHelpTab.setStyle("-tab-text-color: #888888");
    }

    /**
     * Changes the text color of the analytics tab to white while the rest remains grey.
     */
    private void onAnalytics() {
        menuOverviewTab.setStyle("-tab-text-color: #888888");
        menuIncomeTab.setStyle("-tab-text-color: #888888");
        menuExpenseTab.setStyle("-tab-text-color: #888888");
        menuAnalyticsTab.setStyle("-tab-text-color: white");
        menuHelpTab.setStyle("-tab-text-color: #888888");
    }
}
