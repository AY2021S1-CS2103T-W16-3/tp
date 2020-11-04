package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ArgumentMultimap;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ArgumentTokenizer;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Parses input arguments and creates a new ConvertBookmarkIncomeCommand object
 */
public class ConvertBookmarkIncomeCommandParser implements Parser<ConvertBookmarkIncomeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ConvertBookmarkIncomeCommand
     * and returns an ConvertBookmarkIncomeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConvertBookmarkIncomeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        Date date;

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            if (argMultimap.moreThanOneValuePresent(PREFIX_DATE)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_DATE_CONSTRAINTS));
            } else {
                date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            }
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate currentDate = LocalDate.now();
            date = new Date(dateTimeFormatter.format(currentDate));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ConvertBookmarkIncomeCommand.MESSAGE_USAGE), pe);
        }

        return new ConvertBookmarkIncomeCommand(index, date);
    }

}
