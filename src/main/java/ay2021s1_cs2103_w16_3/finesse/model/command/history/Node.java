package ay2021s1_cs2103_w16_3.finesse.model.command.history;

import static java.util.Objects.requireNonNull;

/**
 * A node in a doubly linked list.
 */
public class Node<E> {
    /** The value contained in this {@code Node}. **/
    private final E value;
    /** A reference to the next {@code Node}. **/
    private Node<E> nextNode;
    /** A reference to the previous {@code Node}. **/
    private Node<E> previousNode;

    /**
     * Creates a new {@code Node} containing the specified value.
     *
     * @param value The value to be contained in this {@code Node}.
     */
    public Node(E value) {
        this.value = value;
    }

    /**
     * Returns the value that is contained in this {@code Node}.
     *
     * @return The value that is contained in this {@code Node}.
     */
    public E getValue() {
        return value;
    }

    /**
     * Returns the {@code Node} that comes after this {@code Node}.
     *
     * @return The next {@code Node}.
     */
    public Node<E> getNextNode() {
        return nextNode;
    }

    /**
     * Returns the {@code Node} that comes before this {@code Node}.
     *
     * @return The previous {@code Node}.
     */
    public Node<E> getPreviousNode() {
        return previousNode;
    }

    /**
     * Returns whether there exists a node that comes after this {@code Node}.
     *
     * @return {@code true} if {@code nextNode} is not {@code null}; false otherwise.
     */
    public boolean hasNextNode() {
        return nextNode != null;
    }

    /**
     * Returns whether there exists a node that comes before this {@code Node}.
     *
     * @return {@code true} if {@code previousNode} is not {@code null}; false otherwise;
     */
    public boolean hasPreviousNode() {
        return previousNode != null;
    }

    /**
     * Links two nodes together by setting the respective nodes to point to each other.
     * {@code firstNode} will have its {@code nextNode} be set to {@code secondNode}, while
     * {@code secondNode} will have its {@code previousNode} be set to {@code firstNode}.
     * If linking the two nodes together overrides existing links, the linking fails and
     * {@code false} will be returned.
     *
     * @param firstNode The {@code Node} that comes before in the link.
     * @param secondNode The {@code Node} that comes after in the link.
     * @param <E> The type of the two {@code Node}s.
     * @return {@code true} if the two nodes are successfully linked; {@code false} otherwise.
     */
    public static <E> boolean linkNodes(Node<E> firstNode, Node<E> secondNode) {
        requireNonNull(firstNode);
        requireNonNull(secondNode);

        // Do not override existing links.
        if (firstNode.hasNextNode() || secondNode.hasPreviousNode()) {
            return false;
        }

        firstNode.nextNode = secondNode;
        secondNode.previousNode = firstNode;

        return true;
    }

    /**
     * Removes all links for this {@code Node}.
     */
    public void removeLinks() {
        // Remove link with the node that comes after this node.
        if (nextNode != null) {
            nextNode.previousNode = null;
            nextNode = null;
        }

        // Remove link with the node that comes before this node.
        if (previousNode != null) {
            previousNode.nextNode = null;
            previousNode = null;
        }
    }
}
