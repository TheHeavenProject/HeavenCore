package heaven.heavencore;

import heaven.heavencore.player.playerDataManager;
import heaven.heavencore.shop.shop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class various {

    public static void invDecoration(Inventory inv) {

        for (int i = 0; i < inv.getSize(); i++) {
            if (i <= 9 || inv.getSize() - 9 <= i || i % 9 == 0 || i % 9 == 8) {
                ItemStack none = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta iMeta = none.getItemMeta();
                iMeta.setDisplayName("");
                none.setItemMeta(iMeta);
                inv.setItem(i, none);
            }
        }
    }

    public static void price(Player player, Integer price) {
        playerDataManager.money.replace(player, price);
    }

    public static String convertItem(String column) {
        String itemName1 = column.replace("§0", "");
        String itemName2 = itemName1.replace("§1", "");
        String itemName3 = itemName2.replace("§2", "");
        String itemName4 = itemName3.replace("§3", "");
        String itemName5 = itemName4.replace("§4", "");
        String itemName6 = itemName5.replace("§5", "");
        String itemName7 = itemName6.replace("§6", "");
        String itemName8 = itemName7.replace("§7", "");
        String itemName9 = itemName8.replace("§8", "");
        String itemName10 = itemName9.replace("§9", "");
        String itemName11 = itemName10.replace("§a", "");
        String itemName12 = itemName11.replace("§b", "");
        String itemName13 = itemName12.replace("§c", "");
        String itemName14 = itemName13.replace("§d", "");
        String itemName15 = itemName14.replace("§e", "");
        String itemName16 = itemName15.replace("§f", "");
        String itemName17 = itemName16.replace("§k", "");
        String itemName18 = itemName17.replace("§l", "");
        String itemName19 = itemName18.replace("§m", "");
        String itemName20 = itemName19.replace("§n", "");
        String itemName21 = itemName20.replace("§o", "");
        String itemName22 = itemName21.replace("§r", "");

        return itemName22;
    }

}
