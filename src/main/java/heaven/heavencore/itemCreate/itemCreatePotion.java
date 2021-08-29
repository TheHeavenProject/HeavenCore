package heaven.heavencore.itemCreate;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class itemCreatePotion {

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

    public List<String> getPotionEffect(String fileName, String itemName) {
        return itemCreate.getItemList(fileName, itemName + ".effect");
    }

    public Integer getPotionEffectTime(String fileName, String itemName, String effectName) {
        return itemCreate.getItemInt(fileName, itemName + ".effectTime." + effectName);
    }

    public int getPotionTime(String fileName, String itemName, PotionEffectType effectType) {
        return itemCreate.getItemInt(fileName, itemName + ".effect." + effectType + ".effectTime") * 20;
    }

    public ItemStack createPotionItem(String itemName) {

        ItemStack item = new ItemStack(Material.POTION);

        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(getName("potion", itemName));

        List<String> lore = new ArrayList<String>();
        for (String itemLore : itemCreate.getItemLore("potion", itemName + ".lore")) {
            lore.add(itemLore);
        }
        meta.setLore(lore);

        meta.setUnbreakable(true);

        // ポーション 使用後の効果を追加

        for (String effect : getPotionEffect("potion", itemName)) {
            Integer time = getPotionEffectTime("potion", itemName, effect);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(effect), time, 2), false);
        }

        meta.setCustomModelData(getCustomModelData("potion", itemName));

        item.setItemMeta(meta);

        return item;
    }

}
