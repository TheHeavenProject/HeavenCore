package heaven.heavencore.itemCreate;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class itemCreateTool {

    itemCreate itemCreate = new itemCreate();

    public String getMaterial(String fileName, String itemName) {
        return itemCreate.getItemString(fileName, itemName + ".material");
    }

    public String getName(String fileName, String itemName) {
        return itemCreate.getItemString(fileName, itemName + ".itemName");
    }

    public List<String> getShopName(String fileName) {
        return itemCreate.getItemList(fileName, "itemList.name");
    }

    public Integer getCustomModelData(String fileName, String itemName) {
        return itemCreate.getItemInt(fileName, itemName + ".customModel-data");
    }

    public ItemStack createfoodItem(String itemName) {
        ItemStack item = new ItemStack(Material.valueOf(getMaterial("tool", itemName)));

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName("tool", itemName));

        List<String> lore = new ArrayList<String>();
        for (String itemLore : itemCreate.getItemLore("tool", itemName + ".lore")) {
            lore.add(itemLore);
        }
        meta.setLore(lore);

        meta.setUnbreakable(true);

        meta.setCustomModelData(getCustomModelData("tool", itemName));
        item.setItemMeta(meta);

        return item;
    }

}
