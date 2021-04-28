package gcampini.spigotutils.shared;

import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Gil CAMPINI
 */
public class ChatColorHelper {

    public static final ChatColor[] DARK_COLORS = new ChatColor[]{
            ChatColor.DARK_AQUA,
            ChatColor.DARK_BLUE,
            ChatColor.DARK_GRAY,
            ChatColor.DARK_GREEN,
            ChatColor.DARK_RED,
            ChatColor.DARK_PURPLE
    };

    public static final ChatColor[] LIGHT_COLORS = new ChatColor[]{
            ChatColor.LIGHT_PURPLE,
            ChatColor.AQUA,
            ChatColor.GOLD,
            ChatColor.YELLOW,
            ChatColor.RED,
            ChatColor.BLUE,
            ChatColor.GREEN,
    };

    public static final ChatColor[] COLORS = Stream.concat(Arrays.stream(DARK_COLORS), Arrays.stream(LIGHT_COLORS)).toArray(ChatColor[]::new);

    public static final ChatColor[] SHADES_OF_GRAY = new ChatColor[]{
            ChatColor.WHITE,
            ChatColor.GRAY,
            ChatColor.DARK_GRAY,
            ChatColor.BLACK
    };

    public static final ChatColor[] MODIFIERS = new ChatColor[]{
            ChatColor.RESET,
            ChatColor.ITALIC,
            ChatColor.BOLD,
            ChatColor.UNDERLINE,
            ChatColor.MAGIC,
            ChatColor.STRIKETHROUGH
    };

}
