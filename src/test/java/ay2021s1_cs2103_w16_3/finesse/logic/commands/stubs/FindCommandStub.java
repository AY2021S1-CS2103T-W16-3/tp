package ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;

import java.util.List;
import java.util.function.Predicate;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * A {@code FindCommand} stub that takes in a {@code TitleContainsKeyphrasesPredicate}
 * and returns the same {@code TitleContainsKeyphrasesPredicate} when its {@code getPredicate}
 * method is called. All of its other methods fail when called.
 */
public class FindCommandStub extends FindCommand {
    private final List<Predicate<Transaction>> predicates;

    /**
     * Constructs a {@code DeleteCommandStub} object.
     *
     * @param predicates The list of predicates to filter the displayed list by.
     */
    public FindCommandStub(List<Predicate<Transaction>> predicates) {
        super(predicates);
        this.predicates = predicates;
    }

    @Override
    protected List<Predicate<Transaction>> getPredicates() {
        return predicates;
    }

    @Override
    public CommandResult execute(Model model) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public boolean equals(Object other) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }
}
