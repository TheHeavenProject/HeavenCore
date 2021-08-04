package heaven.heavencore.inn;

import heaven.heavencore.HeavenCore;
import heaven.heavencore.player.playerData;
import heaven.heavencore.player.playerDataManager;
import heaven.heavencore.sound;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;

public class innListener implements Listener {

    public String error = "§c§l予期せぬエラーが発生しました。運営へお問い合わせください。";

    String prefix = "§7§l| §4§lHeaven §7§l| §r";

    innManager innManager = new innManager();
    playerData playerData = new playerData();
    playerDataManager playerDataManager = new playerDataManager();

    public HashMap<Player, String> openNPC = new HashMap<>();
    public HashMap<Player, Integer> openNPCId = new HashMap<>();

    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        Integer clickNPCId = event.getNPC().getId();

        int clickFindId = innManager.getNPC(clickNPCId);

        if (clickNPCId == clickFindId) {
            openNPC.put(player, event.getNPC().getName());
            openNPCId.put(player, event.getNPC().getId());
            innManager.openWindow(player, event.getNPC().getName());
            sound.playSound(player);
            player.sendMessage(openNPC.get(player));
        } else {
            return;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        String clickNPCName = openNPC.get(player);

        if (event.getView().getTitle().equalsIgnoreCase("§l宿屋 §8- §l" + clickNPCName)) {
            if (event.getCurrentItem() == null){
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§l体力回復 蘇生制限のリセット")) {
                Double maxHealth = player.getMaxHealth();
                player.setHealth(maxHealth);
                playerDataManager.revival.replace(player, String.valueOf(0));
                player.sendMessage(prefix + "体力を全回復 蘇生制限のリセットをしました");
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lスポーン地点設定")) {
                int npcId = openNPCId.get(player);
                int x = innManager.getInnInt("spawn.x", npcId);
                int y = innManager.getInnInt("spawn.y", npcId);
                int z = innManager.getInnInt("spawn.z", npcId);

                int playerX = playerData.getPlayerInt(player, "spawnPoint.x");
                int playerY = playerData.getPlayerInt(player, "spawnPoint.y");
                int playerZ = playerData.getPlayerInt(player, "spawnPoint.z");

                if (x == playerX && y == playerY && z == playerZ) {
                    player.sendMessage(prefix + HeavenCore.getPlugin().getConfig().getString("rsa"));
                } else {
                    playerData.setPlayerInt(player, "spawnPoint.x", x);
                    playerData.setPlayerInt(player, "spawnPoint.y", y);
                    playerData.setPlayerInt(player, "spawnPoint.z", z);
                    player.sendMessage(prefix + "スポーン地点を設定しました。");
                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (openNPC.containsKey(player)) {
            openNPC.remove(player);
        } else {
            return;
        }

    }

}
