package ay2021s1_cs2103_w16_3.finesse.ui;

import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.logic.Logic;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.ui.expense.ExpensePanel;
import ay2021s1_cs2103_w16_3.finesse.ui.income.IncomePanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TransactionListPanel transactionListPanel;
    private IncomePanel incomePanel;
    private ExpensePanel expensePanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem incomeTabItem;

    @FXML
    private MenuItem expenseTabItem;

    @FXML
    private MenuItem analyticsTabItem;

    @FXML
    private MenuItem overviewTabItem;

    @FXML
    private StackPane transactionListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Menu menuHelpTab;

    @FXML
    private Menu menuIncomeTab;

    @FXML
    private Menu menuExpenseTab;

    @FXML
    private Menu menuOverviewTab;

    @FXML
    private Menu menuAnalyticsTab;

    @FXML
    private Label panelLabel;


    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        incomePanel = new IncomePanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(incomePanel.getRoot());

        expensePanel = new ExpensePanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(expensePanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFinanceTrackerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        onOverview();
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
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the income window.
     */
    @FXML
    private void handleIncome() {
        panelLabel.setText("Income");
        incomePanel = new IncomePanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(incomePanel.getRoot());
        incomePanel.getRoot().toFront();

        onIncome();
    }

    /**
     * Opens the overview window.
     */
    @FXML
    private void handleOverview() {
        panelLabel.setText("Overview");
        transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());
        transactionListPanel.getRoot().toFront();

        onOverview();
    }

    /**
     * Opens the analytics window
     */
    @FXML
    private void handleAnalytics() {
        panelLabel.setText("Analytics");
        transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());
        transactionListPanel.getRoot().toFront();

        onAnalytics();
    }

    /**
     * Opens the expense window.
     */
    @FXML
    private void handleExpense() {
        panelLabel.setText("Expense");
        expensePanel = new ExpensePanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(expensePanel.getRoot());
        expensePanel.getRoot().toFront();

        onExpense();
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
        helpWindow.hide();
        primaryStage.hide();
    }

    public TransactionListPanel getTransactionListPanel() {
        return transactionListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see ay2021s1_cs2103_w16_3.finesse.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Changes the overview menu tab to blue while the rest becomes the same as the background color
     */
    private void onOverview() {
        menuOverviewTab.setStyle("-fx-background-color: #3e7b91");
        menuIncomeTab.setStyle("-fx-background-color: #2E2E36");
        menuExpenseTab.setStyle("-fx-background-color: #2E2E36");
        menuAnalyticsTab.setStyle("-fx-background-color: #2E2E36");
    }

    /**
     * Changes the income menu tab to blue while the rest becomes the same as the background color
     */
    private void onIncome() {
        menuOverviewTab.setStyle("-fx-background-color: #2E2E36");
        menuIncomeTab.setStyle("-fx-background-color: #3e7b91");
        menuExpenseTab.setStyle("-fx-background-color: #2E2E36");
        menuAnalyticsTab.setStyle("-fx-background-color: #2E2E36");
    }

    /**
     * Changes the expense menu tab to blue while the rest becomes the same as the background color
     */
    private void onExpense() {
        menuOverviewTab.setStyle("-fx-background-color: #2E2E36");
        menuIncomeTab.setStyle("-fx-background-color: #2E2E36");
        menuExpenseTab.setStyle("-fx-background-color: #3e7b91");
        menuAnalyticsTab.setStyle("-fx-background-color: #2E2E36");
        menuHelpTab.setStyle("-fx-background-color: #2E2E36");
    }

    /**
     * Changes the analytics menu tab to blue while the rest becomes the same as the background color
     */
    private void onAnalytics() {
        menuOverviewTab.setStyle("-fx-background-color: #2E2E36");
        menuIncomeTab.setStyle("-fx-background-color: #2E2E36");
        menuExpenseTab.setStyle("-fx-background-color: #2E2E36");
        menuAnalyticsTab.setStyle("-fx-background-color: #3e7b91");
        menuHelpTab.setStyle("-fx-background-color: #2E2E36");
    }
}
