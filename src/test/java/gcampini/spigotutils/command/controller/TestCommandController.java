package gcampini.spigotutils.command.controller;

import gcampini.spigotutils.command.argument.IntegerArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Gil CAMPINI
 */
public class TestCommandController {

    @CommandAction(value = "")
    public static void testBroadcast(@Sender CommandSender sender) {
        sender.getServer().broadcastMessage("test");
    }

    @CommandAction(value = "health <amount>")
    public static void testHealth(
            @Input(argument = IntegerArgument.class, id = "amount") int amount,
            @Sender Player player
    ) {
        player.setHealth(amount);
        player.sendMessage("updated");
    }

}
