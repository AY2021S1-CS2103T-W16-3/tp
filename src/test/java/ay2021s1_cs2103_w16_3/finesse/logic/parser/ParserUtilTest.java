package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE_FROM;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.BookmarkTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

public class ParserUtilTest {
    private static final String INVALID_TITLE = "\u2416Bubble Tea";
    private static final String INVALID_AMOUNT = "+651234";
    private static final String INVALID_DATE = "example.com";
    private static final String INVALID_CATEGORY = "\u2416friend";

    private static final String VALID_TITLE = "Phone Bill";
    private static final String VALID_AMOUNT = "123456";
    private static final String VALID_DATE = "01/01/2020";
    private static final String VALID_CATEGORY_1 = "friend";
    private static final String VALID_CATEGORY_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    private static final String EXCEPTION_MESSAGE = "This is an exception message.";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseTitle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((String) null));
    }

    @Test
    public void parseTitle_emptyValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTitle(WHITESPACE));
    }

    @Test
    public void parseTitle_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTitle(INVALID_TITLE));
    }

    @Test
    public void parseTitle_validValueWithoutWhitespace_returnsTitle() throws Exception {
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(VALID_TITLE));
    }

    @Test
    public void parseTitle_validValueWithWhitespace_returnsTrimmedTitle() throws Exception {
        String titleWithWhitespace = WHITESPACE + VALID_TITLE + WHITESPACE;
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(titleWithWhitespace));
    }

    @Test
    public void parseTitleWithAdditionalWhitespace_returnsTitle() throws Exception {
        String moreThanOneWhitespace = WHITESPACE + WHITESPACE + WHITESPACE;
        String titleWithExtraWhitespaceBetweenWords = VALID_TITLE.replaceAll(WHITESPACE, moreThanOneWhitespace);
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle,
                ParserUtil.parseTitleAndTrimBetweenWords(titleWithExtraWhitespaceBetweenWords));
    }

    @Test
    public void parseAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmount((String) null));
    }

    @Test
    public void parseAmount_emptyValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(WHITESPACE));
    }

    @Test
    public void parseAmount_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_AMOUNT));
    }

    @Test
    public void parseAmount_validValueWithoutWhitespace_returnsAmount() throws Exception {
        Amount expectedAmount = new Amount(VALID_AMOUNT);
        assertEquals(expectedAmount, ParserUtil.parseAmount(VALID_AMOUNT));
    }

    @Test
    public void parseAmount_validValueWithWhitespace_returnsTrimmedAmount() throws Exception {
        String amountWithWhitespace = WHITESPACE + VALID_AMOUNT + WHITESPACE;
        Amount expectedAmount = new Amount(VALID_AMOUNT);
        assertEquals(expectedAmount, ParserUtil.parseAmount(amountWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_emptyValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(WHITESPACE));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseCategory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategory(null));
    }

    @Test
    public void parseCategory_emptyValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(WHITESPACE));
    }

    @Test
    public void parseCategory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithoutWhitespace_returnsCategory() throws Exception {
        Category expectedCategory = new Category(VALID_CATEGORY_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(VALID_CATEGORY_1));
    }

    @Test
    public void parseCategory_validValueWithWhitespace_returnsTrimmedCategory() throws Exception {
        String categoryWithWhitespace = WHITESPACE + VALID_CATEGORY_1 + WHITESPACE;
        Category expectedCategory = new Category(VALID_CATEGORY_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(categoryWithWhitespace));
    }

    @Test
    public void parseCategories_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategories(null));
    }

    @Test
    public void parseCategories_collectionWithInvalidCategories_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategories(
                Arrays.asList(VALID_CATEGORY_1, INVALID_CATEGORY)));
    }

    @Test
    public void parseCategories_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseCategories(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseCategories_collectionWithValidCategories_returnsCategorySet() throws Exception {
        Set<Category> actualCategorySet = ParserUtil.parseCategories(Arrays.asList(VALID_CATEGORY_1, VALID_CATEGORY_2));
        Set<Category> expectedCategorySet = new HashSet<Category>(Arrays.asList(new Category(VALID_CATEGORY_1),
                new Category(VALID_CATEGORY_2)));

        assertEquals(expectedCategorySet, actualCategorySet);
    }

    @Test
    public void parseBookmarkTransactionBuilder_missingTitle_throwsParseException() {
        String args = " " + PREFIX_AMOUNT + VALID_AMOUNT;
        assertThrows(ParseException.class, () ->
                ParserUtil.parseBookmarkTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseBookmarkTransactionBuilder_missingAmount_throwsParseException() {
        String args = " " + PREFIX_TITLE + VALID_TITLE;
        assertThrows(ParseException.class, () ->
                ParserUtil.parseBookmarkTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseBookmarkTransactionBuilder_nonEmptyPreamble_throwsParseException() {
        String args = "hello " + PREFIX_TITLE + VALID_TITLE + " " + PREFIX_AMOUNT + VALID_AMOUNT;
        assertThrows(ParseException.class, () ->
                ParserUtil.parseBookmarkTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseBookmarkTransactionBuilder_moreThanOneTitle_throwsParseException() {
        String args = " " + PREFIX_TITLE + VALID_TITLE + " " + PREFIX_TITLE + VALID_TITLE;
        assertThrows(ParseException.class, () ->
                ParserUtil.parseBookmarkTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseBookmarkTransactionBuilder_moreThanOneAmount_throwsParseException() {
        String args = " " + PREFIX_AMOUNT + VALID_AMOUNT + " " + PREFIX_AMOUNT + VALID_AMOUNT;
        assertThrows(ParseException.class, () ->
                ParserUtil.parseBookmarkTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseBookmarkTransactionBuilder_invalidPrefixPresent_throwsParseException() {
        String args = " " + PREFIX_TITLE + VALID_TITLE + " " + PREFIX_AMOUNT + VALID_AMOUNT
                + " " + PREFIX_DATE + VALID_DATE;
        assertThrows(ParseException.class, () ->
                ParserUtil.parseBookmarkTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseBookmarkTransactionBuilder_validInputs_returnsBookmarkTransactionBuilder() throws ParseException {
        String args = " " + PREFIX_TITLE + VALID_TITLE + " " + PREFIX_AMOUNT + VALID_AMOUNT
                + " " + PREFIX_CATEGORY + VALID_CATEGORY_1;
        Title title = new Title(VALID_TITLE);
        Amount amount = new Amount(VALID_AMOUNT);
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(new Category(VALID_CATEGORY_1));
        assertEquals(new BookmarkTransactionBuilder(title, amount, categorySet),
                ParserUtil.parseBookmarkTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseTransactionBuilder_missingTitle_throwsParseException() {
        String args = " " + PREFIX_AMOUNT + VALID_AMOUNT;
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseTransactionBuilder_missingAmount_throwsParseException() {
        String args = " " + PREFIX_TITLE + VALID_TITLE;
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseTransactionBuilder_nonEmptyPreamble_throwsParseException() {
        String args = "hello " + PREFIX_TITLE + VALID_TITLE + " " + PREFIX_AMOUNT + VALID_AMOUNT;
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseTransactionBuilder_moreThanOneTitle_throwsParseException() {
        String args = " " + PREFIX_TITLE + VALID_TITLE + " " + PREFIX_TITLE + VALID_TITLE;
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseTransactionBuilder_moreThanOneAmount_throwsParseException() {
        String args = " " + PREFIX_AMOUNT + VALID_AMOUNT + " " + PREFIX_AMOUNT + VALID_AMOUNT;
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseTransactionBuilder_moreThanOneDate_throwsParseException() {
        String args = " " + PREFIX_DATE + VALID_DATE + " " + PREFIX_DATE + VALID_DATE;
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseTransactionBuilder_invalidPrefixPresent_throwsParseException() {
        String args = " " + PREFIX_TITLE + VALID_TITLE + " " + PREFIX_AMOUNT + VALID_AMOUNT
                + " " + PREFIX_DATE_FROM + VALID_DATE;
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseTransactionBuilder_validInputsWithoutDate_returnsTransactionBuilder() throws ParseException {
        String args = " " + PREFIX_TITLE + VALID_TITLE + " " + PREFIX_AMOUNT + VALID_AMOUNT
                + " " + PREFIX_CATEGORY + VALID_CATEGORY_1;
        Title title = new Title(VALID_TITLE);
        Amount amount = new Amount(VALID_AMOUNT);
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(new Category(VALID_CATEGORY_1));
        Date date = Date.getCurrentDate();
        assertEquals(new TransactionBuilder(title, amount, date, categorySet),
                ParserUtil.parseTransactionBuilder(args, EXCEPTION_MESSAGE));
    }

    @Test
    public void parseTransactionBuilder_validInputsWithDate_returnsTransactionBuilder() throws ParseException {
        String args = " " + PREFIX_TITLE + VALID_TITLE + " " + PREFIX_AMOUNT + VALID_AMOUNT
                + " " + PREFIX_DATE + VALID_DATE + " " + PREFIX_CATEGORY + VALID_CATEGORY_1;
        Title title = new Title(VALID_TITLE);
        Amount amount = new Amount(VALID_AMOUNT);
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(new Category(VALID_CATEGORY_1));
        Date date = new Date(VALID_DATE);
        assertEquals(new TransactionBuilder(title, amount, date, categorySet),
                ParserUtil.parseTransactionBuilder(args, EXCEPTION_MESSAGE));
    }
}
