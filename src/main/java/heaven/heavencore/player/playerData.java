package heaven.heavencore.player;

import heaven.heavencore.HeavenCore;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class playerData {

    /**
     * プレイヤー 設定
     */

    public void setup() {
        File playerFolder = new File(HeavenCore.getPlugin().getDataFolder()+"/Player/", "");
        playerFolder.mkdirs();
    }

    public void createPlayerFile(Player player) {

        File playerFile = new File(HeavenCore.getPlugin().getDataFolder() + "/Player/", player.getUniqueId().toString() + ".yml");

        try {
            playerFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        String playerLocation = String.valueOf(player.getLocation());

        config.set("class", "");
        config.set("level", 1);
        config.set("exp", 0);
        config.set("sp", 0);
        config.set("money", 0);
        config.set("spawnPoint.x", player.getLocation().getX());
        config.set("spawnPoint.y", player.getLocation().getY());
        config.set("spawnPoint.z", player.getLocation().getZ());
        config.set("spawnPoint.world", "world");
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkData(Player player) {

        File playerFile = new File(HeavenCore.getPlugin().getDataFolder() + "/Player/", player.getUniqueId().toString() + ".yml");

        if (playerFile.exists()) {
            return false;
        } else {
            return true;
        }
    }

    public File getPlayer(Player player) {
        return new File(HeavenCore.getPlugin().getDataFolder() + "/Player/", player.getUniqueId().toString() + ".yml");
    }

    public String getPlayerString(Player player, String column) {
        File inn = getPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getString(column);
    }

    public int getPlayerInt(Player player, String column) {
        File inn = getPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getInt(column);
    }

    public void setPlayerInt(Player player, String column, int value) {
        File playerFile = getPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        config.set(column, value);
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInnString(Player player, String column, String value) {
        File playerFile = getPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        config.set(column, value);
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInnLocation(Player player, String value) {
        File playerFile = getPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        config.set("spawnLocation", player.getWorld() + "," + player.getLocation().getX() + "," + player.getLocation().getY() + "," + player.getLocation().getZ());
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
