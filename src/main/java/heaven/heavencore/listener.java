package heaven.heavencore;

import fr.minuskube.netherboard.Netherboard;
import heaven.heavencore.player.playerData;
import heaven.heavencore.player.playerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class listener implements Listener {

    playerData playerData = new playerData();
    playerDataManager playerDataManager = new playerDataManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Netherboard.instance().createBoard(player,"My Scoreboard");
        HeavenCore.getPlugin().getLogger().info(String.valueOf(player.getLocation()));
        if (playerData.checkData(player)) {
            playerData.createPlayerFile(player);
            playerDataManager.setMap(player);
            return;
        } else {
            playerDataManager.setMap(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerDataManager.deleteMap(player);
    }

}
