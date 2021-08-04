package heaven.heavencore;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class sound {

    public static void playSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2.5F, 1.9F);
    }

}
