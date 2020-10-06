package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a Transaction's date in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format dd/mm/yyyy";
    public static final String VALIDATION_REGEX = "^\\d{2}/\\d{2}/\\d{4}$";
    public static final SimpleDateFormat VALIDATION_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    static {
        // initialize VALIDATION_FORMAT to strict mode
        VALIDATION_FORMAT.setLenient(false);
    }

    public final String value;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            VALIDATION_FORMAT.parse(test);
            return test.matches(VALIDATION_REGEX);
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
