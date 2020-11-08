package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.util.StringUtil.isEmptyString;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT_FROM;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT_TO;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE_FROM;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE_TO;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static ay2021s1_cs2103_w16_3.finesse.model.category.Category.MESSAGE_EMPTY_CATEGORY;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.HasCategoriesPredicate;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.HasExactAmountPredicate;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.InAmountRangePredicate;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.InDateRangePredicate;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.OnExactDatePredicate;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.predicates.TitleContainsKeyphrasesPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MESSAGE_AMOUNT_SEARCH_USAGE =
            "To search for transactions with an exact amount, please use " + PREFIX_AMOUNT
                    + " to indicate the amount.\n"
                    + "To search for transactions within a range of amounts, please use " + PREFIX_AMOUNT_FROM
                    + " to indicate the lower bound, and/or " + PREFIX_AMOUNT_TO + " to indicate the upper bound.";

    public static final String MESSAGE_DATE_SEARCH_USAGE =
            "To search for transactions with an exact date, please use " + PREFIX_DATE + " to indicate the date.\n"
                    + "To search for transactions within a range of dates, please use " + PREFIX_DATE_FROM
                    + " to indicate the lower bound, and/or " + PREFIX_DATE_TO + " to indicate the upper bound.";

    public static final String MESSAGE_AMOUNT_SEARCH_CONSTRAINTS =
            PREFIX_AMOUNT + " cannot be used concurrently with " + PREFIX_AMOUNT_FROM
                    + " and/or " + PREFIX_AMOUNT_TO + ".\n" + MESSAGE_AMOUNT_SEARCH_USAGE;

    public static final String MESSAGE_DATE_SEARCH_CONSTRAINTS =
            PREFIX_DATE + " cannot be used concurrently with " + PREFIX_DATE_FROM
                    + " and/or " + PREFIX_DATE_TO + ".\n" + MESSAGE_DATE_SEARCH_USAGE;

    public static final String MESSAGE_AMOUNT_DUPLICATE_CONSTRAINTS =
            "Only one amount can be input for a search of transactions containing an exact amount.\n"
                    + MESSAGE_AMOUNT_SEARCH_USAGE;

    public static final String MESSAGE_DATE_DUPLICATE_CONSTRAINTS =
            "Only one date can be input for a search of transactions occurring on an exact date.\n"
                    + MESSAGE_DATE_SEARCH_USAGE;

    public static final String MESSAGE_AMOUNT_FROM_CONSTRAINTS =
            "Only one amount can be input as the lower bound for a search of transactions within a range of amounts.\n"
                    + MESSAGE_AMOUNT_SEARCH_USAGE;

    public static final String MESSAGE_AMOUNT_TO_CONSTRAINTS =
            "Only one amount can be input as the upper bound for a search of transactions within a range of amounts.\n"
                    + MESSAGE_AMOUNT_SEARCH_USAGE;

    public static final String MESSAGE_AMOUNT_RANGE_CONSTRAINTS =
            "The lower bound for the amount range cannot be larger than the upper bound.";

    public static final String MESSAGE_DATE_FROM_CONSTRAINTS =
            "Only one date can be input as the lower bound for a search of transactions "
                    + "occurring within a range of dates.\n" + MESSAGE_DATE_SEARCH_USAGE;

    public static final String MESSAGE_DATE_TO_CONSTRAINTS =
            "Only one date can be input as the upper bound for a search of transactions "
                    + "occurring within a range of dates.\n" + MESSAGE_DATE_SEARCH_USAGE;

    public static final String MESSAGE_DATE_RANGE_CONSTRAINTS =
            "The lower bound for the date range cannot be later than the upper bound.";

    public static final String MESSAGE_EMPTY_TITLE_KEYPHRASE = "Title keyphrases cannot be empty.";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap;
        try {
            argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.getAllPrefixes());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), pe);
        }

        List<Predicate<Transaction>> predicateList = new ArrayList<>();

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_AMOUNT)) {
            throw new ParseException(MESSAGE_AMOUNT_DUPLICATE_CONSTRAINTS);
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_DATE)) {
            throw new ParseException(MESSAGE_DATE_DUPLICATE_CONSTRAINTS);
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_AMOUNT_FROM)) {
            throw new ParseException(MESSAGE_AMOUNT_FROM_CONSTRAINTS);
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_AMOUNT_TO)) {
            throw new ParseException(MESSAGE_AMOUNT_TO_CONSTRAINTS);
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_DATE_FROM)) {
            throw new ParseException(MESSAGE_DATE_FROM_CONSTRAINTS);
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_DATE_TO)) {
            throw new ParseException(MESSAGE_DATE_TO_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            List<String> titleKeyphrases = argMultimap.getAllValues(PREFIX_TITLE);
            for (String s: titleKeyphrases) {
                if (isEmptyString(s)) {
                    throw new ParseException(MESSAGE_EMPTY_TITLE_KEYPHRASE);
                }
            }
            predicateList.add(new TitleContainsKeyphrasesPredicate(titleKeyphrases));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            if (argMultimap.getValue(PREFIX_AMOUNT_FROM).isPresent()
                    || argMultimap.getValue(PREFIX_AMOUNT_TO).isPresent()) {
                throw new ParseException(MESSAGE_AMOUNT_SEARCH_CONSTRAINTS);
            }
            Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            predicateList.add(new HasExactAmountPredicate(amount));
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            if (argMultimap.getValue(PREFIX_DATE_FROM).isPresent()
                    || argMultimap.getValue(PREFIX_DATE_TO).isPresent()) {
                throw new ParseException(MESSAGE_DATE_SEARCH_CONSTRAINTS);
            }
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            predicateList.add(new OnExactDatePredicate(date));
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            List<String> categories = argMultimap.getAllValues(PREFIX_CATEGORY);
            for (String s: categories) {
                if (isEmptyString(s)) {
                    throw new ParseException(MESSAGE_EMPTY_CATEGORY);
                }
            }
            predicateList.add(new HasCategoriesPredicate(categories));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT_FROM).isPresent()
                || argMultimap.getValue(PREFIX_AMOUNT_TO).isPresent()) {
            Amount amountFrom = null;
            Amount amountTo = null;
            if (argMultimap.getValue(PREFIX_AMOUNT_FROM).isPresent()) {
                amountFrom = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT_FROM).get());
            }
            if (argMultimap.getValue(PREFIX_AMOUNT_TO).isPresent()) {
                amountTo = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT_TO).get());
            }
            if (amountFrom != null && amountTo != null && amountFrom.compareTo(amountTo) > 0) {
                throw new ParseException(MESSAGE_AMOUNT_RANGE_CONSTRAINTS);
            }
            predicateList.add(new InAmountRangePredicate(Optional.ofNullable(amountFrom),
                    Optional.ofNullable(amountTo)));
        }

        if (argMultimap.getValue(PREFIX_DATE_FROM).isPresent()
                || argMultimap.getValue(PREFIX_DATE_TO).isPresent()) {
            Date dateFrom = null;
            Date dateTo = null;
            if (argMultimap.getValue(PREFIX_DATE_FROM).isPresent()) {
                dateFrom = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_FROM).get());
            }
            if (argMultimap.getValue(PREFIX_DATE_TO).isPresent()) {
                dateTo = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_TO).get());
            }
            if (dateFrom != null && dateTo != null && dateFrom.compareTo(dateTo) > 0) {
                throw new ParseException(MESSAGE_DATE_RANGE_CONSTRAINTS);
            }
            predicateList.add(new InDateRangePredicate(Optional.ofNullable(dateFrom),
                    Optional.ofNullable(dateTo)));
        }

        if (predicateList.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicateList);
    }

}
