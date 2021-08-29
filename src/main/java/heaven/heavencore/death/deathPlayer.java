package heaven.heavencore.death;

import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import heaven.heavencore.HeavenCore;
import heaven.heavencore.player.playerData;
import heaven.heavencore.player.playerDataManager;
import heaven.heavencore.prefix;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.HashMap;

public class deathPlayer implements Listener {

    playerData playerData = new playerData();
    playerDataManager playerDataManager = new playerDataManager();

    private String DEATH = "DEATH_NOW";
    private String DEATH_OK = "DEATH_NOW_OK";
    private String REVIVAL_NOW = "REVIVAL_NOW";
    private String REVIVAL = "REVIVAL";
    private String EFFECT = "EFFECT";

    public static HashMap<Player, String> deathPlayerList = new HashMap<>();
    public static HashMap<Player, String> ArmorStandName = new HashMap<>();
    public static HashMap<String, Player> ArmorStandPlayerName = new HashMap<>();
    public static HashMap<Player, String> RevivalPlayer = new HashMap<>();
    public static HashMap<Player, String> effects = new HashMap<>();

    FileConfiguration config = HeavenCore.getPlugin().getConfig();

    public void changeGameMode(Player player, GameMode gameMode) {
        player.setGameMode(gameMode);
    }

    public void playerSendTitle(Player player, String title, String subTitle, Integer fadeIn, Integer stay, Integer fadeOut) {
        player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
    }

    public AnimatedBallEffect effect() {
        AnimatedBallEffect effect = new AnimatedBallEffect(HeavenCore.effectManager);
        return effect;
    }

    public void effectCancel(AnimatedBallEffect effect) {
        effect.cancel();
    }


    public void setEffect(Entity e, Player p) {
        AnimatedBallEffect effect = new AnimatedBallEffect(HeavenCore.effectManager);
        effect.setEntity(e);
        effect.particle = Particle.CLOUD;
        effect.particles = 150;
        effect.particlesPerIteration = 10;
        effect.size = 1;
        effect.xFactor = 1;
        effect.yFactor = 2;
        effect.zFactor = 1;
        effect.type = EffectType.REPEATING;
        effect.setStartTime(5);
        effect.start();
    }

    public void putRevival(Player player) {
        RevivalPlayer.put(player, REVIVAL);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) throws InterruptedException {

        if (event.getEntityType() == EntityType.PLAYER) {
            try {
                Player player = (Player) event.getEntity();

                int x = playerData.getPlayerInt(player, "spawnPoint.x");
                int y = playerData.getPlayerInt(player, "spawnPoint.y");
                int z = playerData.getPlayerInt(player, "spawnPoint.z");
                String world = playerData.getPlayerString(player, "spawnPoint.world");
                Location spawnPoint = new Location(Bukkit.getWorld(world), x, y, z);
                Location deathLoc = player.getLocation().add(0.0, 2.0, 0.0);
                Double maxHealth = player.getMaxHealth();

                if (player.getHealth() - event.getFinalDamage() < 0) {
                    if (config.getInt("revival-count") == Integer.parseInt(playerDataManager.revival.get(player))) {
                        playerSendTitle(player, config.getString("title-die"), config.getString("stitle-die"), 10, 60, 10);
                        player.sendMessage(config.getString("die-chat"));
                        player.sendMessage(config.getString("die-exp"));
                        player.sendMessage(config.getString("die-money"));
                        player.teleport(spawnPoint);
                        changeGameMode(player, GameMode.ADVENTURE);
                        return;
                    }
                    deathPlayerList.put(player, DEATH);
                    player.setHealth(maxHealth);
                    changeGameMode(player, GameMode.SPECTATOR);
                    event.setCancelled(true);

                    player.teleport(deathLoc);

                    player.sendTitle(config.getString("Title-down"), "死亡を早めるにはシフトを押してください", 0, config.getInt("t-r") * 20 - 100, 0);
                    player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1, 1);

                    ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                    head.setDurability((short) 3);
                    SkullMeta skull = (SkullMeta) head.getItemMeta();
                    skull.setOwner(player.getName());
                    skull.setDisplayName(player.getName());
                    head.setItemMeta(skull);

                    ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0.0, 0.0, 0.0), EntityType.ARMOR_STAND);
                    stand.setCustomNameVisible(true);
                    stand.setCustomName(player.getName() + " " + config.getString("t-rm") + "(" + config.getInt("t-r") + ")");
                    stand.setInvulnerable(true);
                    stand.setHelmet(head);
                    stand.setBasePlate(false);
                    stand.setHeadPose(new EulerAngle(-25, 0, 0));
                    stand.hasArms();

                    ArmorStandName.put(player, stand.getCustomName());
                    ArmorStandPlayerName.put(stand.getCustomName(), player);

                    player.setSpectatorTarget(stand);

//                    setEffect(stand, player);
//                    effects.put(player, EFFECT);

                    BukkitRunnable task = new BukkitRunnable() {
                        int count = config.getInt("t-r");

                        public void run() {
                            if (deathPlayerList.containsKey(player)) {
                                if (count == 0) {
                                    playerSendTitle(player, config.getString("title-die"), config.getString("stitle-die"), 10, 60, 10);
                                    player.sendMessage(config.getString("die-chat"));
                                    player.sendMessage(config.getString("die-exp"));
                                    player.sendMessage(config.getString("die-money"));
                                    changeGameMode(player, GameMode.ADVENTURE);
                                    stand.remove();
                                    deathPlayerList.remove(player);
                                    ArmorStandName.remove(player);
//                                    setEffect(stand, player);
//                                    effects.remove(player);
                                    player.teleport(spawnPoint);
                                    cancel();
                                }
                                if (count == 1) {
                                    player.sendTitle(config.getString("Title-down"), "残り1秒", 0, 25, 0);
                                    stand.setCustomName(player.getName() + " " + config.getString("t-rm") + "(" + count + ")");
                                    ArmorStandName.replace(player, stand.getCustomName());
                                }
                                if (count == 2) {
                                    player.sendTitle(config.getString("Title-down"), "残り2秒", 0, 25, 0);
                                    stand.setCustomName(player.getName() + " " + config.getString("t-rm") + "(" + count + ")");
                                    ArmorStandName.replace(player, stand.getCustomName());
                                }
                                if (count == 3) {
                                    player.sendTitle(config.getString("Title-down"), "残り3秒", 0, 25, 0);
                                    stand.setCustomName(player.getName() + " " + config.getString("t-rm") + "(" + count + ")");
                                    ArmorStandName.replace(player, stand.getCustomName());
                                }
                                if (count == 4) {
                                    player.sendTitle(config.getString("Title-down"), "残り4秒", 0, 25, 0);
                                    stand.setCustomName(player.getName() + " " + config.getString("t-rm") + "(" + count + ")");
                                    ArmorStandName.replace(player, stand.getCustomName());
                                }
                                if (count == 5) {
                                    player.sendTitle(config.getString("Title-down"), "残り5秒", 0, 25, 0);
                                    stand.setCustomName(player.getName() + " " + config.getString("t-rm") + "(" + count + ")");
                                    ArmorStandName.replace(player, stand.getCustomName());
                                }
                                if (deathPlayerList.containsKey(player) && deathPlayerList.containsValue(DEATH)) {
                                    count--;
                                } else {
                                    playerSendTitle(player, "蘇生中です...", "", 10, 60, 10);
                                }
                            } else {
                                stand.remove();
                                deathPlayerList.remove(player);
                                ArmorStandName.remove(player);
                                setEffect(stand, player);
                                cancel();
                            }
                        }
                    };
                    task.runTaskTimer(HeavenCore.getPlugin(), 0, 20L);
                }
            } catch (ClassCastException error) {
                event.getEntity().sendMessage("");
            }
        } else {
            return;
        }
    }


    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();

        if (deathPlayerList.containsValue(DEATH)) {
            deathPlayerList.remove(player);
            playerSendTitle(player, config.getString("title-die"), config.getString("stitle-die"), 10, 60, 10);
            player.sendMessage(config.getString("die-chat"));
            changeGameMode(player, GameMode.ADVENTURE);
        }

        if (RevivalPlayer.containsKey(player)) {
            RevivalPlayer.remove(player);
            player.sendMessage("§c§l蘇生をキャンセルしました");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (RevivalPlayer.containsKey(player)) {
            player.sendMessage("§c§l現在蘇生中の為、移動は出来ません。キャンセルするにはシフトを押してください");
            event.setCancelled(true);
            return;
        }

    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent event) {

        Player player = event.getPlayer();

        if (player.isSneaking() && event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {


            String clickStandName = event.getRightClicked().getCustomName();

            boolean standName = ArmorStandName.containsValue(clickStandName);

            Player dPlayer = ArmorStandPlayerName.get(clickStandName);

            if (standName) {

                deathPlayerList.replace(dPlayer, REVIVAL_NOW);

                player.sendTitle("現在蘇生をしています...", "蘇生完了まで残り" + config.getInt("revival-time") + "秒", 10, config.getInt("revival-time") * 20 - 60, 10);


                BukkitRunnable task = new BukkitRunnable() {

                    int count = config.getInt("revival-time");

                    int putCount = config.getInt("revival-time") - 2;

                    public void run() {
                        if (count == 0) {
                            player.sendTitle("蘇生が完了しました！！", "", 10, 60, 10);
                            dPlayer.sendTitle("蘇生が完了しました！！", "", 10, 60, 10);
                            changeGameMode(dPlayer, GameMode.ADVENTURE);
                            playerDataManager.revival.put(player, String.valueOf(+1));
                            ArmorStandName.remove(dPlayer);
                            deathPlayerList.remove(dPlayer);
                            RevivalPlayer.remove(player);
                            ArmorStandPlayerName.remove(clickStandName);
                            cancel();
                        }
                        if (count == 1) {
                            player.sendTitle("現在蘇生をしています...", "蘇生完了まで残り" + count + "秒", 10, config.getInt("revival-time") * 20 - 60, 10);
                        }
                        if (count == 2) {
                            player.sendTitle("現在蘇生をしています...", "蘇生完了まで残り" + count + "秒", 10, config.getInt("revival-time") * 20 - 60, 10);
                        }
                        if (count == 3) {
                            player.sendTitle("現在蘇生をしています...", "蘇生完了まで残り" + count + "秒", 10, config.getInt("revival-time") * 20 - 60, 10);
                        }
                        if (count == putCount) {
                            putRevival(player);
                        }
                        count--;
                    }
                };
                task.runTaskTimer(HeavenCore.getPlugin(), 0, 20L);
            } else {
                player.sendMessage("§c§l予期せぬエラーが発生しました。運営へお問い合わせください。");
            }
        }
        return;
    }
    
}
