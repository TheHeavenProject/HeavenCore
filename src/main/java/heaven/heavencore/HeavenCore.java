package heaven.heavencore;

import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import heaven.heavencore.death.deathPlayer;
import heaven.heavencore.inn.innListener;
import heaven.heavencore.itemCreate.*;
import heaven.heavencore.itemCreate.listener.itemCreateEvent;
import heaven.heavencore.player.FileManager;
import heaven.heavencore.player.infoBar;
import heaven.heavencore.player.job.playerClass;
import heaven.heavencore.player.job.playerClassManager;
import heaven.heavencore.player.level.mobKill;
import heaven.heavencore.player.playerData;
import heaven.heavencore.inn.innManager;
import heaven.heavencore.player.playerDataManager;
import heaven.heavencore.shop.shop;
import heaven.heavencore.shop.shopListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeavenCore extends JavaPlugin implements CommandExecutor {

    private static HeavenCore plugin;

    private EffectManager em;

    static innManager innManager = new innManager();
    heaven.heavencore.player.playerData playerData = new playerData();
    FileManager fileManager = new FileManager();
    playerClassManager playerClassManager = new playerClassManager();
    itemCreate itemCreate = new itemCreate();
    itemCreateSword itemCreateSword = new itemCreateSword();
    itemCreateFood itemCreateFood = new itemCreateFood();
    itemCreateTool itemCreateTool = new itemCreateTool();
    itemCreatePotion itemCreatePotion = new itemCreatePotion();
    shop shop = new shop();

    @Override
    public void onEnable() {
        plugin = this;
        em = new EffectManager(EffectLib.instance());

        innManager.setup();
        playerData.setup();
        fileManager.setup();
        playerClass.setup();
        itemCreate.setup();
        shop.setup();

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new deathPlayer(), this);
        getServer().getPluginManager().registerEvents(new innListener(), this);
        getServer().getPluginManager().registerEvents(new listener(), this);
        getServer().getPluginManager().registerEvents(new mobKill(), this);
        getServer().getPluginManager().registerEvents(new itemCreateEvent(), this);
        getServer().getPluginManager().registerEvents(new shopListener(), this);
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
        if (args[0].equalsIgnoreCase("reload")) {
            if (args.length == 1) {
                innManager.reload();
                itemCreate.reloadSword();
                itemCreate.reloadFood();
                itemCreate.reloadTool();
                itemCreate.reloadPotion();
                reloadConfig();
                prefix.message(player, "リロードしました");
            }
            return true;
        } else if (args[0].equalsIgnoreCase("debug")) {
            for (String i : itemCreate.getItemList("sword", "itemList.sword")) {
                player.sendMessage(i);
            }
            return true;
        } else if (args[0].equalsIgnoreCase("player")) {
            if (args.length == 3) {
                if (args[1].equalsIgnoreCase("class")) {
                    if (args[2].isEmpty()) {
                        prefix.message(player, "クラス名を入力してください");
                    }
                    playerClassManager.setPlayerClass(player, args[2]);
                } else if (args[1].equalsIgnoreCase("level")) {
                    if (args[2].isEmpty()) {
                        prefix.message(player, "変更したいレベルを入力してください");
                    }
                    playerDataManager.level.replace(player, Integer.valueOf(args[2]));
                } else if (args[1].equalsIgnoreCase("exp")) {
                    if (args[2].isEmpty()) {
                        prefix.message(player, "変更したい経験値を入力してください");
                    }
                    playerDataManager.exp.replace(player, Integer.valueOf(args[2]));
                } else if (args[1].equalsIgnoreCase("sp")) {
                    if (args[2].isEmpty()) {
                        prefix.message(player, "変更したいステータスポイントを入力してください");
                    }
                    playerDataManager.sp.replace(player, Integer.valueOf(args[2]));
                } else if (args[1].equalsIgnoreCase("money")) {
                    if (args[2].isEmpty()) {
                        prefix.message(player, "変更したいまねーを入力してください");
                    }
                    playerDataManager.money.replace(player, Integer.valueOf(args[2]));
                }
            }
        } else if (args[0].equalsIgnoreCase("item")) {
            if (args.length == 3) {
                if (args[1].equalsIgnoreCase("SWORD")) {
                    player.getInventory().addItem(itemCreateSword.createSwordItem(args[2]));
                } else if (args[1].equalsIgnoreCase("FOOD")) {
                    player.getInventory().addItem(itemCreateFood.createfoodItem(args[2], 1));
                } else if (args[1].equalsIgnoreCase("TOOL")) {
                    player.getInventory().addItem(itemCreateTool.createfoodItem(args[2]));
                } else if (args[1].equalsIgnoreCase("POTION")) {
                    player.getInventory().addItem(itemCreatePotion.createPotionItem(args[2]));
                }
            }
        }
        prefix.message(player, "コマンドが不明です");
        return true;
    }

}
