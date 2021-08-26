package heaven.heavencore.inn;

import heaven.heavencore.HeavenCore;
import heaven.heavencore.player.playerData;
import heaven.heavencore.player.playerDataManager;
import heaven.heavencore.prefix;
import heaven.heavencore.shop.shop;
import heaven.heavencore.sound;
import heaven.heavencore.various;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;

public class innListener implements Listener {

    public String error = "§c§l予期せぬエラーが発生しました。運営へお問い合わせください。";

    innManager innManager = new innManager();
    playerData playerData = new playerData();
    playerDataManager playerDataManager = new playerDataManager();

    public HashMap<Player, String> openNPC = new HashMap<>();
    public HashMap<Player, Integer> openNPCId = new HashMap<>();

    public void setCoolTime(Player player, Integer npcId) {
        innCoolTime.setCooldown(player, innManager.getInnInt("cooltime", npcId));
    }

    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        Integer clickNPCId = event.getNPC().getId();

        int clickFindId = innManager.getNPC(clickNPCId);

        if (clickNPCId == clickFindId) {
            openNPC.put(player, event.getNPC().getName());
            openNPCId.put(player, event.getNPC().getId());
            innManager.openWindow(player, event.getNPC().getName(), clickNPCId);
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

        int npcId = openNPCId.get(player);

        int price = innManager.getInnInt("price", npcId);

        int playerMoney = playerDataManager.money.get(player);

        if(event.getCurrentItem() == null){
            return;
        }

        if (event.getView().getTitle().equalsIgnoreCase("§l宿屋 §8- §l" + clickNPCName)) {
            if (event.getCurrentItem() == null){
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lスポーン地点設定")) {

                if (innCoolTime.checkCooldown(player)) {
                    if (playerData.getPlayerInt(player, "spawnPoint.npc") == openNPCId.get(player)) {
                        prefix.message(player, HeavenCore.getPlugin().getConfig().getString("rsa"));
                    } else {

                        int x = innManager.getInnInt("spawn.x", npcId);
                        int y = innManager.getInnInt("spawn.y", npcId);
                        int z = innManager.getInnInt("spawn.z", npcId);

                        playerData.setPlayerInt(player, "spawnPoint.x", x);
                        playerData.setPlayerInt(player, "spawnPoint.y", y);
                        playerData.setPlayerInt(player, "spawnPoint.z", z);
                        playerData.setPlayerInt(player, "spawnPoint.npc", openNPCId.get(player));
                        int prices = playerMoney - price;

                        setCoolTime(player, npcId);

                        various.price(player, prices);

                        prefix.message(player, "スポーン地点を設定しました");
                        sound.playSoundSel(player, Sound.ENTITY_PLAYER_LEVELUP, 2);

                    }
                } else {
                    prefix.message(player, "§c§lスポーン地点を設定するには、あと" + innCoolTime.getCooldown(player));
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§l体力回復 蘇生制限のリセット")) {
                Double maxHealth = player.getMaxHealth();
                player.setHealth(maxHealth);
                playerDataManager.revival.replace(player, String.valueOf(0));
                prefix.message(player, "体力を全回復 蘇生制限のリセットをしました");
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (openNPC.containsKey(player)) {
            openNPC.remove(player);
        } else if (shop.openNPCId.containsKey(player)) {
            if (!shop.openNPCId.containsValue(shop.openNPCId.get(player))) {
                shop.openNPCName.remove(player);
                shop.openNPCId.remove(player);
            }
        } else {
            return;
        }

    }

}
