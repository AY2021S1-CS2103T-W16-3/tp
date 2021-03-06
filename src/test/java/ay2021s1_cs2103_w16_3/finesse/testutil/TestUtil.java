package ay2021s1_cs2103_w16_3.finesse.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the transaction in the {@code model}'s transaction list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredTransactionList().size() / 2);
    }

    /**
     * Returns the last index of the transaction in the {@code model}'s transaction list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredTransactionList().size());
    }

    /**
     * Returns the transaction in the {@code model}'s transaction list at {@code index}.
     */
    public static Transaction getTransaction(Model model, Index index) {
        return model.getFilteredTransactionList().get(index.getZeroBased());
    }
}
