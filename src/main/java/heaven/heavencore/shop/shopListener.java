package heaven.heavencore.shop;

import heaven.heavencore.itemCreate.*;
import heaven.heavencore.player.playerDataManager;
import heaven.heavencore.prefix;
import heaven.heavencore.sound;
import heaven.heavencore.various;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class shopListener implements Listener {

    heaven.heavencore.itemCreate.itemCreateSword itemCreateSword = new itemCreateSword();
    heaven.heavencore.itemCreate.itemCreateFood itemCreateFood = new itemCreateFood();
    heaven.heavencore.itemCreate.itemCreateTool itemCreateTool = new itemCreateTool();
    heaven.heavencore.itemCreate.itemCreatePotion itemCreatePotion = new itemCreatePotion();
    shop shop = new shop();

    public boolean totalPrice(Integer npcId, String item, Integer amount, Player player) {

        Integer price = shop.getShopInt(npcId + ".contents.price." + ChatColor.stripColor(item));

        if (price * amount <= playerDataManager.money.get(player)) {
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    public void onClick(NPCRightClickEvent event) {

        Player player = event.getClicker();
        String npc = event.getNPC().getName();
        Integer id = event.getNPC().getId();

        if (shop.getShopInt(id + ".npcId") == id) {
            shop.openNPCName.put(player, npc);
            shop.openNPCId.put(player, id);

            shop.load(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        String npc = shop.openNPCName.get(player);
        Integer npcId = shop.openNPCId.get(player);

        if(event.getCurrentItem() == null){
            return;
        }

        if (event.getView().getTitle().equalsIgnoreCase("§lSHOP §8- §l" + npc)) {

            String item = event.getCurrentItem().getItemMeta().getDisplayName();

            Integer price = playerDataManager.money.get(player) - shop.getShopInt(npcId + ".contents.price." + ChatColor.stripColor(item));

            if (shop.getShopString(npcId + ".itemType." + ChatColor.stripColor(item)).equals("sword")) {
                if (totalPrice(npcId, item, 1, player)) {
                    playerDataManager.money.replace(player, price);
                    player.getInventory().addItem(itemCreateSword.createSwordItem(ChatColor.stripColor(item)));
                    sound.playSoundSel(player, Sound.ENTITY_PLAYER_LEVELUP, 2);
                } else {
                    prefix.message(player, "§l§c所持金が足りません");
                    sound.playSoundSel(player, Sound.BLOCK_NOTE_BLOCK_HARP, 2);
                }
            }
            if (shop.getShopString(npcId + ".itemType." + ChatColor.stripColor(item)).equals("food")) {
                player.closeInventory();
                shop.openNPCName.put(player, npc);
                shop.openNPCId.put(player, npcId);
                player.openInventory(viewOrder(ChatColor.stripColor(item), npc, npcId));
            }
            if (shop.getShopString(npcId + ".itemType." + ChatColor.stripColor(item)).equals("tool")) {
                if (totalPrice(npcId ,item, 1, player)) {
                    playerDataManager.money.replace(player, price);
                    player.getInventory().addItem(itemCreateTool.createfoodItem(ChatColor.stripColor(item)));
                    sound.playSoundSel(player, Sound.ENTITY_PLAYER_LEVELUP, 2);
                } else {
                    prefix.message(player, "§l§c所持金が足りません");
                    sound.playSoundSel(player, Sound.BLOCK_NOTE_BLOCK_HARP, 2);
                }
            }
            if (shop.getShopString(npcId + ".itemType." + ChatColor.stripColor(item)).equals("potion")) {
                if (totalPrice(npcId, item, 1, player)) {
                    playerDataManager.money.replace(player, price);
                    player.getInventory().addItem(itemCreatePotion.createPotionItem(ChatColor.stripColor(item)));
                    sound.playSoundSel(player, Sound.ENTITY_PLAYER_LEVELUP, 2);
                } else {
                    prefix.message(player, "§l§c所持金が足りません");
                    sound.playSoundSel(player, Sound.BLOCK_NOTE_BLOCK_HARP, 2);
                }
            }
            event.setCancelled(true);
        }
        if (event.getView().getTitle().equalsIgnoreCase(npc)) {

            String item = event.getCurrentItem().getItemMeta().getDisplayName();

            if(event.getCurrentItem() == null){
                return;
            }

            ItemStack itemStack = event.getView().getItem(21);
            ItemStack itemStackBuy = event.getView().getItem(41);

            String itemStackName = itemStack.getItemMeta().getDisplayName();

            ItemMeta itemMetaBuy = itemStackBuy.getItemMeta();

            Integer itemAmount = itemStack.getAmount();

            Integer Price = shop.getShopInt(npcId + ".contents.price." + ChatColor.stripColor(itemStackName));
            itemStackBuy.getItemMeta().setDisplayName(String.valueOf(Price));

            if (item.equals("§a§l+")) {

                Integer amount = itemStack.getAmount() + 1;
                if (amount == 65) {

                    prefix.message(player, "§c§lこれ以上増やすことは出来ません");
                    sound.playSoundSel(player, Sound.BLOCK_NOTE_BLOCK_HARP, 2);

                } else if(amount > 64) {

                    itemStack.setAmount(64);
                    itemStackBuy.getItemMeta().setDisplayName("§a§l購入する §f§l" + Price * amount + " §b§lEL");
                    itemStackBuy.setItemMeta(itemMetaBuy);
                    sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);

                } else {

                    itemStack.setAmount(amount);
                    itemMetaBuy.setDisplayName("§a§l購入する §f§l" + amount * Price + " §b§lEL");
                    itemStackBuy.setItemMeta(itemMetaBuy);
                    sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);

                }

            } else if (item.equals("§a§l+16")) {

                Integer amount = itemStack.getAmount() + 16;
                if (amount == 64 + 16) {

                    prefix.message(player, "§c§lこれ以上増やすことは出来ません");
                    sound.playSoundSel(player, Sound.BLOCK_NOTE_BLOCK_HARP, 2);

                } else if(amount > 64) {

                    itemStack.setAmount(64);
                    itemMetaBuy.setDisplayName("§a§l購入する §f§l" + amount * Price + " §b§lEL");
                    itemStackBuy.setItemMeta(itemMetaBuy);
                    sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);

                } else {

                    itemStack.setAmount(amount);
                    itemMetaBuy.setDisplayName("§a§l購入する §f§l" + amount * Price + " §b§lEL");
                    itemStackBuy.setItemMeta(itemMetaBuy);
                    sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);

                }

            } else if (item.equals("§a§l+32")) {

                Integer amount = itemStack.getAmount() + 32;
                if (amount == 64 + 32) {

                    prefix.message(player, "§c§lこれ以上増やすことは出来ません");
                    sound.playSoundSel(player, Sound.BLOCK_NOTE_BLOCK_HARP, 2);

                } else if(amount > 64) {

                    itemStack.setAmount(64);
                    itemMetaBuy.setDisplayName("§a§l購入する §f§l" + amount * Price + " §b§lEL");
                    itemStackBuy.setItemMeta(itemMetaBuy);
                    sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);

                } else {

                    itemStack.setAmount(amount);
                    itemMetaBuy.setDisplayName("§a§l購入する §f§l" + amount * Price + " §b§lEL");
                    itemStackBuy.setItemMeta(itemMetaBuy);
                    sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);

                }

            } else if (item.equals("§a§l+64")) {

                itemStack.setAmount(64);
                sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);

                itemMetaBuy.setDisplayName("§a§l購入する §f§l" + 64 * Price + " §b§lEL");
                itemStackBuy.setItemMeta(itemMetaBuy);
                sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);

            } else if (item.equals("§c§l-")) {

                Integer amount = itemStack.getAmount() - 1;
                if (amount == 0) {

                    prefix.message(player, "§c§lこれ以上減らすことは出来ません");
                    sound.playSoundSel(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1);

                } else {

                    itemStack.setAmount(amount);
                    itemMetaBuy.setDisplayName("§a§l購入する §f§l" + amount * Price + " §b§lEL");
                    itemStackBuy.setItemMeta(itemMetaBuy);
                    sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);
                }

            } else if (item.equals("§c§lリセットする")) {

                itemStack.setAmount(1);
                itemMetaBuy.setDisplayName("§a§l購入する §f§l" + Price * 1 + " §b§lEL");
                itemStackBuy.setItemMeta(itemMetaBuy);
                sound.playSoundSel(player, Sound.UI_BUTTON_CLICK, 1);

            } else if (item.equals("§a§l購入する §f§l" + Price * itemAmount + " §b§lEL")) {

                prefix.message(player, String.valueOf(itemAmount));

                if (totalPrice(npcId, item, itemAmount, player)) {
                    Integer price = playerDataManager.money.get(player) - shop.getShopInt(npcId + ".contents.price." + ChatColor.stripColor(itemStackName)) * itemAmount;

                    various.price(player, price);
                    player.getInventory().addItem(itemCreateFood.createfoodItem(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()), itemStack.getAmount()));
                    sound.playSoundSel(player, Sound.ENTITY_PLAYER_LEVELUP, 2);
                } else {
                    prefix.message(player, "§c§l所持金が足りません");
                    sound.playSoundSel(player, Sound.BLOCK_NOTE_BLOCK_HARP, 2);
                }
            } else if (item.equals("§c§l戻る")) {
                shop.load(player);
                shop.openNPCName.put(player, event.getView().getTitle());
            }

            event.setCancelled(true);
        }
    }

    public Inventory viewOrder(String item, String npc, Integer npcId) {

        Integer itemPrice = shop.getShopInt(npcId + ".contents.price." + ChatColor.stripColor(item));

        ItemStack buyItem = itemCreateFood.createfoodItem(item, 1);

        ItemStack incDown = new ItemStack(Material.RED_STAINED_GLASS);
        ItemStack incDown1 = new ItemStack(Material.RED_STAINED_GLASS);
        ItemStack inc = new ItemStack(Material.WHITE_STAINED_GLASS);
        ItemStack inc1 = new ItemStack(Material.BLACK_STAINED_GLASS, 16);
        ItemStack inc2 = new ItemStack(Material.WHITE_STAINED_GLASS, 32);
        ItemStack inc3 = new ItemStack(Material.WHITE_STAINED_GLASS, 64);
        ItemStack buy = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemStack cancel = new ItemStack(Material.RED_STAINED_GLASS_PANE);

        ItemMeta incMeta = inc.getItemMeta();
        incMeta.setDisplayName("§a§l+");
        inc.setItemMeta(incMeta);

        ItemMeta inc1Meta = inc1.getItemMeta();
        inc1Meta.setDisplayName("§a§l+16");
        inc1.setItemMeta(inc1Meta);

        ItemMeta inc2Meta = inc2.getItemMeta();
        inc2Meta.setDisplayName("§a§l+32");
        inc2.setItemMeta(inc2Meta);

        ItemMeta inc3Meta = inc3.getItemMeta();
        inc3Meta.setDisplayName("§a§l+64");
        inc3.setItemMeta(inc3Meta);

        ItemMeta incDownMeta = incDown.getItemMeta();
        incDownMeta.setDisplayName("§c§l-");
        incDown.setItemMeta(incDownMeta);

        ItemMeta incDownMeta1 = incDown1.getItemMeta();
        incDownMeta1.setDisplayName("§c§lリセットする");
        incDown1.setItemMeta(incDownMeta1);

        ItemMeta buyMeta = buy.getItemMeta();
        buyMeta.setDisplayName("§a§l購入する §f§l" + itemPrice + " §b§lEL");
        buy.setItemMeta(buyMeta);

        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName("§c§l戻る");
        cancel.setItemMeta(cancelMeta);

        Inventory inv = Bukkit.createInventory(null, 54, npc);

        various.invDecoration(inv);

        inv.setItem(19, incDown1);
        inv.setItem(20, incDown);
        inv.setItem(21, buyItem);
        inv.setItem(22, inc);
        inv.setItem(23, inc1);
        inv.setItem(24, inc2);
        inv.setItem(25, inc3);
        inv.setItem(39, cancel);
        inv.setItem(41, buy);

        return inv;
    }

}
