package heaven.heavencore.inn;

import heaven.heavencore.HeavenCore;
import heaven.heavencore.various;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class innManager {

    private static FileConfiguration customFile;

    /**
     * 宿屋 設定
      */

    public void setup() {
        File inn = new File(HeavenCore.getPlugin().getDataFolder()+"", "inn.yml");

        try {
            inn.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        try {
            config.save(inn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getInn() {
        return new File(HeavenCore.getPlugin().getDataFolder() + "", "inn.yml");
    }

    public int getNPC(int NPCId) {
        File inn = getInn();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getInt(NPCId + ".id");
    }

    public String getInnString(String column, int NPCId) {
        File inn = getInn();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getString(NPCId + "." + column);
    }

    public int getInnInt(String column, int NPCId) {
        File inn = getInn();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getInt(NPCId + "." + column);
    }

    public static void reload() {
        File inn = getInn();
        customFile = YamlConfiguration.loadConfiguration(inn);
    }

    public void openWindow(Player player, String NPCName, Integer npcId) {
        Inventory inv = Bukkit.createInventory(null, 27, "§l宿屋 §8- §l" + NPCName);

        ItemStack heal = new ItemStack(Material.POTION);
        ItemMeta healMeta = heal.getItemMeta();
        healMeta.setDisplayName("§c§l体力回復 蘇生制限のリセット");
        List<String> lore = new ArrayList<>();
        lore.add("§7  体力を全回復");
        lore.add("§7  蘇生する際に増えていく、蘇生制限をリセットします");
        healMeta.setLore(lore);
        heal.setItemMeta(healMeta);

        ItemStack spawn = new ItemStack(Material.RED_BED);
        ItemMeta spawnMeta = spawn.getItemMeta();
        spawnMeta.setDisplayName("§c§lスポーン地点設定");
        List<String> loreSpawn = new ArrayList<>();
        loreSpawn.add("§7  新たに、スポーン地点を設定します");
        loreSpawn.add("§7  値段: " + getInnInt("price", npcId));
        spawnMeta.setLore(loreSpawn);
        spawn.setItemMeta(spawnMeta);

        ItemStack grass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta iMeta = grass.getItemMeta();
        iMeta.setDisplayName("");
        grass.setItemMeta(iMeta);

        various.invDecoration(inv);

        inv.setItem(11, heal);
        inv.setItem(15, spawn);

        player.openInventory(inv);

    }

}
