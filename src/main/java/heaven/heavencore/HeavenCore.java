package heaven.heavencore;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.BleedEffect;
import heaven.heavencore.death.deathPlayer;
import heaven.heavencore.inn.innCoolTime;
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
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class HeavenCore extends JavaPlugin implements TabExecutor {

    private static HeavenCore plugin;

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

    public static EffectManager effectManager;

    @Override
    public void onEnable() {
        plugin = this;
        effectManager = new EffectManager(this);

        innManager.setup();
        playerData.setup();
        fileManager.setup();
        playerClass.setup();
        itemCreate.setup();
        shop.setup();
        innCoolTime.setupCooldown();

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
        effectManager.dispose();
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
            player.sendMessage(String.valueOf(innCoolTime.getCooldown(player)));
            return true;
        } else if (args[0].equalsIgnoreCase("effect")) {
            AnimatedBallEffect effect = new AnimatedBallEffect(HeavenCore.effectManager);
            effect.setEntity(player);
            effect.particle = Particle.CLOUD;
            effect.particles = 150;
            effect.particlesPerIteration = 10;
            effect.size = 1;
            effect.xFactor = 1;
            effect.yFactor = 2;
            effect.zFactor = 1;
            effect.xOffset = 0;
            effect.yOffset = 0;
            effect.zOffset = 0;
            effect.xRotation = 0;
            effect.yRotation = 0;
            effect.zRotation = 0;
            effect.type = EffectType.REPEATING;
            effect.start();
            effect.cancel();
            return true;
        } else if (args[0].equalsIgnoreCase("debugtest")) {
            innCoolTime.setCooldown(player, 30);
            return true;
        } else if (args[0].equalsIgnoreCase("attribute")) {
//            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            player.sendMessage(String.valueOf(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getDefaultValue()));
            return true;
        } else if (args[0].equalsIgnoreCase("item")) {
            if (args.length == 3) {
                if (args[1].equalsIgnoreCase("SWORD")) {
                    player.getInventory().addItem(itemCreateSword.createSwordItem(args[2]));
                    prefix.message(player, itemCreateSword.createSwordItem(args[2]).getItemMeta().getDisplayName() + "を渡しました");
                    return true;
                } else if (args[1].equalsIgnoreCase("FOOD")) {
                    player.getInventory().addItem(itemCreateFood.createfoodItem(args[2], 16));
                    prefix.message(player, itemCreateFood.createfoodItem(args[2], 1).getItemMeta().getDisplayName() + "を渡しました");
                    return true;
                } else if (args[1].equalsIgnoreCase("TOOL")) {
                    player.getInventory().addItem(itemCreateTool.createfoodItem(args[2]));
                    prefix.message(player, itemCreateTool.createfoodItem(args[2]).getItemMeta().getDisplayName() + "を渡しました");
                    return true;
                } else if (args[1].equalsIgnoreCase("POTION")) {
                    player.getInventory().addItem(itemCreatePotion.createPotionItem(args[2]));
                    prefix.message(player, itemCreatePotion.createPotionItem(args[2]).getItemMeta().getDisplayName() + "を渡しました");
                    return true;
                }
            }
        }
        if (args.length == 3) {

            OfflinePlayer selectPlayer = Bukkit.getOfflinePlayer(args[0]).getPlayer();

            if (args[1].equalsIgnoreCase("class")) {
                if (args[2].isEmpty()) {
                    prefix.message(player, "クラス名を入力してください");
                }
                playerClassManager.setPlayerClass((Player) selectPlayer, args[2]);

                prefix.message(player, selectPlayer.getName() + "さんに" + args[2] + "を設定しました");

                return true;
            } else if (args[1].equalsIgnoreCase("level")) {
                if (args[2].isEmpty()) {
                    prefix.message(player, "変更したいレベルを入力してください");
                }
                playerDataManager.level.replace((Player) selectPlayer, Integer.valueOf(args[2]));

                prefix.message(player, selectPlayer.getName() + "さんに" + args[2] + "レベルを設定しました");

                return true;
            } else if (args[1].equalsIgnoreCase("exp")) {
                if (args[2].isEmpty()) {
                    prefix.message(player, "変更したい経験値を入力してください");
                }
                playerDataManager.exp.replace((Player) selectPlayer, Integer.valueOf(args[2]));

                prefix.message(player, selectPlayer.getName() + "さんに" + args[2] + "経験値を設定しました");

                return true;
            } else if (args[1].equalsIgnoreCase("sp")) {
                if (args[2].isEmpty()) {
                    prefix.message(player, "変更したいステータスポイントを入力してください");
                }
                playerDataManager.sp.replace((Player) selectPlayer, Integer.valueOf(args[2]));

                prefix.message(player, selectPlayer.getName() + "さんに" + args[2] + "ステータスポイントを設定しました");

                return true;
            } else if (args[1].equalsIgnoreCase("money")) {
                if (args[2].isEmpty()) {
                    prefix.message(player, "変更したいまねーを入力してください");
                }
                playerDataManager.money.replace((Player) selectPlayer, Integer.valueOf(args[2]));
                prefix.message(player, selectPlayer.getName() + "さんに" + args[2] + "まねーを設定しました");
                return true;
            }
        }
        prefix.message(player, "コマンドが不明です");
        return true;
    }

    //  Tab補完
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            List<String> arg = new ArrayList<>();

            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                arg.add(players[i].getName());
            }
            arg.add("reload");
            arg.add("item");

            return arg;
        } else if (args.length == 2) {

            if (args[0].equals("item")) {
                List<String> arg = new ArrayList<>();
                arg.add("SWORD");
                arg.add("FOOD");
                arg.add("TOOL");
                arg.add("POTION");
                return arg;
            } else {
                List<String> arg = new ArrayList<>();
                arg.add("level");
                arg.add("exp");
                arg.add("sp");
                arg.add("money");
                return arg;
            }
        } else if (args.length == 3) {
            if (args[1].equals("SWORD")) {
                List<String> arg = new ArrayList<>();
                for (String i : itemCreate.getItemList("sword", "itemList.name")) {
                    arg.add(i);
                }
                return arg;
            } else if (args[1].equals("FOOD")) {
                List<String> arg = new ArrayList<>();
                for (String i : itemCreate.getItemList("food", "itemList.name")) {
                    arg.add(i);
                }
                return arg;
            } else if (args[1].equals("TOOL")) {
                List<String> arg = new ArrayList<>();
                for (String i : itemCreate.getItemList("tool", "itemList.name")) {
                    arg.add(i);
                }
                return arg;
            } else if (args[1].equals("POTION")) {
                List<String> arg = new ArrayList<>();
                for (String i : itemCreate.getItemList("potion", "itemList.name")) {
                    arg.add(i);
                }
                return arg;
            }
        }
        return null;
    }
}
