package heaven.heavencore.player;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import heaven.heavencore.HeavenCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class infoBar {

    private HeavenCore plugin;

    FileManager fM = new FileManager();
    playerData playerData = new playerData();

    public String getPlayerClass(Player player) {
        return playerData.getPlayerString(player, "class");
    }

    public infoBar(HeavenCore plugin){
        this.plugin = plugin;
    }

    public boolean checkEnable(Player player) {

        if (playerData.getPlayerBoolean(player, "font")) {
            return true;
        } else {
            return false;
        }

    }

    public void initScoreboard(){
        Bukkit.getScheduler().runTaskTimer(HeavenCore.getPlugin(), () ->{
            for(Player player : Bukkit.getOnlinePlayers()){
                onCreateBoard(player);
                createActionBar(player);
            }
        },0 ,5);
    }

    public void onCreateBoard(Player p) {
        int playerLevel = playerDataManager.level.get(p);

        int playerNextExp = fM.getExpInt(playerLevel);

        BPlayerBoard board = Netherboard.instance().getBoard(p);
        board.set("§g", 11);
        board.set("§7≫ §6§lステータス", 10);
        board.set("§a§l  Job§f: " + getPlayerClass(p), 9);
        board.set("§6§l  Level§f : " + playerDataManager.level.get(p), 8);
        if (playerNextExp == 0) {
            board.set("§b§l  EXP§f : " + playerDataManager.exp.get(p) + "/MAX", 7);
        } else {
            board.set("§b§l  EXP§f : " + playerDataManager.exp.get(p) + "/" + playerNextExp, 7);
        }
        board.set("§d§l  SP§f : " + playerDataManager.sp.get(p), 6);
        board.set("§9", 5);
        board.set("§7≫ §6§lCoin", 4);
        board.set("  " + playerDataManager.money.get(p) + " §bEL", 3);
        board.set("§3", 2);
        board.set("§7≫ §b§lNEWS", 1);
        board.set("  " + HeavenCore.getPlugin().getConfig().getString("news"), 0);

        board.setName("§7>§8> §4§lHeaven §8<§7<");
    }

    public void createActionBar(Player player) {

        if (checkEnable(player)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§lHP: §f" + Math.ceil(player.getHealth()) + "/" + Math.ceil(player.getMaxHealth()) + "  §b§lMP: " + "0" + "/" + "0"));
        }

    }

}
