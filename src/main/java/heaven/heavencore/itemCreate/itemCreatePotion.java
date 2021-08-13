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

    public List<PotionEffectType> getPotionEffect(String fileName, String itemName) {
        int effect = itemCreate.getItemInt(fileName, itemName + ".effect");
        return Collections.singletonList(PotionEffectType.getById(effect));
    }

    public List<PotionEffectType> getPotionEffect(String fileName, String itemName, String effectType) {
        int effect = itemCreate.getItemInt(fileName, itemName + ".effect." + effectType + ".effectName");
        return Collections.singletonList(PotionEffectType.getById(effect));
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

        for (PotionEffectType effect : getPotionEffect("potion", itemName)) {
            meta.addCustomEffect(new PotionEffect(effect, getPotionTime("potion", itemName, effect), 0), true);
        }

//        meta.addCustomEffect(new PotionEffect(PotionEffectType.BAD_OMEN, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.LUCK, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.SATURATION, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.UNLUCK, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, getPotionTime("potion", itemName), 0), true);
//        meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, getPotionTime("potion", itemName), 0), true);
        meta.setCustomModelData(getCustomModelData("potion", itemName));

        item.setItemMeta(meta);

        return item;
    }

}
