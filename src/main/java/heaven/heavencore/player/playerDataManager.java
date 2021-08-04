package heaven.heavencore.player;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class playerDataManager {

    playerData playerData = new playerData();

    public static HashMap<Player, Integer> level = new HashMap<>();
    public static HashMap<Player, Integer> exp = new HashMap<>();
    public static HashMap<Player, Integer> sp = new HashMap<>();
    public static HashMap<Player, Integer> money = new HashMap<>();
    public static HashMap<Player, String> revival = new HashMap<>();

    public void setMap(Player player) {
        revival.put(player, String.valueOf(playerData.getPlayerInt(player, "revival")));
        level.put(player, playerData.getPlayerInt(player, "level"));
        exp.put(player, playerData.getPlayerInt(player, "exp"));
        sp.put(player, playerData.getPlayerInt(player, "sp"));
        money.put(player, playerData.getPlayerInt(player, "money"));
        player.sendMessage("§7§l| §4§lHeaven §7§l| §rプレイヤー情報をロードしています...");
        player.sendMessage(String.valueOf(exp.get(player)));
    }

    public void deleteMap(Player player) {
        playerData.setPlayerInt(player, "level", level.get(player));
        playerData.setPlayerInt(player, "exp", exp.get(player));
        playerData.setPlayerInt(player, "sp", sp.get(player));
        playerData.setPlayerInt(player, "money", money.get(player));
        playerData.setPlayerInt(player, "revival", Integer.parseInt(revival.get(player)));

        revival.remove(player);
        level.remove(player);
        exp.remove(player);
        sp.remove(player);
        money.remove(player);
    }

}
