package heaven.heavencore.player.job;

import heaven.heavencore.HeavenCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class playerClass {

//  ファイルを作成
    public static void setup() {
        File file = new File(HeavenCore.getPlugin().getDataFolder() + "", "class.yml");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileConfiguration config =YamlConfiguration.loadConfiguration(file);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return new File(HeavenCore.getPlugin().getDataFolder() + "", "class.yml");
    }

    public int getClassInt(String column) {
        File inn = getFile();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getInt(column);
    }

}
