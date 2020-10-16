package ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;

/**
 * A {@code DeleteCommand} stub that takes in an {@code Index} and returns the same
 * {@code Index} when its {@code getTargetIndex} method is called. All of its other
 * methods fail when called.
 */
public class DeleteCommandStub extends DeleteCommand {
    private final Index targetIndex;

    /**
     * Constructs a {@code DeleteCommandStub} object.
     *
     * @param targetIndex The target index in the displayed list.
     */
    public DeleteCommandStub(Index targetIndex) {
        super(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    protected Index getTargetIndex() {
        return targetIndex;
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
