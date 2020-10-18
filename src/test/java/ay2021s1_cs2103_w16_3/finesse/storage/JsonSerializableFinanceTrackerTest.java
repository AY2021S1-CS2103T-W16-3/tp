package ay2021s1_cs2103_w16_3.finesse.storage;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.exceptions.IllegalValueException;
import ay2021s1_cs2103_w16_3.finesse.commons.util.JsonUtil;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions;

public class JsonSerializableFinanceTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFinanceTrackerTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE =
            TEST_DATA_FOLDER.resolve("typicalTransactionsFinanceTracker.json");
    private static final Path INVALID_TRANSACTION_FILE =
            TEST_DATA_FOLDER.resolve("invalidTransactionsFinanceTracker.json");

    @Test
    public void toModelType_typicalTransactionsFile_success() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSACTIONS_FILE,
                JsonSerializableFinanceTracker.class).get();
        FinanceTracker financeTrackerFromFile = dataFromFile.toModelType();
        FinanceTracker typicalTransactionsFinanceTracker = TypicalTransactions.getTypicalFinanceTracker();
        assertEquals(financeTrackerFromFile, typicalTransactionsFinanceTracker);
    }

    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTION_FILE,
                JsonSerializableFinanceTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
