package heaven.heavencore.player;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import heaven.heavencore.HeavenCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class infoBar {

    private HeavenCore plugin;

    FileManager fM = new FileManager();

//    public void createScoreBoard(Player player) {
//
//        int playerLevel = pD.level.get(player);
//
//        int maxExp = fM.getExpInt(String.valueOf(playerLevel));
//
//        ScoreboardManager sb = Bukkit.getScoreboardManager();
//        Scoreboard scoreboard = sb.getNewScoreboard();
//
//        Objective ob = scoreboard.registerNewObjective("§4§lHeaven", "dummy");
//
//        ob.setDisplaySlot(DisplaySlot.SIDEBAR);
//        ob.setDisplayName("§7>§8> §4§lHeaven §8<§7<");
//
//        Score score = ob.getScore("§0");
//        Score s2 = ob.getScore("§7≫ §6§lステータス");
//        Score s3 = ob.getScore("§6§l  Level: §f" + pD.level.get(player));
//        Score s4 = ob.getScore("§b§l  EXP: §f" + pD.exp.get(player) + "/" + maxExp);
//        Score s5 = ob.getScore("§d§l  SP: §f" + pD.sp.get(player));
//        Score s6 = ob.getScore("§9"); //create a line for the board
//        Score s7 = ob.getScore("§7≫ §6§lCoin");
//        Score s8 = ob.getScore(pD.money.get(player) + "§bEL");
//
//        score.setScore(8);
//        s2.setScore(7);
//        s3.setScore(6);
//        s4.setScore(5);
//        s5.setScore(4);
//        s6.setScore(3);
//        s7.setScore(2);
//        s8.setScore(1);
//
//        player.setScoreboard(scoreboard);
//    }

    public infoBar(HeavenCore plugin){
        this.plugin = plugin;
    }

    public void initScoreboard(){
        Bukkit.getScheduler().runTaskTimer(HeavenCore.getPlugin(), () ->{
            for(Player player : Bukkit.getOnlinePlayers()){
                onCreateBoard(player);
            }
        },0 ,5);
    }

    public void onCreateBoard(Player p) {

        int playerLevel = playerDataManager.level.get(p);

        int playerNextExp = fM.getExpInt(playerLevel);

        BPlayerBoard board = Netherboard.instance().getBoard(p);
        board.set("§g", 10);
        board.set("§7≫ §6§lステータス", 9);
        board.set("§6§l  Level: §f" + playerDataManager.level.get(p), 8);
        board.set("§b§l  EXP: §f" + playerDataManager.exp.get(p) + "/" + playerNextExp, 7);
        board.set("§d§l  SP: §f" + playerDataManager.sp.get(p), 6);
        board.set("§9", 5);
        board.set("§7≫ §6§lCoin", 4);
        board.set("  " + playerDataManager.money.get(p) + " §bEL", 3);
        board.set("§3", 2);
        board.set("§7≫ §b§lNEWS", 1);
        board.set("  " + HeavenCore.getPlugin().getConfig().getString("news"), 0);

        board.setName("§7>§8> §4§lHeaven §8<§7<");
    }

}
