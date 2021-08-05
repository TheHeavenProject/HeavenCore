package heaven.heavencore;

import heaven.heavencore.death.deathPlayer;
import heaven.heavencore.inn.innListener;
import heaven.heavencore.player.FileManager;
import heaven.heavencore.player.infoBar;
import heaven.heavencore.player.job.playerClass;
import heaven.heavencore.player.job.playerClassManager;
import heaven.heavencore.player.level.mobKill;
import heaven.heavencore.player.playerData;
import heaven.heavencore.inn.innManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeavenCore extends JavaPlugin implements CommandExecutor {

    private static HeavenCore plugin;

    static innManager innManager = new innManager();
    heaven.heavencore.player.playerData playerData = new playerData();
    FileManager fileManager = new FileManager();
    playerClassManager playerClassManager = new playerClassManager();

    @Override
    public void onEnable() {

        plugin = this;

        innManager.setup();
        playerData.setup();
        fileManager.setup();
        playerClass.setup();

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new deathPlayer(), this);
        getServer().getPluginManager().registerEvents(new innListener(), this);
        getServer().getPluginManager().registerEvents(new listener(), this);
        getServer().getPluginManager().registerEvents(new mobKill(), this);
        getCommand("heaven").setExecutor(this);

        infoBar board = new infoBar(this);
        board.initScoreboard();
    }

    @Override
    public void onDisable() {
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!player.isOp()) {
            prefix.message(player, "あなたにこのコマンドを実行する権限はありません");
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                innManager.reload();
                reloadConfig();
                prefix.message(player, "リロードしました");
                return true;
            }
            if (args[0].equalsIgnoreCase("debug")) {
                player.setHealthScale(20);
                prefix.message(player, String.valueOf(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()));
                return true;
            }
            if (args[0].equalsIgnoreCase("warria")) {
                playerClassManager.setPlayerClass(player, "warria");
//                player.setHealthScale(20);
                return true;
            }
            if (args[0].equalsIgnoreCase("reset")) {
                playerClassManager.setStatusReset(player, "warria");
                prefix.message(player, String.valueOf(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()));
                player.setHealthScale(20);
                return true;
            }
            if (args[0].equalsIgnoreCase("speed")) {
                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.6);
                return true;
            }
        }
        prefix.message(player, "コマンドが不明です");
        return true;
    }

}
