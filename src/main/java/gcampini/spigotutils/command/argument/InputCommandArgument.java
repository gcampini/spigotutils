package gcampini.spigotutils.command.argument;

import gcampini.spigotutils.command.CommandInputs;

import java.util.Objects;

/**
 * Represents an input command argument. The expected input is not known.
 * {@code InputCommandArgument} is an abstract class that allows the evaluated argument to be stored in the {@link CommandInputs}.
 *
 * @author Gil CAMPINI
 */
public abstract class InputCommandArgument<T> implements CommandArgument<T> {

    private final String id;

    public InputCommandArgument(String id) {
        this.id = Objects.requireNonNull(id, "id is null");
    }

    /**
     * Return the identifier of this argument.
     *
     * @return the id
     */
    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputCommandArgument<?> that = (InputCommandArgument<?>) o;
        return id().equals(that.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id());
    }

    @Override
    public String toString() {
        return "<" + id + ":" + getClass().getSimpleName() + ">";
    }

}
