package heaven.heavencore.player.level;

import heaven.heavencore.player.FileManager;
import heaven.heavencore.player.playerDataManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class mobKill implements Listener {

    public static HashMap<Player, Integer> oldExp = new HashMap<>();

    FileManager fileManager = new FileManager();
    checkExp cE = new checkExp();

    public void drop(Player player, String mob) {



    }

    @EventHandler
    public void onKill(MythicMobDeathEvent event) {
        Player player = (Player) event.getKiller();
        String mobName = event.getMob().getDisplayName();

        String mobSearch = fileManager.getMobString(mobName + ".name");

        if (event.getMob().getDisplayName().equalsIgnoreCase(mobSearch)) {
            int lExp = fileManager.getMobInt(mobName + ".exp");

            oldExp.put(player, playerDataManager.exp.get(player));
            playerDataManager.exp.remove(player);
            playerDataManager.exp.put(player, lExp + oldExp.get(player));

            cE.checkExp(player);
        } else {
            return;
        }
    }
}
