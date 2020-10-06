package ay2021s1_cs2103_w16_3.finesse.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import ay2021s1_cs2103_w16_3.finesse.commons.exceptions.IllegalValueException;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * An Immutable FinanceTracker that is serializable to JSON format.
 */
@JsonRootName(value = "fine$$e")
class JsonSerializableFinanceTracker {

    public static final String MESSAGE_DUPLICATE_TRANSACTION = "Transactions list contains duplicate transaction(s).";

    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFinanceTracker} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableFinanceTracker(@JsonProperty("transactions") List<JsonAdaptedTransaction> transactions) {
        this.transactions.addAll(transactions);
    }

    /**
     * Converts a given {@code ReadOnlyFinanceTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFinanceTracker}.
     */
    public JsonSerializableFinanceTracker(ReadOnlyFinanceTracker source) {
        transactions.addAll(source.getTransactionList().stream().map(JsonAdaptedTransaction::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this finance tracker into the model's {@code FinanceTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FinanceTracker toModelType() throws IllegalValueException {
        FinanceTracker financeTracker = new FinanceTracker();
        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            if (financeTracker.hasTransaction(transaction)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TRANSACTION);
            }
            financeTracker.addTransaction(transaction);
        }
        return financeTracker;
    }

}
