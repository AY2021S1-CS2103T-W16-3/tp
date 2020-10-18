package ay2021s1_cs2103_w16_3.finesse.storage;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.BUBBLE_TEA;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.TEACHING_ASSISTANT_2;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.TUITION_FEES_2;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ay2021s1_cs2103_w16_3.finesse.commons.exceptions.DataConversionException;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;

public class JsonFinanceTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonFinanceTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFinanceTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readFinanceTracker(null));
    }

    private java.util.Optional<ReadOnlyFinanceTracker> readFinanceTracker(String filePath) throws Exception {
        return new JsonFinanceTrackerStorage(Paths.get(filePath))
                .readFinanceTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFinanceTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readFinanceTracker("notJsonFormatFinanceTracker.json"));
    }

    @Test
    public void readFinanceTracker_invalidTransactionFinanceTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFinanceTracker("invalidTransactionsFinanceTracker.json"));
    }

    @Test
    public void readFinanceTracker_invalidAndValidTransactionFinanceTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFinanceTracker(
                "invalidAndValidTransactionsFinanceTracker.json"));
    }

    @Test
    public void readAndSaveFinanceTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempFinanceTracker.json");
        FinanceTracker original = getTypicalFinanceTracker();
        JsonFinanceTrackerStorage jsonFinanceTrackerStorage = new JsonFinanceTrackerStorage(filePath);

        // Save in new file and read back
        jsonFinanceTrackerStorage.saveFinanceTracker(original, filePath);
        ReadOnlyFinanceTracker readBack = jsonFinanceTrackerStorage.readFinanceTracker(filePath).get();
        assertEquals(original, new FinanceTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTransaction(new TransactionBuilder(TUITION_FEES_2).buildExpense());
        original.removeTransaction(new TransactionBuilder(BUBBLE_TEA).buildExpense());
        jsonFinanceTrackerStorage.saveFinanceTracker(original, filePath);
        readBack = jsonFinanceTrackerStorage.readFinanceTracker(filePath).get();
        assertEquals(original, new FinanceTracker(readBack));

        // Save and read without specifying file path
        original.addTransaction(new TransactionBuilder(TEACHING_ASSISTANT_2).buildIncome());
        jsonFinanceTrackerStorage.saveFinanceTracker(original); // file path not specified
        readBack = jsonFinanceTrackerStorage.readFinanceTracker().get(); // file path not specified
        assertEquals(original, new FinanceTracker(readBack));

    }

    @Test
    public void saveFinanceTracker_nullFinanceTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFinanceTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code financeTracker} at the specified {@code filePath}.
     */
    private void saveFinanceTracker(ReadOnlyFinanceTracker financeTracker, String filePath) {
        try {
            new JsonFinanceTrackerStorage(Paths.get(filePath))
                    .saveFinanceTracker(financeTracker, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFinanceTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFinanceTracker(new FinanceTracker(), null));
    }
}
