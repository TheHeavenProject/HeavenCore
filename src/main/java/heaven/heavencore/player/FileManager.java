package heaven.heavencore.player;

import heaven.heavencore.HeavenCore;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public void setup() {
        File expFile = new File(HeavenCore.getPlugin().getDataFolder() + "", "exp.yml");
        File mobFile = new File(HeavenCore.getPlugin().getDataFolder() + "", "mob.yml");

        try {
            expFile.createNewFile();
            mobFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(expFile);
        YamlConfiguration mobConfig = YamlConfiguration.loadConfiguration(mobFile);
        try {
            config.save(expFile);
            mobConfig.save(mobFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getExp() {
        return new File(HeavenCore.getPlugin().getDataFolder() + "", "exp.yml");
    }

    public String getExpString(String column) {
        File inn = getExp();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getString(column);
    }

    public int getExpInt(int nextLevel) {
        File inn = getExp();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getInt(nextLevel + ".exp");
    }

    public File getMob() {
        return new File(HeavenCore.getPlugin().getDataFolder() + "", "mob.yml");
    }

    public String getMobString(String column) {
        File inn = getMob();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getString(column);
    }

    public int getMobInt(String column) {
        File inn = getMob();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getInt(column);
    }

}
