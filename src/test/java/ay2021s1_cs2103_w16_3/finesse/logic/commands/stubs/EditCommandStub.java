package ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;

/**
 * An {@code EditCommand} stub that takes in an {@code Index} and returns the same
 * {@code Index} when its {@code getTargetIndex} method is called. All of its other
 * methods fail when called.
 */
public class EditCommandStub extends EditCommand {
    private final Index targetIndex;
    private final EditTransactionDescriptor editTransactionDescriptor;

    /**
     * Constructs an {@code EditCommandStub} object.
     *
     * @param targetIndex The target index in the displayed list.
     */
    public EditCommandStub(Index targetIndex, EditTransactionDescriptor editTransactionDescriptor) {
        super(targetIndex, editTransactionDescriptor);
        this.targetIndex = targetIndex;
        this.editTransactionDescriptor = editTransactionDescriptor;
    }

    @Override
    protected Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    protected EditTransactionDescriptor getEditTransactionDescriptor() {
        return editTransactionDescriptor;
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
