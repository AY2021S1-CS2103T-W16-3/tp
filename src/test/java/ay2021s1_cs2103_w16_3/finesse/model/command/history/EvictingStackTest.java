package ay2021s1_cs2103_w16_3.finesse.model.command.history;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class EvictingStackTest {
    @Test
    public void constructor_invalidMaxSize_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EvictingStack<Integer>(0));
        assertThrows(IllegalArgumentException.class, () -> new EvictingStack<Integer>(-10));
    }

    @Test
    public void push_moreElementsThanMaxSize_bottomElementEvicted() {
        EvictingStack<Integer> evictingStack = new EvictingStack<>(1);
        assertTrue(evictingStack.isEmpty());
        evictingStack.push(1);
        assertFalse(evictingStack.isEmpty());
        evictingStack.push(2);
        assertEquals(2, evictingStack.pop());
        // Check that element 1 is no longer in the stack.
        assertTrue(evictingStack.isEmpty());
    }

    @Test
    public void peek_emptyEvictingStack_throwsEmptyStackException() {
        EvictingStack<Integer> evictingStack = new EvictingStack<>(5);
        assertThrows(EmptyStackException.class, evictingStack::peek);
    }

    @Test
    public void pop_emptyEvictingStack_throwsEmptyStackException() {
        EvictingStack<Integer> evictingStack = new EvictingStack<>(5);
        assertThrows(EmptyStackException.class, evictingStack::pop);
    }

    @Test
    public void getTopNode_iterateThroughStack_correctNumberOfNodes() {
        EvictingStack<Integer> evictingStack = new EvictingStack<>(5);
        IntStream.range(1, 9).forEach(evictingStack::push);

        // Check that the top-most element is 8.
        assertEquals(8, evictingStack.peek());

        // Check that the stack only holds the maximum of 5 elements.
        Node<Integer> node = evictingStack.getTopNode();
        int numOfNodes = 1;
        while (node.hasPreviousNode()) {
            node = node.getPreviousNode();
            numOfNodes++;
        }
        assertEquals(5, numOfNodes);

        // Check that the bottom-most element is 4.
        assertEquals(4, node.getValue());
    }
}
