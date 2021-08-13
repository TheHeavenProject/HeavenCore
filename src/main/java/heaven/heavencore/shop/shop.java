package heaven.heavencore.shop;

import heaven.heavencore.HeavenCore;
import heaven.heavencore.convert;
import heaven.heavencore.itemCreate.itemCreateFood;
import heaven.heavencore.itemCreate.itemCreatePotion;
import heaven.heavencore.itemCreate.itemCreateSword;
import heaven.heavencore.itemCreate.itemCreateTool;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
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

//            String itemName1 = itemName.replace("§0", "");
//            String itemName2 = itemName1.replace("§1", "");
//            String itemName3 = itemName2.replace("§2", "");
//            String itemName4 = itemName3.replace("§3", "");
//            String itemName5 = itemName4.replace("§4", "");
//            String itemName6 = itemName5.replace("§5", "");
//            String itemName7 = itemName6.replace("§6", "");
//            String itemName8 = itemName7.replace("§7", "");
//            String itemName9 = itemName8.replace("§8", "");
//            String itemName10 = itemName9.replace("§9", "");
//            String itemName11 = itemName10.replace("§a", "");
//            String itemName12 = itemName11.replace("§b", "");
//            String itemName13 = itemName12.replace("§c", "");
//            String itemName14 = itemName13.replace("§d", "");
//            String itemName15 = itemName14.replace("§e", "");
//            String itemName16 = itemName15.replace("§f", "");
//            String itemName17 = itemName16.replace("§k", "");
//            String itemName18 = itemName17.replace("§l", "");
//            String itemName19 = itemName18.replace("§m", "");
//            String itemName20 = itemName19.replace("§n", "");
//            String itemName21 = itemName20.replace("§o", "");
//            String itemName22 = itemName21.replace("§r", "");
            String itemCon = convert.convertItem(itemName);

            Integer num = getShopInt(npcId + ".contents.slot." + itemCon);
            inv.setItem(num, i);
        }

        return inv;
    }

}
