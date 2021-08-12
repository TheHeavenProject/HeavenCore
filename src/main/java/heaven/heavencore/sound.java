package heaven.heavencore;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class sound {

    public static void playSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2.5F, 1.9F);
    }

    public static void playSoundNot(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2.5F, 1.9F);
    }

    public static void playSoundSel(Player player, Sound sound, float pitch) {
        player.playSound(player.getLocation(), sound, 2.5F, pitch);
    }

}
