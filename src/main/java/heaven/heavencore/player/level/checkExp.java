package heaven.heavencore.player.level;

import heaven.heavencore.HeavenCore;
import heaven.heavencore.player.FileManager;
import heaven.heavencore.player.job.playerClassManager;
import heaven.heavencore.player.playerData;
import heaven.heavencore.player.playerDataManager;
import heaven.heavencore.prefix;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class checkExp {

    HashMap<Player, Integer> playerLevel = new HashMap<>();

    FileConfiguration getConfig = HeavenCore.getPlugin().getConfig();
    FileManager fileManager = new FileManager();
    playerClassManager playerClassManager = new playerClassManager();

    public String getPlayerClass(Player player) {
        playerData playerData = new playerData();
        return playerData.getPlayerString(player, "class");
    }

    public int getPlayerExp(Player player) {
        int playerExp = playerDataManager.exp.get(player);
        return playerExp;
    }

    public int getNextExp(Player player) {
        int level = playerDataManager.level.get(player);
        int nextExp = fileManager.getExpInt(level);
        return nextExp;
    }

    public void checkExp(Player player) {
        if (getPlayerExp(player) >= getNextExp(player)) {
            playerLevel.put(player, playerDataManager.level.get(player));

            int playerNextLevel = playerDataManager.level.get(player) + 1;

            player.sendMessage(getConfig.getString("levelup-message"));
            player.sendMessage(playerDataManager.level.get(player) + " → " + playerNextLevel);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.5F, 2);

            playerClassManager.levelUp(player, getPlayerClass(player));

            prefix.message(player, String.valueOf(player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue()));

            playerDataManager.level.remove(player);
            playerDataManager.level.put(player, playerLevel.get(player) + 1);

            int playerExp = mobKill.oldExp.get(player);
            int playerExpNew = fileManager.getExpInt(playerDataManager.level.get(player) - 2);
//            レベルアップ前の必要経験値数
            playerDataManager.exp.remove(player);
            playerDataManager.exp.put(player, playerExp - playerExpNew + 1);

            playerLevel.remove(player);
            mobKill.oldExp.remove(player);

        } else {
            return;
        }
    }

}
