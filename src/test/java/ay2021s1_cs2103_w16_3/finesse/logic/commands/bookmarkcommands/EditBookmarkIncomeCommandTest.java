package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmarkcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_PART_TIME;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_PART_TIME;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showBookmarkIncomeAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs.EditBookmarkCommandStub;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.BookmarkTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncomeList;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditBookmarkTransactionDescriptorBuilder;

public class EditBookmarkIncomeCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        BookmarkIncome editedBookmarkIncome = new BookmarkTransactionBuilder().buildBookmarkIncome();
        EditBookmarkTransactionDescriptor descriptor =
                new EditBookmarkTransactionDescriptorBuilder(editedBookmarkIncome).build();
        EditBookmarkCommandStub superCommand = new EditBookmarkCommandStub(INDEX_FIRST, descriptor);
        EditBookmarkIncomeCommand editBookmarkIncomeCommand =
                new EditBookmarkIncomeCommand(superCommand);

        String expectedMessage = String.format(EditBookmarkIncomeCommand.MESSAGE_EDIT_BOOKMARK_INCOME_SUCCESS,
                editedBookmarkIncome);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setBookmarkIncome(model.getFilteredBookmarkIncomeList().get(0), editedBookmarkIncome);

        assertCommandSuccess(editBookmarkIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastIncome = Index.fromOneBased(model.getFilteredBookmarkIncomeList().size());
        BookmarkIncome lastIncome = model.getFilteredBookmarkIncomeList().get(indexLastIncome.getZeroBased());

        BookmarkTransactionBuilder incomeInList = new BookmarkTransactionBuilder(lastIncome);
        BookmarkIncome editedIncome = incomeInList.withTitle(VALID_TITLE_INTERNSHIP)
                .withAmount(VALID_AMOUNT_PART_TIME)
                .withCategories(VALID_CATEGORY_WORK).buildBookmarkIncome();

        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_INTERNSHIP).withAmount(VALID_AMOUNT_PART_TIME)
                .withCategories(VALID_CATEGORY_WORK).build();
        EditBookmarkCommandStub superCommand = new EditBookmarkCommandStub(indexLastIncome, descriptor);
        EditBookmarkIncomeCommand editBookmarkIncomeCommand =
                new EditBookmarkIncomeCommand(superCommand);

        String expectedMessage = String.format(EditBookmarkIncomeCommand.MESSAGE_EDIT_BOOKMARK_INCOME_SUCCESS,
                editedIncome);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setBookmarkIncome(lastIncome, editedIncome);

        assertCommandSuccess(editBookmarkIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidBookmarkIncomeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkIncomeList().size() + 1);
        EditBookmarkTransactionDescriptor descriptor = new EditBookmarkTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_PART_TIME).build();
        EditBookmarkCommandStub superCommand = new EditBookmarkCommandStub(outOfBoundIndex, descriptor);
        EditBookmarkIncomeCommand editBookmarkIncomeCommand =
                new EditBookmarkIncomeCommand(superCommand);

        assertCommandFailure(editBookmarkIncomeCommand, model, MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered bookmark income list where index is larger than size of filtered bookmark income list,
     * but smaller than size of finance tracker
     */
    @Test
    public void execute_invalidBookmarkIncomeIndexFilteredList_failure() {
        showBookmarkIncomeAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;

        // ensures that outOfBoundIndex is still in bounds of bookmark income list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getBookmarkIncomeList().size());

        EditBookmarkCommandStub superCommand = new EditBookmarkCommandStub(outOfBoundIndex,
                new EditBookmarkTransactionDescriptorBuilder().withTitle(VALID_TITLE_PART_TIME).build());
        EditBookmarkIncomeCommand editBookmarkIncomeCommand = new EditBookmarkIncomeCommand(superCommand);

        assertCommandFailure(editBookmarkIncomeCommand, model, MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_settingDuplicateEditedBookmarkExpense_throwsDuplicateBookmarkTransactionException() {
        BookmarkIncome editedBookmarkIncome = new BookmarkTransactionBuilder()
                .withTitle(VALID_TITLE_PART_TIME).withAmount(VALID_AMOUNT_PART_TIME)
                .buildBookmarkIncome();
        EditBookmarkTransactionDescriptor descriptor =
                new EditBookmarkTransactionDescriptorBuilder(editedBookmarkIncome).build();

        EditBookmarkCommandStub superCommand = new EditBookmarkCommandStub(INDEX_SECOND, descriptor);
        EditBookmarkIncomeCommand editBookmarkIncomeCommand = new EditBookmarkIncomeCommand(superCommand);

        assertCommandFailure(editBookmarkIncomeCommand, model,
                BookmarkIncomeList.MESSAGE_OPERATION_WOULD_RESULT_IN_DUPLICATE_BOOKMARK_INCOME);
    }

    @Test
    public void equals() {
        final EditBookmarkCommandStub superCommand = new EditBookmarkCommandStub(INDEX_FIRST,
                DESC_SPOTIFY_SUBSCRIPTION);
        final EditBookmarkIncomeCommand standardCommand =
                new EditBookmarkIncomeCommand(superCommand);

        // Same values -> returns true
        EditBookmarkTransactionDescriptor copyDescriptor =
                new EditBookmarkTransactionDescriptor(DESC_SPOTIFY_SUBSCRIPTION);
        EditBookmarkCommandStub superCommandWithSameValues = new EditBookmarkCommandStub(INDEX_FIRST, copyDescriptor);
        EditBookmarkIncomeCommand commandWithSameValues =
                new EditBookmarkIncomeCommand(superCommandWithSameValues);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different index -> returns false
        assertFalse(standardCommand
                .equals(new EditBookmarkIncomeCommand(
                        new EditBookmarkCommandStub(INDEX_SECOND, DESC_SPOTIFY_SUBSCRIPTION))));

        // Different descriptor -> returns false
        assertFalse(standardCommand
                .equals(new EditBookmarkIncomeCommand(
                        new EditBookmarkCommandStub(INDEX_FIRST, DESC_PHONE_BILL))));
    }

}
