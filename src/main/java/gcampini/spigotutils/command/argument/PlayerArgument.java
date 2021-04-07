package gcampini.spigotutils.command.argument;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Gil CAMPINI
 */
public class PlayerArgument implements IdentifiableCommandArgument<Player>, TabCompletableCommandArgument<Player> {

    private final String id;
    private final Server source;

    public PlayerArgument(String id, Server source) {
        this.id = Objects.requireNonNull(id, "id is null");
        this.source = Objects.requireNonNull(source, "server source is null");
    }

    public PlayerArgument() {
        this("player", Bukkit.getServer());
    }

    @Override
    public Player evaluate(String arg) throws IllegalArgumentException {
        Player player = source.getPlayer(arg);
        if (player == null) throw new IllegalArgumentException("Player is not connected.");
        return player;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public List<String> possibilities(CommandSender sender) {
        return source.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
    }

}
