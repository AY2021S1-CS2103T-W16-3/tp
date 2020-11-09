package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;

import java.util.Collections;

public class ArgumentTokenizerTest {

    private final Prefix unknownPrefix = new Prefix("--u");

    /**
     * Calls the method {@code ArgumentTokenizer::tokenize} using the set of {@Prefix} in {@CliSyntax}.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}.
     * @param prefixes   Subset of {@Prefix} in {@CliSyntax} that are considered valid. Any other prefixes is invalid
     *                   and thus should throw ParseException.
     * @return           ArgumentMultimap object that maps prefixes to their arguments.
     * @throws           ParseException if any invalid prefixes are detected.
     */
    private ArgumentMultimap tokenize(String argsString, Prefix... prefixes) throws ParseException {
        return ArgumentTokenizer.tokenize(argsString, "This is an exception message.", prefixes);
    }

    @Test
    public void tokenize_emptyArgsString_noValues() throws ParseException {
        String argsString = "  ";
        ArgumentMultimap argMultimapFromCliSyntaxPrefixes = tokenize(argsString, PREFIX_TITLE);

        assertPreambleEmpty(argMultimapFromCliSyntaxPrefixes);
        assertArgumentAbsent(argMultimapFromCliSyntaxPrefixes, PREFIX_TITLE);
    }

    private void assertPreamblePresent(ArgumentMultimap argMultimap, String expectedPreamble) {
        assertEquals(expectedPreamble, argMultimap.getPreamble());
    }

    private void assertPreambleEmpty(ArgumentMultimap argMultimap) {
        assertTrue(argMultimap.getPreamble().isEmpty());
    }

    /**
     * Asserts all the arguments in {@code argMultimap} with {@code prefix} match the {@code expectedValues}
     * and only the last value is returned upon calling {@code ArgumentMultimap#getValue(Prefix)}.
     */
    private void assertArgumentPresent(ArgumentMultimap argMultimap, Prefix prefix, String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argMultimap.getValue(prefix).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argMultimap.getAllValues(prefix).size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMultimap.getAllValues(prefix).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMultimap argMultimap, Prefix prefix) {
        assertFalse(argMultimap.getValue(prefix).isPresent());
    }

    @Test
    public void tokenize_noPrefixes_allTakenAsPreamble() throws ParseException {
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        ArgumentMultimap argMultimapFromCliSyntaxPrefixes = tokenize(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(argMultimapFromCliSyntaxPrefixes, argsString.trim());
    }

    @Test
    public void tokenize_oneArgument() throws ParseException {
        ArgumentMultimap argMultimap;

        // Preamble present
        String argsString = "  Some preamble string t/ Argument value ";
        argMultimap = tokenize(argsString, PREFIX_TITLE);
        assertPreamblePresent(argMultimap, "Some preamble string");
        assertArgumentPresent(argMultimap, PREFIX_TITLE, "Argument value");

        // No preamble
        argsString = " t/   Argument value ";
        argMultimap = tokenize(argsString, PREFIX_TITLE);
        assertPreambleEmpty(argMultimap);
        assertArgumentPresent(argMultimap, PREFIX_TITLE, "Argument value");

    }

    @Test
    public void tokenize_multipleArguments() throws ParseException {
        // Only two arguments are present
        String argsString = "SomePreambleString t/ dashT-Value a/pSlash value";
        ArgumentMultimap argMultimap = tokenize(argsString, PREFIX_TITLE, PREFIX_AMOUNT, PREFIX_DATE);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, PREFIX_AMOUNT, "pSlash value");
        assertArgumentPresent(argMultimap, PREFIX_TITLE, "dashT-Value");
        assertArgumentAbsent(argMultimap, PREFIX_DATE);

        // All three arguments are present
        argsString = "Different Preamble String d/111 t/ dashT-Value a/pSlash value";
        argMultimap = tokenize(argsString, PREFIX_AMOUNT, PREFIX_TITLE, PREFIX_DATE);
        assertPreamblePresent(argMultimap, "Different Preamble String");
        assertArgumentPresent(argMultimap, PREFIX_AMOUNT, "pSlash value");
        assertArgumentPresent(argMultimap, PREFIX_TITLE, "dashT-Value");
        assertArgumentPresent(argMultimap, PREFIX_DATE, "111");

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure ArgumentMultimap is correctly reset
        // (i.e. no stale values from the previous tokenizing remain)
        argsString = "";
        argMultimap = tokenize(argsString, PREFIX_AMOUNT, PREFIX_TITLE, PREFIX_DATE);
        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, PREFIX_AMOUNT);

        /* Also covers: testing for prefixes not specified as a prefix */

        // Prefixes not previously given to the tokenizer should not return any values
        argsString = unknownPrefix + "some value";
        argMultimap = tokenize(argsString, PREFIX_AMOUNT, PREFIX_TITLE, PREFIX_DATE);
        assertArgumentAbsent(argMultimap, unknownPrefix);
        assertPreamblePresent(argMultimap, argsString); // Unknown prefix is taken as part of preamble
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() throws ParseException {
        // Two arguments repeated, some have empty values
        String argsString = "SomePreambleString t/ dashT-Value d/ d/ t/ another dashT value a/ pSlash value t/";
        ArgumentMultimap argMultimap = tokenize(argsString, PREFIX_AMOUNT, PREFIX_TITLE, PREFIX_DATE);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, PREFIX_AMOUNT, "pSlash value");
        assertArgumentPresent(argMultimap, PREFIX_TITLE, "dashT-Value", "another dashT value", "");
        assertArgumentPresent(argMultimap, PREFIX_DATE, "", "");
    }

    @Test
    public void tokenize_multipleArgumentsJoined() throws ParseException {
        String argsString = "SomePreambleStringa/ pSlash joinedt/joined t/ not joinedd/joined";
        ArgumentMultimap argMultimap = tokenize(argsString, PREFIX_AMOUNT, PREFIX_TITLE, PREFIX_DATE);
        assertPreamblePresent(argMultimap, "SomePreambleStringa/ pSlash joinedt/joined");
        assertArgumentAbsent(argMultimap, PREFIX_AMOUNT);
        assertArgumentPresent(argMultimap, PREFIX_TITLE, "not joinedd/joined");
        assertArgumentAbsent(argMultimap, PREFIX_DATE);
    }

    @Test
    public void tokenize_invalidArgumentPresent_throwsParseException() {
        String argsString = "SomePreableString t/ dashT-Value d/ invalid-hatQ a/ pSlash-value";
        assertThrows(ParseException.class, () -> tokenize(argsString, PREFIX_AMOUNT, PREFIX_TITLE));
    }

    @Test
    public void equalsMethod() {
        Prefix aaa = new Prefix("aaa");

        assertEquals(aaa, aaa);
        assertEquals(aaa, new Prefix("aaa"));

        assertNotEquals(aaa, "aaa");
        assertNotEquals(aaa, new Prefix("aab"));
    }

}
