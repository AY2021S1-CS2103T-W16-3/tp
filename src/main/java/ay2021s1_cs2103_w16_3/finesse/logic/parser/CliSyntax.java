package ay2021s1_cs2103_w16_3.finesse.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_AMOUNT = new Prefix("a/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_AMOUNT_FROM = new Prefix("af/");
    public static final Prefix PREFIX_AMOUNT_TO = new Prefix("at/");
    public static final Prefix PREFIX_DATE_FROM = new Prefix("df/");
    public static final Prefix PREFIX_DATE_TO = new Prefix("dt/");

    /**
     * Prevents instantiation of this class.
     */
    private CliSyntax() {}
}
