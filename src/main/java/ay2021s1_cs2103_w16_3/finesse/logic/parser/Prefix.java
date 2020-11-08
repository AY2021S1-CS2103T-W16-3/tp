package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static java.util.Objects.requireNonNull;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    private final String prefix;

    /**
     * Constructs a new {@code Prefix} object with the specified prefix.
     * Note that the prefix cannot be {@code null}.
     *
     * @param prefix The specified prefix.
     */
    public Prefix(String prefix) {
        requireNonNull(prefix);
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String toString() {
        return getPrefix();
    }

    @Override
    public int hashCode() {
        return prefix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Prefix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Prefix otherPrefix = (Prefix) obj;
        return otherPrefix.getPrefix().equals(getPrefix());
    }
}
