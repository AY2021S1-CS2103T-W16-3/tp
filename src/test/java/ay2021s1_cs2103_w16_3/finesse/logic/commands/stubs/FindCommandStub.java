package ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TitleContainsKeywordsPredicate;

/**
 * A {@code FindCommand} stub that takes in a {@code TitleContainsKeywordsPredicate}
 * and returns the same {@code TitleContainsKeywordsPredicate} when its {@code getPredicate}
 * method is called. All of its other methods fail when called.
 */
public class FindCommandStub extends FindCommand {
    private final TitleContainsKeywordsPredicate predicate;

    /**
     * Constructs a {@code DeleteCommandStub} object.
     *
     * @param predicate The predicate to filter the displayed list by.
     */
    public FindCommandStub(TitleContainsKeywordsPredicate predicate) {
        super(predicate);
        this.predicate = predicate;
    }

    @Override
    protected TitleContainsKeywordsPredicate getPredicate() {
        return predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean equals(Object other) {
        throw new AssertionError("This method should not be called.");
    }
}
