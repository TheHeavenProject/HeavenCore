package heaven.heavencore.shop;

import heaven.heavencore.HeavenCore;
import heaven.heavencore.various;
import heaven.heavencore.itemCreate.itemCreateFood;
import heaven.heavencore.itemCreate.itemCreatePotion;
import heaven.heavencore.itemCreate.itemCreateSword;
import heaven.heavencore.itemCreate.itemCreateTool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class shop {

    /**
     *
     * 1:
     *  npcName: ""
     *  contents:
     *    sword:
     *      - name
     *      - name
     *      - name
     *    slot:
     *      name: num
     *      name: num
     *      name: num
     *
     */

    itemCreateSword itemCreateSword = new itemCreateSword();
    itemCreateFood itemCreateFood = new itemCreateFood();
    itemCreateTool itemCreateTool = new itemCreateTool();
    itemCreatePotion itemCreatePotion = new itemCreatePotion();

    public static HashMap<Player, Integer> openNPCId = new HashMap<>();
    public static HashMap<Player, String> openNPCName = new HashMap<>();

    public void setup() {
        File shop = new File(HeavenCore.getPlugin().getDataFolder() + "", "shop.yml");

        try {
            shop.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(shop);
        try {
            config.save(shop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getShop() {
        return new File(HeavenCore.getPlugin().getDataFolder() + "", "shop.yml");
    }

    public String getShopString(String column) {
        File inn = getShop();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getString(column);
    }

    public int getShopInt(String column) {
        File inn = getShop();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getInt(column);
    }

    public List<String> getShopList(String column) {
        File inn = getShop();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(inn);
        return config.getStringList(column);
    }

    public void load(Player player) {
        player.openInventory(inventory(player));
    }

    public Inventory inventory(Player player) {

        int npcId = openNPCId.get(player);
        String npc = openNPCName.get(player);

        List<ItemStack> item = new ArrayList<>();

        for (String i : getShopList(npcId + ".contents.sword")) {
            item.add(itemCreateSword.createSwordItem(i));
        }
        for (String i : getShopList(npcId + ".contents.food")) {
            item.add(itemCreateFood.createfoodItem(i, 1));
        }
        for (String i : getShopList(npcId + ".contents.tool")) {
            item.add(itemCreateTool.createfoodItem(i));
        }
        for (String i : getShopList(npcId + ".contents.potion")) {
            item.add(itemCreatePotion.createPotionItem(i));
        }

        Inventory inv = Bukkit.createInventory(null, 54, "§lSHOP §8- §l" + npc);

        for (ItemStack i : item) {

            String itemName = i.getItemMeta().getDisplayName();

            String itemCon = various.convertItem(itemName);

//            various.invDecoration(inv, 54);

            Integer num = getShopInt(npcId + ".contents.slot." + ChatColor.stripColor(itemName));
            inv.setItem(num, i);

            ItemStack grass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta iMeta = grass.getItemMeta();
            iMeta.setDisplayName(" ");
            grass.setItemMeta(iMeta);

            various.invDecoration(inv);

        }

        return inv;
    }

}
