package heaven.heavencore;

import org.bukkit.entity.Player;

public class prefix {
    public static String prefix = "§7§l| §4§lHeaven §7§l| §r";

    public static void message(Player player, String mes) {
        player.sendMessage("§7§l| §4§lHeaven §7§l| §r" + mes);
    }

}
