package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

public class TransactionBuilderTest {
    @Test
    public void equals() {
        TransactionBuilder transactionBuilder = new TransactionBuilder();

        // Same values -> returns true
        assertNotSame(new TransactionBuilder(), transactionBuilder);
        assertEquals(new TransactionBuilder(), transactionBuilder);

        // Different titles -> returns false
        assertNotEquals(new TransactionBuilder().withTitle("Internship"), transactionBuilder);

        // Different amounts -> returns false
        assertNotEquals(new TransactionBuilder().withAmount("5"), transactionBuilder);

        // Different dates -> returns false
        assertNotEquals(new TransactionBuilder().withDate("01/11/2020"), transactionBuilder);

        // Different categories -> returns false
        assertNotEquals(new TransactionBuilder().withCategories("Work"), transactionBuilder);

        // Same object -> returns true
        assertEquals(transactionBuilder, transactionBuilder);

        // null -> returns false
        assertNotEquals(transactionBuilder, null);

        // Different types -> returns false
        assertNotEquals(transactionBuilder, 5);
    }
}
