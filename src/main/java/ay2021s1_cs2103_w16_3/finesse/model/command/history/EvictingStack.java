package ay2021s1_cs2103_w16_3.finesse.model.command.history;

import java.util.EmptyStackException;

/**
 * A stack that can hold a fixed number of elements. When pushing a new element
 * onto an already full {@code EvictingStack}, the bottom-most element is removed.
 */
public class EvictingStack<E> {
    /** The maximum size of this {@code EvictingStack}. **/
    private final int maxSize;
    /** The current size of this {@code EvictingStack}. **/
    private int currentSize;
    /** A reference to the top-most {@code Node}. **/
    private Node<E> topNode;
    /** A reference to the bottom-most {@code Node}. **/
    private Node<E> bottomNode;

    /**
     * Constructs an empty {@code EvictingStack} with the specified maximum size.
     *
     * @param maxSize The maximum size of the {@code EvictingStack}.
     * @throws IllegalArgumentException If the specified maximum size is not at least 1.
     */
    public EvictingStack(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("Maximum size of the evicting stack must be at least 1.");
        }
        this.maxSize = maxSize;
        currentSize = 0;
    }

    /**
     * Removes the bottom-most element in this {@code EvictingStack}.
     */
    private void removeBottomElement() {
        assert currentSize >= 1;

        Node<E> nextNode = bottomNode.getNextNode();
        bottomNode.removeLinks();
        bottomNode = nextNode;
        currentSize--;

        // Set the top node to null if the stack is empty.
        if (currentSize == 0) {
            topNode = null;
        }
    }

    /**
     * Checks if this {@code EvictingStack} is empty.
     *
     * @return {@code true} if this {@code EvictingStack} is empty; {@code false} otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Pushes an element onto the top of this {@code EvictingStack}. If the
     * stack is full, the bottom element is evicted.
     *
     * @param element The element to be pushed onto the stack.
     * @return The element that was pushed onto the stack.
     */
    public E push(E element) {
        // Evict the bottom-most element if the stack is full.
        if (currentSize == maxSize) {
            removeBottomElement();
        }

        assert currentSize < maxSize;

        // Push the new element onto the stack.
        Node<E> newNode = new Node<>(element);
        if (topNode != null) {
            Node.linkNodes(topNode, newNode);
        }
        topNode = newNode;
        currentSize++;

        // Set the bottom node to the new node if the stack was previously empty.
        if (currentSize == 1) {
            bottomNode = newNode;
        }

        return element;
    }

    /**
     * Returns the value of the top-most element in this {@code EvictingStack}.
     *
     * @return The value of the element at the top of this {@code EvictingStack}.
     * @throws EmptyStackException If this {@code EvictingStack} is empty.
     */
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return topNode.getValue();
    }

    /**
     * Removes the top-most element from this {@code EvictingStack} and returns it.
     *
     * @return The value of the element at the top of this {@code EvictingStack}.
     */
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        // Pop the top-most element from the stack.
        E element = topNode.getValue();
        Node<E> previousNode = topNode.getPreviousNode();
        topNode.removeLinks();
        topNode = previousNode;
        currentSize--;

        // Set the bottom node to null if the stack is empty.
        if (currentSize == 0) {
            bottomNode = null;
        }

        return element;
    }

    /**
     * Returns the {@code Node} containing the top-most element of this {@code EvictingStack}.
     *
     * @return The {@code Node} containing the top-most element of this {@code EvictingStack}.
     */
    public Node<E> getTopNode() {
        return topNode;
    }
}
