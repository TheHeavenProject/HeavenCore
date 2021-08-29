package heaven.heavencore.itemCreate;

import heaven.heavencore.HeavenCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class itemCreate {

    private static FileConfiguration customFile;
    /**
     *
     *  itemName:
     *    itemName:
     *    customModel-data
     *    material:
     *
     *
     */

    /**
     *
     *  itemName:
     *    itemName:
     *    customModel-data
     *    material:
     *    heal:
     *    food-level:
     *
     *
     */

    // ファイル作成
    public void setup() {
        File sword = new File(HeavenCore.getPlugin().getDataFolder() + "/Item/" + "sword.yml");
        File food = new File(HeavenCore.getPlugin().getDataFolder() + "/Item/" + "food.yml");
        File tool = new File(HeavenCore.getPlugin().getDataFolder() + "/Item/" + "tool.yml");
        File potion = new File(HeavenCore.getPlugin().getDataFolder() + "/Item/" + "potion.yml");

        try {
            sword.createNewFile();
            food.createNewFile();
            tool.createNewFile();
            potion.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileConfiguration swordConfig = YamlConfiguration.loadConfiguration(sword);
        FileConfiguration foodConfig = YamlConfiguration.loadConfiguration(food);
        FileConfiguration toolConfig = YamlConfiguration.loadConfiguration(tool);
        FileConfiguration potionConfig = YamlConfiguration.loadConfiguration(potion);

        try {
            swordConfig.save(sword);
            foodConfig.save(food);
            toolConfig.save(tool);
            potionConfig.save(potion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getFile(String fileName) {
        return new File(HeavenCore.getPlugin().getDataFolder() + "/Item/", fileName + ".yml");
    }

    public int getItemInt(String fileName, String column) {
        File inn = getFile(fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getInt(column);
    }

    public String getItemString(String fileName, String column) {
        File inn = getFile(fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getString(column);
    }

    public List<String> getItemLore(String fileName, String column) {
        File inn = getFile(fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getStringList(column);
    }

    public List<String> getItemList(String fileName, String column) {
        File inn = getFile(fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getStringList(column);
    }

    public List<Integer> getItemListInt(String fileName, String column) {
        File inn = getFile(fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getIntegerList(column);
    }

    public static void reloadSword() {
        File inn = getFile("sword");
        customFile = YamlConfiguration.loadConfiguration(inn);
    }

    public static void reloadFood() {
        File inn = getFile("food");
        customFile = YamlConfiguration.loadConfiguration(inn);
    }

    public static void reloadTool() {
        File inn = getFile("tool");
        customFile = YamlConfiguration.loadConfiguration(inn);
    }

    public static void reloadPotion() {
        File inn = getFile("potion");
        customFile = YamlConfiguration.loadConfiguration(inn);
    }

}
