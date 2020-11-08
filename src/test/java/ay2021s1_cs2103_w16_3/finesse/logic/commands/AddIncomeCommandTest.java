package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs.ModelStub;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.TransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

public class AddIncomeCommandTest {

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddIncomeCommand(null));
    }

    @Test
    public void execute_transactionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIncomeAdded modelStub = new ModelStubAcceptingIncomeAdded();
        Income validIncome = new TransactionBuilder().buildIncome();

        CommandResult commandResult = new AddIncomeCommand(validIncome).execute(modelStub);

        assertEquals(String.format(AddIncomeCommand.MESSAGE_SUCCESS, validIncome), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIncome), modelStub.incomesAdded);
        assertEquals(commandResult.getTabToSwitchTo().get(), Tab.INCOME);
    }

    @Test
    public void equals() {
        Income internship = new TransactionBuilder().withTitle("Internship").buildIncome();
        Income teachingAssistant = new TransactionBuilder().withTitle("Teaching Assistant").buildIncome();
        AddIncomeCommand addInternshipCommand = new AddIncomeCommand(internship);
        AddIncomeCommand addTeachingAssistantCommand = new AddIncomeCommand(teachingAssistant);

        // same object -> returns true
        assertTrue(addInternshipCommand.equals(addInternshipCommand));

        // same values -> returns true
        AddIncomeCommand addInternshipCommandCopy = new AddIncomeCommand(internship);
        assertTrue(addInternshipCommand.equals(addInternshipCommandCopy));

        // different types -> returns false
        assertFalse(addInternshipCommand.equals(1));

        // null -> returns false
        assertFalse(addInternshipCommand.equals(null));

        // different transaction -> returns false
        assertFalse(addInternshipCommand.equals(addTeachingAssistantCommand));
    }

    /**
     * A Model stub that always accepts the income being added.
     */
    private class ModelStubAcceptingIncomeAdded extends ModelStub {
        final ArrayList<Income> incomesAdded = new ArrayList<>();

        @Override
        public void addIncome(Income income) {
            requireNonNull(income);
            incomesAdded.add(income);
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            return new FinanceTracker();
        }
    }
}
