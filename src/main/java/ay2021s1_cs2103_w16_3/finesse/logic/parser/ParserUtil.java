package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.util.StringUtil.isEmptyString;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static ay2021s1_cs2103_w16_3.finesse.model.category.Category.MESSAGE_EMPTY_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount.MESSAGE_EMPTY_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.model.transaction.Date.MESSAGE_EMPTY_DATE;
import static ay2021s1_cs2103_w16_3.finesse.model.transaction.Title.MESSAGE_EMPTY_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.commons.util.StringUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.BookmarkTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (isEmptyString(trimmedTitle)) {
            throw new ParseException(MESSAGE_EMPTY_TITLE);
        }
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     * Any extra whitespaces between words will be removed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitleAndTrimBetweenWords(String title) throws ParseException {
        requireNonNull(title);
        String removedExtraWhitespaceTitle = title.replaceAll("\\s{2,}", " ");
        return parseTitle(removedExtraWhitespaceTitle);
    }

    /**
     * Parses a {@code String amount} into a {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (isEmptyString(trimmedAmount)) {
            throw new ParseException(MESSAGE_EMPTY_AMOUNT);
        }
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(trimmedAmount);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (isEmptyString(trimmedDate)) {
            throw new ParseException(MESSAGE_EMPTY_DATE);
        }
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (isEmptyString(trimmedCategory)) {
            throw new ParseException(MESSAGE_EMPTY_CATEGORY);
        }
        if (!Category.isValidCategoryName(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>}.
     */
    public static Set<Category> parseCategories(Collection<String> categories) throws ParseException {
        requireNonNull(categories);
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(parseCategory(categoryName));
        }
        return categorySet;
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>} if {@code categories} is non-empty.
     * If {@code categories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero categories.
     */
    public static Optional<Set<Category>> parseCategoriesForEdit(Collection<String> categories) throws ParseException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> categorySet = categories.size() == 1 && categories.contains("")
                ? Collections.emptySet() : categories;
        return Optional.of(ParserUtil.parseCategories(categorySet));
    }

    /**
     * Parses {@code String args} into a {@code BookmarkTransactionBuilder}.
     *
     * @throws ParseException if the given {@code args} do not adhere to the format of a bookmark transaction.
     */
    public static BookmarkTransactionBuilder parseBookmarkTransactionBuilder(String args, String exceptionMessage)
            throws ParseException {
        requireNonNull(args);
        requireNonNull(exceptionMessage);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, exceptionMessage, PREFIX_TITLE, PREFIX_AMOUNT,
                PREFIX_CATEGORY);

        if (!argMultimap.getPreamble().isEmpty() || !argMultimap.arePrefixesPresent(PREFIX_TITLE, PREFIX_AMOUNT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, exceptionMessage));
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_TITLE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkTransaction.MESSAGE_TITLE_CONSTRAINTS));
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_AMOUNT)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkTransaction.MESSAGE_AMOUNT_CONSTRAINTS));
        }

        Title title = ParserUtil.parseTitleAndTrimBetweenWords(argMultimap.getValue(PREFIX_TITLE).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Set<Category> categoryList = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));

        return new BookmarkTransactionBuilder(title, amount, categoryList);
    }

    /**
     * Parses {@code String args} into a {@code TransactionBuilder}.
     *
     * @throws ParseException if the given {@code args} do not adhere to the format of a transaction.
     */
    public static TransactionBuilder parseTransactionBuilder(String args, String exceptionMessage)
            throws ParseException {
        requireNonNull(args);
        requireNonNull(exceptionMessage);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, exceptionMessage, PREFIX_TITLE, PREFIX_AMOUNT,
                PREFIX_DATE, PREFIX_CATEGORY);

        if (!argMultimap.getPreamble().isEmpty() || !argMultimap.arePrefixesPresent(PREFIX_TITLE, PREFIX_AMOUNT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, exceptionMessage));
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_TITLE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_TITLE_CONSTRAINTS));
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_AMOUNT)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_AMOUNT_CONSTRAINTS));
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_DATE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_DATE_CONSTRAINTS));
        }

        Title title = ParserUtil.parseTitleAndTrimBetweenWords(argMultimap.getValue(PREFIX_TITLE).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Set<Category> categoryList = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));

        Date date;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        } else {
            date = Date.getCurrentDate();
        }

        return new TransactionBuilder(title, amount, date, categoryList);
    }

}
