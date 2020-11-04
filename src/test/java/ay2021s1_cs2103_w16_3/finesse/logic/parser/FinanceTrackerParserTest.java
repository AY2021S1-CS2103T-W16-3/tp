package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
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
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.TitleContainsKeyphrasesPredicate;
import ay2021s1_cs2103_w16_3.finesse.testutil.BookmarkTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.BookmarkTransactionUtil;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditBookmarkTransactionDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditTransactionDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionUtil;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;

public class FinanceTrackerParserTest {

    private final FinanceTrackerParser parser = new FinanceTrackerParser();
    private final OverviewUiStateStub overviewUiStateStub = new OverviewUiStateStub();
    private final IncomeUiStateStub incomeUiStateStub = new IncomeUiStateStub();
    private final ExpensesUiStateStub expensesUiStateStub = new ExpensesUiStateStub();
    private final AnalyticsUiStateStub analyticsUiStateStub = new AnalyticsUiStateStub();

    @Test
    public void parseCommand_addWhenOverviewTab() {
        Expense expense = new TransactionBuilder().buildExpense();
        assertThrows(ParseException.class, () -> parser.parseCommand(
                TransactionUtil.getAddCommand(expense), overviewUiStateStub));
    }

    @Test
    public void parseCommand_addWhenIncomeTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddCommand(income), incomeUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addWhenExpensesTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddCommand(expense), expensesUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addWhenAnalyticsTab() {
        Expense expense = new TransactionBuilder().buildExpense();
        assertThrows(ParseException.class, () -> parser.parseCommand(
                TransactionUtil.getAddCommand(expense), analyticsUiStateStub));
    }

    @Test
    public void parseCommand_addExpenseWhenOverviewTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
            (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense),
            overviewUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addExpenseWhenIncomeTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense),
                        incomeUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addExpenseWhenExpensesTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense),
                        expensesUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addExpenseWhenAnalyticsTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
                (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense),
                        analyticsUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addIncomeWhenOverviewTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                overviewUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addIncomeWhenIncomeTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                        incomeUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addIncomeWhenExpensesTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                        expensesUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addIncomeWhenAnalyticsTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                        analyticsUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_addBookmarkIncomeWhenOverviewTab() throws Exception {
        BookmarkIncome bookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();
        AddBookmarkIncomeCommand command =
                (AddBookmarkIncomeCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkIncomeCommand(bookmarkIncome), overviewUiStateStub);
        assertEquals(new AddBookmarkIncomeCommand(bookmarkIncome), command);
    }

    @Test
    public void parseCommand_addBookmarkIncomeWhenIncomesTab() throws Exception {
        BookmarkIncome bookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();
        AddBookmarkIncomeCommand command =
                (AddBookmarkIncomeCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkIncomeCommand(bookmarkIncome), incomeUiStateStub);
        assertEquals(new AddBookmarkIncomeCommand(bookmarkIncome), command);
    }

    @Test
    public void parseCommand_addBookmarkIncomeWhenExpensesTab() throws Exception {
        BookmarkIncome bookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();
        AddBookmarkIncomeCommand command =
                (AddBookmarkIncomeCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkIncomeCommand(bookmarkIncome), expensesUiStateStub);
        assertEquals(new AddBookmarkIncomeCommand(bookmarkIncome), command);
    }

    @Test
    public void parseCommand_addBookmarkIncomeWhenAnalyticsTab() throws Exception {
        BookmarkIncome bookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();
        AddBookmarkIncomeCommand command =
                (AddBookmarkIncomeCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkIncomeCommand(bookmarkIncome), analyticsUiStateStub);
        assertEquals(new AddBookmarkIncomeCommand(bookmarkIncome), command);
    }

    @Test
    public void parseCommand_addBookmarkExpenseWhenOverviewTab() throws Exception {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        AddBookmarkExpenseCommand command =
                (AddBookmarkExpenseCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkExpenseCommand(bookmarkExpense), overviewUiStateStub);
        assertEquals(new AddBookmarkExpenseCommand(bookmarkExpense), command);
    }

    @Test
    public void parseCommand_addBookmarkExpenseWhenIncomesTab() throws Exception {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        AddBookmarkExpenseCommand command =
                (AddBookmarkExpenseCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkExpenseCommand(bookmarkExpense), incomeUiStateStub);
        assertEquals(new AddBookmarkExpenseCommand(bookmarkExpense), command);
    }

    @Test
    public void parseCommand_addBookmarkExpenseWhenExpensesTab() throws Exception {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        AddBookmarkExpenseCommand command =
                (AddBookmarkExpenseCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkExpenseCommand(bookmarkExpense), expensesUiStateStub);
        assertEquals(new AddBookmarkExpenseCommand(bookmarkExpense), command);
    }

    @Test
    public void parseCommand_addBookmarkExpenseWhenAnalyticsTab() throws Exception {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        AddBookmarkExpenseCommand command =
                (AddBookmarkExpenseCommand) parser.parseCommand(BookmarkTransactionUtil
                        .getAddBookmarkExpenseCommand(bookmarkExpense), analyticsUiStateStub);
        assertEquals(new AddBookmarkExpenseCommand(bookmarkExpense), command);
    }

    @Test
    public void parseCommand_clearWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, overviewUiStateStub) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD,
                overviewUiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, incomeUiStateStub) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD,
                incomeUiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, expensesUiStateStub) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD,
                expensesUiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, analyticsUiStateStub) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD,
                analyticsUiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearWithArgsWhenOverviewTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ClearCommand.COMMAND_WORD + "a", overviewUiStateStub));
    }

    @Test
    public void parseCommand_clearWithArgsWhenIncomeTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ClearCommand.COMMAND_WORD + "a", incomeUiStateStub));
    }

    @Test
    public void parseCommand_clearWithArgsWhenExpensesTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ClearCommand.COMMAND_WORD + "a", expensesUiStateStub));
    }

    @Test
    public void parseCommand_clearWithArgsWhenAnalyticsTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ClearCommand.COMMAND_WORD + "a", analyticsUiStateStub));
    }

    @Test
    public void parseCommand_deleteWhenOverviewTab() {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), overviewUiStateStub));
    }

    @Test
    public void parseCommand_deleteWhenIncomeTab() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), incomeUiStateStub);
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteWhenExpensesTab() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), expensesUiStateStub);
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteWhenAnalyticsTab() {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), analyticsUiStateStub));
    }

    @Test
    public void parseCommand_deleteBookmarkWhenOverviewTab() {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                DeleteBookmarkCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), overviewUiStateStub));
    }

    @Test
    public void parseCommand_deleteBookmarkWhenIncomesTab() throws Exception {
        DeleteBookmarkCommand command = (DeleteBookmarkCommand) parser.parseCommand(
                DeleteBookmarkCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), incomeUiStateStub);
        assertEquals(new DeleteBookmarkIncomeCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteBookmarkWhenExpensesTab() throws Exception {
        DeleteBookmarkCommand command = (DeleteBookmarkCommand) parser.parseCommand(
                DeleteBookmarkCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), expensesUiStateStub);
        assertEquals(new DeleteBookmarkExpenseCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteBookmarkWhenAnalyticsTab() {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                DeleteBookmarkCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), analyticsUiStateStub));
    }

    @Test
    public void parseCommand_editWhenOverviewTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(expense).build();
        assertThrows(ParseException.class, () -> parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), overviewUiStateStub));
    }

    @Test
    public void parseCommand_editWhenIncomeTab() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(income).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), incomeUiStateStub);
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editWhenExpensesTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(expense).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), expensesUiStateStub);
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editWhenAnalyticsTab() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(expense).build();
        assertThrows(ParseException.class, () -> parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), analyticsUiStateStub));
    }

    @Test
    public void parseCommand_editBookmarkWhenOverviewTab() {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder(bookmarkExpense)
                .build();
        assertThrows(ParseException.class, () -> parser.parseCommand(EditBookmarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + BookmarkTransactionUtil
                .getEditBookmarkTransactionDescriptorDetails(descriptor), overviewUiStateStub));

    }

    @Test
    public void parseCommand_editBookmarkWhenIncomesTab() throws Exception {
        BookmarkIncome bookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();
        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder(bookmarkIncome)
                .build();
        EditBookmarkCommand command = (EditBookmarkCommand) parser.parseCommand(EditBookmarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + BookmarkTransactionUtil
                .getEditBookmarkTransactionDescriptorDetails(descriptor), incomeUiStateStub);
        assertEquals(new EditBookmarkIncomeCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editBookmarkWhenExpensesTab() throws Exception {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder(bookmarkExpense)
                .build();
        EditBookmarkCommand command = (EditBookmarkCommand) parser.parseCommand(EditBookmarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + BookmarkTransactionUtil
                .getEditBookmarkTransactionDescriptorDetails(descriptor), expensesUiStateStub);
        assertEquals(new EditBookmarkExpenseCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editBookmarkWhenAnalyticsTab() {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder().buildBookmarkExpense();
        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder(bookmarkExpense)
                .build();
        assertThrows(ParseException.class, () -> parser.parseCommand(EditBookmarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " "
                + BookmarkTransactionUtil
                .getEditBookmarkTransactionDescriptorDetails(descriptor), overviewUiStateStub));
    }

    @Test
    public void parseCommand_convertBookmarkWhenOverviewTab() {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                ConvertBookmarkCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), overviewUiStateStub));
    }

    @Test
    public void parseCommand_convertBookmarkWhenIncomesTab() throws Exception {
        Date testDate = new Date(VALID_DATE_INTERNSHIP);
        ConvertBookmarkCommand command = (ConvertBookmarkCommand) parser.parseCommand(
                ConvertBookmarkCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + PREFIX_DATE + VALID_DATE_INTERNSHIP,
                incomeUiStateStub);
        assertEquals(new ConvertBookmarkIncomeCommand(INDEX_FIRST, testDate), command);
    }

    @Test
    public void parseCommand_convertBookmarkWhenExpensesTab() throws Exception {
        Date testDate = new Date(VALID_DATE_INTERNSHIP);
        ConvertBookmarkCommand command = (ConvertBookmarkCommand) parser.parseCommand(
                ConvertBookmarkCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + PREFIX_DATE + VALID_DATE_INTERNSHIP,
                expensesUiStateStub);
        assertEquals(new ConvertBookmarkExpenseCommand(INDEX_FIRST, testDate), command);
    }

    @Test
    public void parseCommand_convertBookmarkWhenAnalyticsTab() {
        assertThrows(ParseException.class, () -> parser.parseCommand(
                ConvertBookmarkCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), analyticsUiStateStub));
    }

    @Test
    public void parseCommand_exitWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, overviewUiStateStub) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD,
                overviewUiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, incomeUiStateStub) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD,
                incomeUiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, expensesUiStateStub) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD,
                expensesUiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, analyticsUiStateStub) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD,
                analyticsUiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitWithArgsWhenOverviewTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ExitCommand.COMMAND_WORD + "a", overviewUiStateStub));
    }

    @Test
    public void parseCommand_exitWithArgsWhenIncomeTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ExitCommand.COMMAND_WORD + "a", incomeUiStateStub));
    }

    @Test
    public void parseCommand_exitWithArgsWhenExpensesTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ExitCommand.COMMAND_WORD + "a", expensesUiStateStub));
    }

    @Test
    public void parseCommand_exitWithArgsWhenAnalyticsTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ExitCommand.COMMAND_WORD + "a", analyticsUiStateStub));
    }

    @Test
    public void parseCommand_findWhenOverviewTab() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " t/" + keywords.stream()
                        .collect(Collectors.joining(" t/")), overviewUiStateStub);
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(new TitleContainsKeyphrasesPredicate(keywords));
        assertEquals(new FindCommand(predicateList), command);
    }

    @Test
    public void parseCommand_findWhenIncomeTab() throws Exception {
        List<String> keyphrases = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " t/" + keyphrases.stream()
                        .collect(Collectors.joining(" t/")), incomeUiStateStub);
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(new TitleContainsKeyphrasesPredicate(keyphrases));
        assertEquals(new FindCommand(predicateList), command);
    }

    @Test
    public void parseCommand_findWhenExpensesTab() throws Exception {
        List<String> keyphrases = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " t/" + keyphrases.stream()
                        .collect(Collectors.joining(" t/")), expensesUiStateStub);
        List<Predicate<Transaction>> predicateList = new ArrayList<>();
        predicateList.add(new TitleContainsKeyphrasesPredicate(keyphrases));
        assertEquals(new FindCommand(predicateList), command);
    }

    @Test
    public void parseCommand_findWhenAnalyticsTab() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        assertThrows(ParseException.class, () -> parser.parseCommand(
                FindCommand.COMMAND_WORD + " t/" + keywords.stream()
                        .collect(Collectors.joining(" t/")), analyticsUiStateStub));
    }

    @Test
    public void parseCommand_helpWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, overviewUiStateStub) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD,
                overviewUiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, incomeUiStateStub) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD,
                incomeUiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, expensesUiStateStub) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD,
                expensesUiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, analyticsUiStateStub) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD,
                analyticsUiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpWithArgsWhenOverviewTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(HelpCommand.COMMAND_WORD + "a", overviewUiStateStub));
    }

    @Test
    public void parseCommand_helpWithArgsWhenIncomeTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(HelpCommand.COMMAND_WORD + "a", incomeUiStateStub));
    }

    @Test
    public void parseCommand_helpWithArgsWhenExpensesTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(HelpCommand.COMMAND_WORD + "a", expensesUiStateStub));
    }

    @Test
    public void parseCommand_helpWithArgsWhenAnalyticsTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(HelpCommand.COMMAND_WORD + "a", analyticsUiStateStub));
    }

    @Test
    public void parseCommand_listWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD,
                overviewUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, incomeUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD,
                incomeUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, expensesUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD,
                expensesUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listWhenAnalyticsTab() {
        assertThrows(ParseException.class, () -> parser.parseCommand(ListCommand.COMMAND_WORD, analyticsUiStateStub));
    }

    @Test
    public void parseCommand_listWithArgsWhenOverviewTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListCommand.COMMAND_WORD + "a", overviewUiStateStub));
    }

    @Test
    public void parseCommand_listWithArgsWhenIncomeTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListCommand.COMMAND_WORD + "a", incomeUiStateStub));
    }

    @Test
    public void parseCommand_listWithArgsWhenExpensesTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListCommand.COMMAND_WORD + "a", expensesUiStateStub));
    }

    @Test
    public void parseCommand_listWithArgsWhenAnalyticsTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListCommand.COMMAND_WORD + "a", analyticsUiStateStub));
    }

    @Test
    public void parseCommand_listTransactionWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD,
                overviewUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listTransactionWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD, incomeUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD,
                incomeUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listTransactionWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD, expensesUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD,
                expensesUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listTransactionWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD, analyticsUiStateStub)
                instanceof ListTransactionCommand);
        assertTrue(parser.parseCommand(ListTransactionCommand.COMMAND_WORD,
                analyticsUiStateStub) instanceof ListTransactionCommand);
    }

    @Test
    public void parseCommand_listTransactionWithArgsWhenOverviewTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListTransactionCommand.COMMAND_WORD + "a", overviewUiStateStub));
    }

    @Test
    public void parseCommand_listTransactionWithArgsWhenIncomeTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListTransactionCommand.COMMAND_WORD + "a", incomeUiStateStub));
    }

    @Test
    public void parseCommand_listTransactionWithArgsWhenExpensesTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListTransactionCommand.COMMAND_WORD + "a", expensesUiStateStub));
    }

    @Test
    public void parseCommand_listTransactionWithArgsWhenAnalyticsTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListTransactionCommand.COMMAND_WORD + "a", analyticsUiStateStub));
    }

    @Test
    public void parseCommand_listExpenseWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD,
                overviewUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listExpenseWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, incomeUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD,
                incomeUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listExpenseWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, expensesUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD,
                expensesUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listExpenseWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, analyticsUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD,
                analyticsUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listExpenseWithArgsWhenOverviewTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListExpenseCommand.COMMAND_WORD + "a", overviewUiStateStub));
    }

    @Test
    public void parseCommand_listExpenseWithArgsWhenIncomeTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListExpenseCommand.COMMAND_WORD + "a", incomeUiStateStub));
    }

    @Test
    public void parseCommand_listExpenseWithArgsWhenExpensesTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListExpenseCommand.COMMAND_WORD + "a", expensesUiStateStub));
    }

    @Test
    public void parseCommand_listExpenseWithArgsWhenAnalyticsTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListExpenseCommand.COMMAND_WORD + "a", analyticsUiStateStub));
    }

    @Test
    public void parseCommand_listIncomeWhenOverviewTab() throws Exception {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD,
                overviewUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listIncomeWhenIncomeTab() throws Exception {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, incomeUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD,
                incomeUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listIncomeWhenExpensesTab() throws Exception {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, expensesUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD,
                expensesUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listIncomeWhenAnalyticsTab() throws Exception {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, analyticsUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD,
                analyticsUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_listIncomeWithArgsWhenOverviewTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListIncomeCommand.COMMAND_WORD + "a", overviewUiStateStub));
    }

    @Test
    public void parseCommand_listIncomeWithArgsWhenIncomeTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListIncomeCommand.COMMAND_WORD + "a", incomeUiStateStub));
    }

    @Test
    public void parseCommand_listIncomeWithArgsWhenExpensesTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListIncomeCommand.COMMAND_WORD + "a", expensesUiStateStub));
    }

    @Test
    public void parseCommand_listIncomeWithArgsWhenAnalyticsTab() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListIncomeCommand.COMMAND_WORD + "a", analyticsUiStateStub));
    }

    @Test
    public void parseCommand_tabWhenOverviewTab() throws Exception {
        TabCommand command = (TabCommand) parser.parseCommand(
                TabCommand.COMMAND_WORD + " 1", overviewUiStateStub);
        assertEquals(new TabCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_tabWhenIncomeTab() throws Exception {
        TabCommand command = (TabCommand) parser.parseCommand(
                TabCommand.COMMAND_WORD + " 1", incomeUiStateStub);
        assertEquals(new TabCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_tabWhenExpensesTab() throws Exception {
        TabCommand command = (TabCommand) parser.parseCommand(
                TabCommand.COMMAND_WORD + " 1", expensesUiStateStub);
        assertEquals(new TabCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_tabWhenAnalyticsTab() throws Exception {
        TabCommand command = (TabCommand) parser.parseCommand(
                TabCommand.COMMAND_WORD + " 1", analyticsUiStateStub);
        assertEquals(new TabCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_unrecognisedInputWhenOverviewTab_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", overviewUiStateStub));
    }

    @Test
    public void parseCommand_unrecognisedInputWhenIncomeTab_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", incomeUiStateStub));
    }

    @Test
    public void parseCommand_unrecognisedInputWhenExpensesTab_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", expensesUiStateStub));
    }

    @Test
    public void parseCommand_unrecognisedInputWhenAnalyticsTab_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", analyticsUiStateStub));
    }

    @Test
    public void parseCommand_unknownCommandWhenOverviewTab_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", overviewUiStateStub));
    }

    @Test
    public void parseCommand_unknownCommandWhenIncomeTab_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", incomeUiStateStub));
    }

    @Test
    public void parseCommand_unknownCommandWhenExpensesTab_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", expensesUiStateStub));
    }

    @Test
    public void parseCommand_unknownCommandWhenAnalyticsTab_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", analyticsUiStateStub));
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
    public static class IncomeUiStateStub extends UiStateStub {
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
}
