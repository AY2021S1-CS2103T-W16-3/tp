package ay2021s1_cs2103_w16_3.finesse.model.command.history;

/**
 * Keeps track of the user input history as well as the command history navigation state.
 */
public class CommandHistory {
    /** An {@code EvictingStack} to keep track of the command history. **/
    private final EvictingStack<String> commandHistory;
    /** A reference to the current {@code Node} in the current command navigation state. **/
    private Node<String> currentNode;

    /**
     * Creates a new {@code CommandHistory} which keeps track of the most recent commands.
     *
     * @param maxCommands The number of recent commands to keep track of.
     * @throws IllegalArgumentException If the specified maximum commands is not at least 1.
     */
    public CommandHistory(int maxCommands) {
        if (maxCommands < 1) {
            throw new IllegalArgumentException("Maximum number of commands must be at least 1.");
        }
        commandHistory = new EvictingStack<>(maxCommands);
    }

    /**
     * Adds a command to the command history and resets the state of the command navigation.
     *
     * @param command The command to be added.
     */
    public void addCommand(String command) {
        commandHistory.push(command);
        currentNode = null;
    }

    /**
     * Returns the previous command in the current command navigation state. If the current
     * command is already the earliest tracked command, then the current command is returned.
     * If the command history is empty, returns an empty string.
     *
     * @return The previous command.
     */
    public String navigateUp() {
        if (currentNode == null && commandHistory.isEmpty()) {
            return "";
        } else if (currentNode == null) {
            currentNode = commandHistory.getTopNode();
        } else if (currentNode.hasPreviousNode()) {
            currentNode = currentNode.getPreviousNode();
        }
        return currentNode.getValue();
    }

    /**
     * Returns the next command in the current command navigation state. If the current
     * command is already the latest tracked command, then {@code null} is returned.
     * If the command history is empty, returns an empty string.
     *
     * @return The next command.
     */
    public String navigateDown() {
        if (currentNode == null) {
            return "";
        } else if (currentNode.hasNextNode()) {
            currentNode = currentNode.getNextNode();
        } else {
            return null;
        }
        return currentNode.getValue();
    }
}
