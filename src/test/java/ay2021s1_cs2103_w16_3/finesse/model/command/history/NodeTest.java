package ay2021s1_cs2103_w16_3.finesse.model.command.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NodeTest {
    @Test
    public void contructor() {
        String value = "Hello World";
        Node<String> node = new Node<>(value);

        assertEquals(value, node.getValue());
    }

    @Test
    public void linkNodes_noLinkConflicts_returnsTrue() {
        Node<Integer> firstNode = new Node<>(1);
        Node<Integer> secondNode = new Node<>(2);

        assertTrue(Node.linkNodes(firstNode, secondNode));

        // Check that the link was successfully created.
        assertFalse(firstNode.hasPreviousNode());
        assertTrue(firstNode.hasNextNode());
        assertTrue(secondNode.hasPreviousNode());
        assertFalse(secondNode.hasNextNode());

        assertEquals(secondNode, firstNode.getNextNode());
        assertEquals(firstNode, secondNode.getPreviousNode());
    }

    @Test
    public void linkNodes_hasLinkConflicts_returnsFalse() {
        Node<Integer> firstNode = new Node<>(1);
        Node<Integer> secondNode = new Node<>(2);
        Node<Integer> thirdNode = new Node<>(3);

        assertTrue(Node.linkNodes(firstNode, secondNode));
        assertFalse(Node.linkNodes(firstNode, thirdNode));

        // Check that the link between firstNode and secondNode was not created.
        assertFalse(firstNode.hasPreviousNode());
        assertTrue(firstNode.hasNextNode());
        assertFalse(thirdNode.hasPreviousNode());
        assertFalse(thirdNode.hasNextNode());

        assertEquals(secondNode, firstNode.getNextNode());
        assertNull(thirdNode.getPreviousNode());
    }

    @Test
    public void linkNodes_nullNode_throwsNullPointerException() {
        Node<Integer> node = new Node<>(1);

        assertThrows(NullPointerException.class, () -> Node.linkNodes(node, null));
        assertThrows(NullPointerException.class, () -> Node.linkNodes(null, node));
        assertThrows(NullPointerException.class, () -> Node.linkNodes(null, null));
    }

    @Test
    public void removeLinks() {
        Node<Integer> firstNode = new Node<>(1);
        Node<Integer> secondNode = new Node<>(2);
        Node<Integer> thirdNode = new Node<>(3);

        // Create a circular chain of nodes.
        assertTrue(Node.linkNodes(firstNode, secondNode));
        assertTrue(Node.linkNodes(secondNode, thirdNode));
        assertTrue(Node.linkNodes(thirdNode, firstNode));

        // Check that all nodes have previous and next links.
        assertTrue(firstNode.hasPreviousNode());
        assertTrue(firstNode.hasNextNode());
        assertTrue(secondNode.hasPreviousNode());
        assertTrue(secondNode.hasNextNode());
        assertTrue(thirdNode.hasPreviousNode());
        assertTrue(thirdNode.hasNextNode());

        secondNode.removeLinks();

        // Check that all links with secondNode are be removed.
        assertTrue(firstNode.hasPreviousNode());
        assertFalse(firstNode.hasNextNode());
        assertFalse(secondNode.hasPreviousNode());
        assertFalse(secondNode.hasNextNode());
        assertFalse(thirdNode.hasPreviousNode());
        assertTrue(thirdNode.hasNextNode());
    }
}
