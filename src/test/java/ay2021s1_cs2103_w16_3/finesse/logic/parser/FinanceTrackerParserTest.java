package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ClearCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ExitCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.HelpCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListTransactionCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.TabCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.budget.SetExpenseLimitCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.budget.SetSavingsGoalCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.BookmarkTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.TitleContainsKeyphrasesPredicate;
import ay2021s1_cs2103_w16_3.finesse.testutil.BookmarkTransactionUtil;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditBookmarkTransactionDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditTransactionDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionUtil;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;

/**
 * Tests the result of parsing each command within each tab. Note that each tab is its own
 * equivalence partition.
 */
public class FinanceTrackerParserTest {

    private final FinanceTrackerParser parser = new FinanceTrackerParser();
    private final OverviewUiStateStub overviewUiStateStub = new OverviewUiStateStub();
    private final IncomesUiStateStub incomesUiStateStub = new IncomesUiStateStub();
    private final ExpensesUiStateStub expensesUiStateStub = new ExpensesUiStateStub();
    private final AnalyticsUiStateStub analyticsUiStateStub = new AnalyticsUiStateStub();
    private final UserGuideUiStateStub userGuideUiStateStub = new UserGuideUiStateStub();

    private void parseCommand_addCommandOnInvalidTab_throwsParseException(UiStateStub uiStateStub) {
        // Similar result as using an Income object instead.
        Expense expense = new TransactionBuilder().buildExpense();
        assertThrows(ParseException.class, () -> parser.parseCommand(
                TransactionUtil.getAddCommand(expense), uiStateStub));
    }

    @Test
    public void parseCommand_addCommandOnOverviewTab_throwsParseException() {
        parseCommand_addCommandOnInvalidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_addCommandOnIncomesTab_returnsAddIncomeCommand() throws ParseException {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddCommand(income), incomesUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addCommandOnExpensesTab_returnsAddExpenseCommand() throws ParseException {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddCommand(expense), expensesUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addCommandOnAnalyticsTab_throwsParseException() {
        parseCommand_addCommandOnInvalidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_addCommandOnUserGuideTab_throwsParseException() {
        parseCommand_addCommandOnInvalidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_addExpenseCommandOnValidTab_returnsAddExpenseCommand(UiStateStub uiStateStub)
            throws ParseException {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense),
                        uiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addExpenseCommandOnOverviewTab_returnsAddExpenseCommand() throws ParseException {
        parseCommand_addExpenseCommandOnValidTab_returnsAddExpenseCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_addExpenseCommandOnIncomesTab_returnsAddExpenseCommand() throws ParseException {
        parseCommand_addExpenseCommandOnValidTab_returnsAddExpenseCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_addExpenseCommandOnExpensesTab_returnsAddExpenseCommand() throws ParseException {
        parseCommand_addExpenseCommandOnValidTab_returnsAddExpenseCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_addExpenseCommandOnAnalyticsTab_returnsAddExpenseCommand() throws ParseException {
        parseCommand_addExpenseCommandOnValidTab_returnsAddExpenseCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_addExpenseCommandOnUserGuideTab_returnsAddExpenseCommand() throws ParseException {
        parseCommand_addExpenseCommandOnValidTab_returnsAddExpenseCommand(userGuideUiStateStub);
    }

    private void parseCommand_addIncomeCommandOnValidTab_returnsAddIncomeCommand(UiStateStub uiStateStub)
            throws ParseException {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                        uiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addIncomeCommandOnOverviewTab_returnsAddIncomeCommand() throws ParseException {
        parseCommand_addIncomeCommandOnValidTab_returnsAddIncomeCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_addIncomeCommandOnIncomesTab_returnsAddIncomeCommand() throws ParseException {
        parseCommand_addIncomeCommandOnValidTab_returnsAddIncomeCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_addIncomeCommandOnExpensesTab_returnsAddIncomeCommand() throws ParseException {
        parseCommand_addIncomeCommandOnValidTab_returnsAddIncomeCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_addIncomeCommandOnAnalyticsTab_returnsAddIncomeCommand() throws ParseException {
        parseCommand_addIncomeCommandOnValidTab_returnsAddIncomeCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_addIncomeCommandOnUserGuideTab_returnsAddIncomeCommand() throws ParseException {
        parseCommand_addIncomeCommandOnValidTab_returnsAddIncomeCommand(userGuideUiStateStub);
    }

    private void parseCommand_addBookmarkIncomeCommandOnValidTab_returnsAddBookmarkIncomeCommand(
            UiStateStub uiStateStub) throws ParseException {
        BookmarkIncome bookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();
        AddBookmarkIncomeCommand command =
                (AddBookmarkIncomeCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkIncomeCommand(bookmarkIncome), uiStateStub);
        assertEquals(new AddBookmarkIncomeCommand(bookmarkIncome), command);
    }

    @Test
    public void parseCommand_addBookmarkIncomeCommandOnOverviewTab_returnsAddBookmarkIncomeCommand()
            throws ParseException {
        parseCommand_addBookmarkIncomeCommandOnValidTab_returnsAddBookmarkIncomeCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_addBookmarkIncomeCommandOnIncomesTab_returnsAddBookmarkIncomeCommand()
            throws ParseException {
        parseCommand_addBookmarkIncomeCommandOnValidTab_returnsAddBookmarkIncomeCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_addBookmarkIncomeCommandOnExpensesTab_returnsAddBookmarkIncomeCommand()
            throws ParseException {
        parseCommand_addBookmarkIncomeCommandOnValidTab_returnsAddBookmarkIncomeCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_addBookmarkIncomeCommandOnAnalyticsTab_returnsAddBookmarkIncomeCommand()
            throws ParseException {
        parseCommand_addBookmarkIncomeCommandOnValidTab_returnsAddBookmarkIncomeCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_addBookmarkIncomeCommandOnUserGuideTab_returnsAddBookmarkIncomeCommand()
            throws ParseException {
        parseCommand_addBookmarkIncomeCommandOnValidTab_returnsAddBookmarkIncomeCommand(userGuideUiStateStub);
    }

    private void parseCommand_addBookmarkExpenseCommandOnValidTab_returnsAddBookmarkExpenseCommand(
            UiStateStub uiStateStub) throws ParseException {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        AddBookmarkExpenseCommand command =
                (AddBookmarkExpenseCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkExpenseCommand(bookmarkExpense), uiStateStub);
        assertEquals(new AddBookmarkExpenseCommand(bookmarkExpense), command);
    }

    @Test
    public void parseCommand_addBookmarkExpenseCommandOnOverviewTab_returnsAddBookmarkExpenseCommand()
            throws ParseException {
        parseCommand_addBookmarkExpenseCommandOnValidTab_returnsAddBookmarkExpenseCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_addBookmarkExpenseCommandOnIncomesTab_returnsAddBookmarkExpenseCommand()
            throws ParseException {
        parseCommand_addBookmarkExpenseCommandOnValidTab_returnsAddBookmarkExpenseCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_addBookmarkExpenseCommandOnExpensesTab_returnsAddBookmarkExpenseCommand()
            throws ParseException {
        parseCommand_addBookmarkExpenseCommandOnValidTab_returnsAddBookmarkExpenseCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_addBookmarkExpenseCommandOnAnalyticsTab_returnsAddBookmarkExpenseCommand()
            throws ParseException {
        parseCommand_addBookmarkExpenseCommandOnValidTab_returnsAddBookmarkExpenseCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_addBookmarkExpenseCommandOnUserGuideTab_returnsAddBookmarkExpenseCommand()
            throws ParseException {
        parseCommand_addBookmarkExpenseCommandOnValidTab_returnsAddBookmarkExpenseCommand(userGuideUiStateStub);
    }

    private void parseCommand_clearCommandOnValidTab_returnsClearCommand(UiStateStub uiStateStub)
            throws ParseException {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, uiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearCommandOnOverviewTab_returnsClearCommand() throws ParseException {
        parseCommand_clearCommandOnValidTab_returnsClearCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_clearCommandOnIncomesTab_returnsClearCommand() throws ParseException {
        parseCommand_clearCommandOnValidTab_returnsClearCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_clearCommandOnExpensesTab_returnsClearCommand() throws ParseException {
        parseCommand_clearCommandOnValidTab_returnsClearCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_clearCommandOnAnalyticsTab_returnsClearCommand() throws ParseException {
        parseCommand_clearCommandOnValidTab_returnsClearCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_clearCommandOnUserGuideTab_returnsClearCommand() throws ParseException {
        parseCommand_clearCommandOnValidTab_returnsClearCommand(userGuideUiStateStub);
    }

    private void parseCommand_clearCommandWithArgsOnValidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ClearCommand.COMMAND_WORD + " a", uiStateStub));
    }

    @Test
    public void parseCommand_clearCommandWithArgsOnOverviewTab_throwsParseException() {
        parseCommand_clearCommandWithArgsOnValidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_clearCommandWithArgsOnIncomesTab_throwsParseException() {
        parseCommand_clearCommandWithArgsOnValidTab_throwsParseException(incomesUiStateStub);
    }

    @Test
    public void parseCommand_clearCommandWithArgsOnExpensesTab_throwsParseException() {
        parseCommand_clearCommandWithArgsOnValidTab_throwsParseException(expensesUiStateStub);
    }

    @Test
    public void parseCommand_clearCommandWithArgsOnAnalyticsTab_throwsParseException() {
        parseCommand_clearCommandWithArgsOnValidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_clearCommandWithArgsOnUserGuideTab_throwsParseException() {
        parseCommand_clearCommandWithArgsOnValidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_deleteCommandOnValidTab_returnsDeleteCommand(UiStateStub uiStateStub)
            throws ParseException {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), uiStateStub);
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    private void parseCommand_deleteCommandOnInvalidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), uiStateStub));
    }

    @Test
    public void parseCommand_deleteCommandOnOverviewTab_throwsParseException() {
        parseCommand_deleteCommandOnInvalidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_deleteCommandOnIncomesTab_returnsDeleteCommand() throws ParseException {
        parseCommand_deleteCommandOnValidTab_returnsDeleteCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_deleteCommandOnExpensesTab_returnsDeleteCommand() throws ParseException {
        parseCommand_deleteCommandOnValidTab_returnsDeleteCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_deleteCommandOnAnalyticsTab_throwsParseException() {
        parseCommand_deleteCommandOnInvalidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_deleteCommandOnUserGuideTab_throwsParseException() {
        parseCommand_deleteCommandOnInvalidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_deleteBookmarkCommandOnValidTab_returnsDeleteBookmarkCommand(UiStateStub uiStateStub)
            throws ParseException {
        DeleteBookmarkCommand command = (DeleteBookmarkCommand) parser.parseCommand(
                DeleteBookmarkCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), uiStateStub);
        assertEquals(new DeleteBookmarkCommand(INDEX_FIRST), command);
    }

    private void parseCommand_deleteBookmarkCommandOnInvalidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                DeleteBookmarkCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), uiStateStub));
    }

    @Test
    public void parseCommand_deleteBookmarkCommandOnOverviewTab_throwsParseException() {
        parseCommand_deleteBookmarkCommandOnInvalidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_deleteBookmarkCommandOnIncomesTab_returnsDeleteBookmarkCommand() throws ParseException {
        parseCommand_deleteBookmarkCommandOnValidTab_returnsDeleteBookmarkCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_deleteBookmarkCommandOnExpensesTab_returnsDeleteBookmarkCommand() throws ParseException {
        parseCommand_deleteBookmarkCommandOnValidTab_returnsDeleteBookmarkCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_deleteBookmarkCommandOnAnalyticsTab_throwsParseException() {
        parseCommand_deleteBookmarkCommandOnInvalidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_deleteBookmarkCommandOnUserGuideTab_throwsParseException() {
        parseCommand_deleteBookmarkCommandOnInvalidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_editCommandOnInvalidTab_throwsParseException(UiStateStub uiStateStub) {
        Expense expense = new TransactionBuilder().buildExpense();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(expense).build();
        assertThrows(ParseException.class, () -> parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), uiStateStub));
    }

    @Test
    public void parseCommand_editCommandOnOverviewTab_throwsParseException() {
        parseCommand_editCommandOnInvalidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_editCommandOnIncomesTab_returnsEditCommand() throws ParseException {
        Income income = new TransactionBuilder().buildIncome();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(income).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), incomesUiStateStub);
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editCommandOnExpensesTab_returnsEditCommand() throws ParseException {
        Expense expense = new TransactionBuilder().buildExpense();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(expense).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), expensesUiStateStub);
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editCommandOnAnalyticsTab_throwsParseException() {
        parseCommand_editCommandOnInvalidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_editCommandOnUserGuideTab_throwsParseException() {
        parseCommand_editCommandOnInvalidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_editBookmarkCommandOnInvalidTab_throwsParseException(UiStateStub uiStateStub) {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder(bookmarkExpense)
                .build();
        assertThrows(ParseException.class, () -> parser.parseCommand(EditBookmarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + BookmarkTransactionUtil
                .getEditBookmarkTransactionDescriptorDetails(descriptor), uiStateStub));
    }

    @Test
    public void parseCommand_editBookmarkCommandOnOverviewTab_throwsParseException() {
        parseCommand_editBookmarkCommandOnInvalidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_editBookmarkCommandOnIncomesTab_returnsEditBookmarkCommand() throws ParseException {
        BookmarkIncome bookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();
        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder(bookmarkIncome)
                .build();
        EditBookmarkCommand command = (EditBookmarkCommand) parser.parseCommand(EditBookmarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + BookmarkTransactionUtil
                .getEditBookmarkTransactionDescriptorDetails(descriptor), incomesUiStateStub);
        assertEquals(new EditBookmarkCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editBookmarkCommandOnExpensesTab_returnsEditBookmarkCommand() throws ParseException {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder(bookmarkExpense)
                .build();
        EditBookmarkCommand command = (EditBookmarkCommand) parser.parseCommand(EditBookmarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + BookmarkTransactionUtil
                .getEditBookmarkTransactionDescriptorDetails(descriptor), expensesUiStateStub);
        assertEquals(new EditBookmarkCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editBookmarkCommandOnAnalyticsTab_throwsParseException() {
        parseCommand_editBookmarkCommandOnInvalidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_editBookmarkCommandOnUserGuideTab_throwsParseException() {
        parseCommand_editBookmarkCommandOnInvalidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_convertBookmarkCommandOnValidTab_returnsConvertBookmarkCommand(UiStateStub uiStateStub)
            throws ParseException {
        Date testDate = new Date(VALID_DATE_INTERNSHIP);
        ConvertBookmarkCommand command = (ConvertBookmarkCommand) parser.parseCommand(
                ConvertBookmarkCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + PREFIX_DATE + VALID_DATE_INTERNSHIP,
                uiStateStub);
        assertEquals(new ConvertBookmarkCommand(INDEX_FIRST, testDate), command);
    }

    private void parseCommand_convertBookmarkCommandOnInvalidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                ConvertBookmarkCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), uiStateStub));
    }

    @Test
    public void parseCommand_convertBookmarkCommandOnOverviewTab_throwsParseException() {
        parseCommand_convertBookmarkCommandOnInvalidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_convertBookmarkCommandOnIncomesTab_returnsConvertBookmarkCommand() throws ParseException {
        parseCommand_convertBookmarkCommandOnValidTab_returnsConvertBookmarkCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_convertBookmarkCommandOnExpensesTab_returnsConvertBookmarkCommand() throws ParseException {
        parseCommand_convertBookmarkCommandOnValidTab_returnsConvertBookmarkCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_convertBookmarkCommandOnAnalyticsTab_throwsParseException() {
        parseCommand_convertBookmarkCommandOnInvalidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_convertBookmarkCommandOnUserGuideTab_throwsParseException() {
        parseCommand_convertBookmarkCommandOnInvalidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_exitCommandOnValidTab_returnsExitCommand(UiStateStub uiStateStub) throws ParseException {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, uiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitCommandOnOverviewTab_returnsExitCommand() throws ParseException {
        parseCommand_exitCommandOnValidTab_returnsExitCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_exitCommandOnIncomesTab_returnsExitCommand() throws ParseException {
        parseCommand_exitCommandOnValidTab_returnsExitCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_exitCommandOnExpensesTab_returnsExitCommand() throws ParseException {
        parseCommand_exitCommandOnValidTab_returnsExitCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_exitCommandOnAnalyticsTab_returnsExitCommand() throws ParseException {
        parseCommand_exitCommandOnValidTab_returnsExitCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_exitCommandOnUserGuideTab_returnsExitCommand() throws ParseException {
        parseCommand_exitCommandOnValidTab_returnsExitCommand(userGuideUiStateStub);
    }

    private void parseCommand_exitCommandWithArgsOnValidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ExitCommand.COMMAND_WORD + " a", uiStateStub));
    }

    @Test
    public void parseCommand_exitCommandWithArgsOnOverviewTab_throwsParseException() {
        parseCommand_exitCommandWithArgsOnValidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_exitCommandWithArgsOnIncomesTab_throwsParseException() {
        parseCommand_exitCommandWithArgsOnValidTab_throwsParseException(incomesUiStateStub);
    }

    @Test
    public void parseCommand_exitCommandWithArgsOnExpensesTab_throwsParseException() {
        parseCommand_exitCommandWithArgsOnValidTab_throwsParseException(expensesUiStateStub);
    }

    @Test
    public void parseCommand_exitCommandWithArgsOnAnalyticsTab_throwsParseException() {
        parseCommand_exitCommandWithArgsOnValidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_exitCommandWithArgsOnUserGuideTab_throwsParseException() {
        parseCommand_exitCommandWithArgsOnValidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_findCommandOnValidTab_returnsFindCommand(UiStateStub uiStateStub) throws ParseException {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + PREFIX_TITLE + keywords.stream()
                        .collect(Collectors.joining(" " + PREFIX_TITLE)), uiStateStub);
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(new TitleContainsKeyphrasesPredicate(keywords));
        assertEquals(new FindCommand(predicateList), command);
    }

    private void parseCommand_findCommandOnInvalidTab_throwsParseException(UiStateStub uiStateStub) {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        assertThrows(ParseException.class, () -> parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + PREFIX_TITLE + keywords.stream()
                        .collect(Collectors.joining(" " + PREFIX_TITLE)), uiStateStub));
    }

    @Test
    public void parseCommand_findCommandOnOverviewTab_returnsFindCommand() throws ParseException {
        parseCommand_findCommandOnValidTab_returnsFindCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_findCommandOnIncomesTab_returnsFindCommand() throws ParseException {
        parseCommand_findCommandOnValidTab_returnsFindCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_findCommandOnExpensesTab_returnsFindCommand() throws ParseException {
        parseCommand_findCommandOnValidTab_returnsFindCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_findCommandOnAnalyticsTab_throwsParseException() {
        parseCommand_findCommandOnInvalidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_findCommandOnUserGuideTab_throwsParseException() {
        parseCommand_findCommandOnInvalidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_helpCommandOnValidTab_returnsHelpCommand(UiStateStub uiStateStub) throws ParseException {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, uiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpCommandOnOverviewTab_returnsHelpCommand() throws ParseException {
        parseCommand_helpCommandOnValidTab_returnsHelpCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_helpCommandOnIncomesTab_returnsHelpCommand() throws ParseException {
        parseCommand_helpCommandOnValidTab_returnsHelpCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_helpCommandOnExpensesTab_returnsHelpCommand() throws ParseException {
        parseCommand_helpCommandOnValidTab_returnsHelpCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_helpCommandOnAnalyticsTab_returnsHelpCommand() throws ParseException {
        parseCommand_helpCommandOnValidTab_returnsHelpCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_helpCommandOnUserGuideTab_returnsHelpCommand() throws ParseException {
        parseCommand_helpCommandOnValidTab_returnsHelpCommand(userGuideUiStateStub);
    }

    private void parseCommand_helpCommandWithArgsOnValidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(HelpCommand.COMMAND_WORD + " a", uiStateStub));
    }

    @Test
    public void parseCommand_helpCommandWithArgsOnOverviewTab_throwsParseException() {
        parseCommand_helpCommandWithArgsOnValidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_helpCommandWithArgsOnIncomesTab_throwsParseException() {
        parseCommand_helpCommandWithArgsOnValidTab_throwsParseException(incomesUiStateStub);
    }

    @Test
    public void parseCommand_helpCommandWithArgsOnExpensesTab_throwsParseException() {
        parseCommand_helpCommandWithArgsOnValidTab_throwsParseException(expensesUiStateStub);
    }

    @Test
    public void parseCommand_helpCommandWithArgsOnAnalyticsTab_throwsParseException() {
        parseCommand_helpCommandWithArgsOnValidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_helpCommandWithArgsOnUserGuideTab_throwsParseException() {
        parseCommand_helpCommandWithArgsOnValidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_setExpenseLimitCommandOnValidTab_returnsSetExpenseLimitCommand(UiStateStub uiStateStub)
            throws ParseException {
        String amount = "$500.00";
        SetExpenseLimitCommand command = (SetExpenseLimitCommand) parser.parseCommand(
                SetExpenseLimitCommand.COMMAND_WORD + " " + PREFIX_AMOUNT + amount, uiStateStub);
        assertEquals(new SetExpenseLimitCommand(new Amount(amount)), command);
    }

    @Test
    public void parseCommand_setExpenseLimitCommandOnOverviewTab_returnsSetExpenseLimitCommand() throws ParseException {
        parseCommand_setExpenseLimitCommandOnValidTab_returnsSetExpenseLimitCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_setExpenseLimitCommandOnIncomesTab_returnsSetExpenseLimitCommand() throws ParseException {
        parseCommand_setExpenseLimitCommandOnValidTab_returnsSetExpenseLimitCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_setExpenseLimitCommandOnExpensesTab_returnsSetExpenseLimitCommand() throws ParseException {
        parseCommand_setExpenseLimitCommandOnValidTab_returnsSetExpenseLimitCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_setExpenseLimitCommandOnAnalyticsTab_returnsSetExpenseLimitCommand()
            throws ParseException {
        parseCommand_setExpenseLimitCommandOnValidTab_returnsSetExpenseLimitCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_setExpenseLimitCommandOnUserGuideTab_returnsSetExpenseLimitCommand()
            throws ParseException {
        parseCommand_setExpenseLimitCommandOnValidTab_returnsSetExpenseLimitCommand(userGuideUiStateStub);
    }

    private void parseCommand_setSavingsGoalCommandOnValidTab_returnsSetSavingsGoalCommand(UiStateStub uiStateStub)
            throws ParseException {
        String amount = "$500.00";
        SetSavingsGoalCommand command = (SetSavingsGoalCommand) parser.parseCommand(
                SetSavingsGoalCommand.COMMAND_WORD + " " + PREFIX_AMOUNT + amount, uiStateStub);
        assertEquals(new SetSavingsGoalCommand(new Amount(amount)), command);
    }

    @Test
    public void parseCommand_setSavingsGoalCommandOnOverviewTab_returnsSetSavingsGoalCommand() throws ParseException {
        parseCommand_setSavingsGoalCommandOnValidTab_returnsSetSavingsGoalCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_setSavingsGoalCommandOnIncomesTab_returnsSetSavingsGoalCommand() throws ParseException {
        parseCommand_setSavingsGoalCommandOnValidTab_returnsSetSavingsGoalCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_setSavingsGoalCommandOnExpensesTab_returnsSetSavingsGoalCommand() throws ParseException {
        parseCommand_setSavingsGoalCommandOnValidTab_returnsSetSavingsGoalCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_setSavingsGoalCommandOnAnalyticsTab_returnsSetSavingsGoalCommand() throws ParseException {
        parseCommand_setSavingsGoalCommandOnValidTab_returnsSetSavingsGoalCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_setSavingsGoalCommandOnUserGuideTab_returnsSetSavingsGoalCommand() throws ParseException {
        parseCommand_setSavingsGoalCommandOnValidTab_returnsSetSavingsGoalCommand(userGuideUiStateStub);
    }

    private void parseCommand_listCommandOnInvalidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListCommand.COMMAND_WORD, uiStateStub));
    }

    @Test
    public void parseCommand_listCommandOnOverviewTab_returnsListCommand() throws ParseException {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listCommandOnIncomesTab_returnsListCommand() throws ParseException {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, incomesUiStateStub)
                instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listCommandOnExpensesTab_returnsListCommand() throws ParseException {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, expensesUiStateStub)
                instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listCommandOnAnalyticsTab_returnsListCommand() {
        parseCommand_listCommandOnInvalidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_listCommandOnUserGuideTab_returnsListCommand() {
        parseCommand_listCommandOnInvalidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_listCommandWithArgsOnValidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListCommand.COMMAND_WORD + " a", uiStateStub));
    }

    @Test
    public void parseCommand_listCommandWithArgsOnOverviewTab_throwsParseException() {
        parseCommand_listCommandWithArgsOnValidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_listCommandWithArgsOnIncomesTab_throwsParseException() {
        parseCommand_listCommandWithArgsOnValidTab_throwsParseException(incomesUiStateStub);
    }

    @Test
    public void parseCommand_listCommandWithArgsOnExpensesTab_throwsParseException() {
        parseCommand_listCommandWithArgsOnValidTab_throwsParseException(expensesUiStateStub);
    }

    @Test
    public void parseCommand_listCommandWithArgsOnAnalyticsTab_throwsParseException() {
        parseCommand_listCommandWithArgsOnValidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_listCommandWithArgsOnUserGuideTab_throwsParseException() {
        parseCommand_listCommandWithArgsOnValidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_listTransactionCommandOnValidTab_returnsListTransactionCommand(UiStateStub uiStateStub)
            throws ParseException {
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD, uiStateStub)
                instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listTransactionCommandOnOverviewTab_returnsListTransactionCommand() throws ParseException {
        parseCommand_listTransactionCommandOnValidTab_returnsListTransactionCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_listTransactionCommandOnIncomesTab_returnsListTransactionCommand() throws ParseException {
        parseCommand_listTransactionCommandOnValidTab_returnsListTransactionCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_listTransactionCommandOnExpensesTab_returnsListTransactionCommand() throws ParseException {
        parseCommand_listTransactionCommandOnValidTab_returnsListTransactionCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_listTransactionCommandOnAnalyticsTab_returnsListTransactionCommand()
            throws ParseException {
        parseCommand_listTransactionCommandOnValidTab_returnsListTransactionCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_listTransactionCommandOnUserGuideTab_returnsListTransactionCommand()
            throws ParseException {
        parseCommand_listTransactionCommandOnValidTab_returnsListTransactionCommand(userGuideUiStateStub);
    }

    private void parseCommand_listTransactionCommandWithArgsOnValidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListTransactionCommand.COMMAND_WORD + " a", uiStateStub));
    }

    @Test
    public void parseCommand_listTransactionCommandWithArgsOnOverviewTab_throwsParseException() {
        parseCommand_listTransactionCommandWithArgsOnValidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_listTransactionCommandWithArgsOnIncomesTab_throwsParseException() {
        parseCommand_listTransactionCommandWithArgsOnValidTab_throwsParseException(incomesUiStateStub);
    }

    @Test
    public void parseCommand_listTransactionCommandWithArgsOnExpensesTab_throwsParseException() {
        parseCommand_listTransactionCommandWithArgsOnValidTab_throwsParseException(expensesUiStateStub);
    }

    @Test
    public void parseCommand_listTransactionCommandWithArgsOnAnalyticsTab_throwsParseException() {
        parseCommand_listTransactionCommandWithArgsOnValidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_listTransactionCommandWithArgsOnUserGuideTab_throwsParseException() {
        parseCommand_listTransactionCommandWithArgsOnValidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_listExpenseCommandOnValidTab_returnsListExpenseCommand(UiStateStub uiStateStub)
            throws ParseException {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, uiStateStub)
                instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listExpenseCommandOnOverviewTab_returnsListExpenseCommand() throws ParseException {
        parseCommand_listExpenseCommandOnValidTab_returnsListExpenseCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_listExpenseCommandOnIncomesTab_returnsListExpenseCommand() throws ParseException {
        parseCommand_listExpenseCommandOnValidTab_returnsListExpenseCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_listExpenseCommandOnExpensesTab_returnsListExpenseCommand() throws ParseException {
        parseCommand_listExpenseCommandOnValidTab_returnsListExpenseCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_listExpenseCommandOnAnalyticsTab_returnsListExpenseCommand() throws ParseException {
        parseCommand_listExpenseCommandOnValidTab_returnsListExpenseCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_listExpenseCommandOnUserGuideTab_returnsListExpenseCommand() throws ParseException {
        parseCommand_listExpenseCommandOnValidTab_returnsListExpenseCommand(userGuideUiStateStub);
    }

    private void parseCommand_listExpenseCommandWithArgsOnValidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListExpenseCommand.COMMAND_WORD + " a", uiStateStub));
    }

    @Test
    public void parseCommand_listExpenseCommandWithArgsOnOverviewTab_throwsParseException() {
        parseCommand_listExpenseCommandWithArgsOnValidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_listExpenseCommandWithArgsOnIncomesTab_throwsParseException() {
        parseCommand_listExpenseCommandWithArgsOnValidTab_throwsParseException(incomesUiStateStub);
    }

    @Test
    public void parseCommand_listExpenseCommandWithArgsOnExpensesTab_throwsParseException() {
        parseCommand_listExpenseCommandWithArgsOnValidTab_throwsParseException(expensesUiStateStub);
    }

    @Test
    public void parseCommand_listExpenseCommandWithArgsOnAnalyticsTab_throwsParseException() {
        parseCommand_listExpenseCommandWithArgsOnValidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_listExpenseCommandWithArgsOnUserGuideTab_throwsParseException() {
        parseCommand_listExpenseCommandWithArgsOnValidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_listIncomeCommandOnValidTab_returnsListIncomeCommand(UiStateStub uiStateStub)
            throws ParseException {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, uiStateStub)
                instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listIncomeCommandOnOverviewTab_returnsListIncomeCommand() throws ParseException {
        parseCommand_listIncomeCommandOnValidTab_returnsListIncomeCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_listIncomeCommandOnIncomesTab_returnsListIncomeCommand() throws ParseException {
        parseCommand_listIncomeCommandOnValidTab_returnsListIncomeCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_listIncomeCommandOnExpensesTab_returnsListIncomeCommand() throws ParseException {
        parseCommand_listIncomeCommandOnValidTab_returnsListIncomeCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_listIncomeCommandOnAnalyticsTab_returnsListIncomeCommand() throws ParseException {
        parseCommand_listIncomeCommandOnValidTab_returnsListIncomeCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_listIncomeCommandOnUserGuideTab_returnsListIncomeCommand() throws ParseException {
        parseCommand_listIncomeCommandOnValidTab_returnsListIncomeCommand(userGuideUiStateStub);
    }

    private void parseCommand_listIncomeCommandWithArgsOnValidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListIncomeCommand.COMMAND_WORD + " a", uiStateStub));
    }

    @Test
    public void parseCommand_listIncomeCommandWithArgsOnOverviewTab_throwParseException() {
        parseCommand_listIncomeCommandWithArgsOnValidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_listIncomeCommandWithArgsOnIncomesTab_throwsParseException() {
        parseCommand_listIncomeCommandWithArgsOnValidTab_throwsParseException(incomesUiStateStub);
    }

    @Test
    public void parseCommand_listIncomeCommandWithArgsOnExpensesTab_throwsParseException() {
        parseCommand_listIncomeCommandWithArgsOnValidTab_throwsParseException(expensesUiStateStub);
    }

    @Test
    public void parseCommand_listIncomeCommandWithArgsOnAnalyticsTab_throwsParseException() {
        parseCommand_listIncomeCommandWithArgsOnValidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_listIncomeCommandWithArgsOnUserGuideTab_throwsParseException() {
        parseCommand_listIncomeCommandWithArgsOnValidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_tabCommandOnValidTab_returnsTabCommand(UiStateStub uiStateStub) throws ParseException {
        TabCommand command = (TabCommand) parser.parseCommand(
                TabCommand.COMMAND_WORD + " 1", uiStateStub);
        assertEquals(new TabCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_tabCommandOnOverviewTab_returnsTabCommand() throws ParseException {
        parseCommand_tabCommandOnValidTab_returnsTabCommand(overviewUiStateStub);
    }

    @Test
    public void parseCommand_tabCommandOnIncomesTab_returnsTabCommand() throws ParseException {
        parseCommand_tabCommandOnValidTab_returnsTabCommand(incomesUiStateStub);
    }

    @Test
    public void parseCommand_tabCommandOnExpensesTab_returnsTabCommand() throws ParseException {
        parseCommand_tabCommandOnValidTab_returnsTabCommand(expensesUiStateStub);
    }

    @Test
    public void parseCommand_tabCommandOnAnalyticsTab_returnsTabCommand() throws ParseException {
        parseCommand_tabCommandOnValidTab_returnsTabCommand(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_tabCommandOnUserGuideTab_returnsTabCommand() throws ParseException {
        parseCommand_tabCommandOnValidTab_returnsTabCommand(userGuideUiStateStub);
    }

    private void parseCommand_unrecognizedInputOnValidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand("", uiStateStub));
    }

    @Test
    public void parseCommand_unrecognizedInputOnOverviewTab_throwsParseException() {
        parseCommand_unrecognizedInputOnValidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_unrecognizedInputOnIncomesTab_throwsParseException() {
        parseCommand_unrecognizedInputOnValidTab_throwsParseException(incomesUiStateStub);
    }

    @Test
    public void parseCommand_unrecognizedInputOnExpensesTab_throwsParseException() {
        parseCommand_unrecognizedInputOnValidTab_throwsParseException(expensesUiStateStub);
    }

    @Test
    public void parseCommand_unrecognizedInputOnAnalyticsTab_throwsParseException() {
        parseCommand_unrecognizedInputOnValidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_unrecognizedInputOnUserGuideTab_throwsParseException() {
        parseCommand_unrecognizedInputOnValidTab_throwsParseException(userGuideUiStateStub);
    }

    private void parseCommand_unknownCommandOnValidTab_throwsParseException(UiStateStub uiStateStub) {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("unknownCommand", uiStateStub));
    }

    @Test
    public void parseCommand_unknownCommandOnOverviewTab_throwsParseException() {
        parseCommand_unknownCommandOnValidTab_throwsParseException(overviewUiStateStub);
    }

    @Test
    public void parseCommand_unknownCommandOnIncomesTab_throwsParseException() {
        parseCommand_unknownCommandOnValidTab_throwsParseException(incomesUiStateStub);
    }

    @Test
    public void parseCommand_unknownCommandOnExpensesTab_throwsParseException() {
        parseCommand_unknownCommandOnValidTab_throwsParseException(expensesUiStateStub);
    }

    @Test
    public void parseCommand_unknownCommandOnAnalyticsTab_throwsParseException() {
        parseCommand_unknownCommandOnValidTab_throwsParseException(analyticsUiStateStub);
    }

    @Test
    public void parseCommand_unknownCommandOnUserGuideTab_throwsParseException() {
        parseCommand_unknownCommandOnValidTab_throwsParseException(userGuideUiStateStub);
    }

    /**
     * A default {@code UiState} stub that has all of the methods failing.
     */
    private static class UiStateStub extends UiState {
        @Override
        public Tab getCurrentTab() {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void setCurrentTab(Tab currentTab) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Overview' tab as the current tab.
     */
    public static class OverviewUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.OVERVIEW;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Income' tab as the current tab.
     */
    public static class IncomesUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.INCOME;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Expenses' tab as the current tab.
     */
    public static class ExpensesUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.EXPENSES;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Analytics' tab as the current tab.
     */
    public static class AnalyticsUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.ANALYTICS;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'User Guide' tab as the current tab.
     */
    public static class UserGuideUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.USER_GUIDE;
        }
    }
}
