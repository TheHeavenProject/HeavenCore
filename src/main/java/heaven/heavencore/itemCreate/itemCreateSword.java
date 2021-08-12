package heaven.heavencore.itemCreate;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class itemCreateSword {

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

    public Double getAttackDamage(String fileName, String itemName) {
        return Double.valueOf(itemCreate.getItemString(fileName, itemName + ".attack-damage"));
    }

    public Double getAttackSpeed(String fileName, String itemName) {
        return Double.valueOf(itemCreate.getItemString(fileName, itemName + ".attack-speed"));
    }

    public Integer getCustomModelData(String fileName, String itemName) {
        return itemCreate.getItemInt(fileName, itemName + ".customModel-data");
    }



    public ItemStack createSwordItem(String itemName) {
        ItemStack item = new ItemStack(Material.valueOf(getMaterial("sword", itemName)));

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName("sword", itemName));

        List<String> lore = new ArrayList<String>();
        for (String itemLore : itemCreate.getItemLore("sword", itemName + ".lore")) {
            lore.add(itemLore);
        }
        meta.setLore(lore);

        meta.setUnbreakable(true);

        final AttributeModifier attackDamageModifier = new AttributeModifier(UUID.randomUUID(), "GENERIC.ATTACK_DAMAGE",
                getAttackDamage("sword", itemName), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);

        final AttributeModifier attackSpeedModifier = new AttributeModifier(UUID.randomUUID(), "GENERIC.ATTACK_SPEED",
                getAttackSpeed("sword", itemName), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);

        meta.setCustomModelData(getCustomModelData("sword", itemName));

        item.setItemMeta(meta);

        return item;
    }

}