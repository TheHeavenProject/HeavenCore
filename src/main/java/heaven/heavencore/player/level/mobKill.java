package heaven.heavencore.player.level;

import heaven.heavencore.itemCreate.*;
import heaven.heavencore.player.FileManager;
import heaven.heavencore.player.playerDataManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class mobKill implements Listener {

    public static HashMap<Player, Integer> oldExp = new HashMap<>();

    FileManager fileManager = new FileManager();
    checkExp cE = new checkExp();

    public void drop(Player player, String mob, Integer amount) {

        itemCreate itemCreate = new itemCreate();
        itemCreateSword itemCreateSword = new itemCreateSword();
        itemCreateFood itemCreateFood = new itemCreateFood();
        itemCreateTool itemCreateTool = new itemCreateTool();
        itemCreatePotion itemCreatePotion = new itemCreatePotion();

        ArrayList<String> drop = new ArrayList<>();
        ArrayList<String> item = new ArrayList<>();

        for (String dropI : fileManager.getMobList(mob + ".drop")) {
            item.add(dropI);
        }

        for (String sword : itemCreate.getItemList("sword", "itemList.name")) {
            for (String food : itemCreate.getItemList("sword", "itemList.name")) {
                for (String tool : itemCreate.getItemList("sword", "itemList.name")) {
                    for (String potion : itemCreate.getItemList("sword", "itemList.name")) {
                        if (item.equals(sword)) {
                            drop.add(sword);
                        } else if (item.equals(food)) {
                            drop.add(food);
                        } else if (item.equals(tool)) {
                            drop.add(tool);
                        } else if (item.equals(potion)) {
                            drop.add(potion);
                        }
                    }
                }
            }
        }

        for (Integer i = drop.size(); i < drop.size(); i++) {
            if (itemCreate.getItemString("sword", "itemList.name").equals(i)) {
                itemCreateSword.createSwordItem(String.valueOf(i));
            } else if (itemCreate.getItemString("food", "itemList.name").equals(i)) {
                itemCreateFood.createfoodItem(String.valueOf(i), amount);
            } else if (itemCreate.getItemString("tool", "itemList.name").equals(i)) {
                itemCreateTool.createfoodItem(String.valueOf(i));
            } else if (itemCreate.getItemString("potion", "itemList.name").equals(i)) {
                itemCreatePotion.createPotionItem(String.valueOf(i));
            }
        }

    }

    @EventHandler
    public void onKill(MythicMobDeathEvent event) {
        Player player = (Player) event.getKiller();
        String mobName = event.getMobType().getInternalName();

        String mobSearch = fileManager.getMobString(mobName + ".name");

        if (event.getMobType().getInternalName().equalsIgnoreCase(mobSearch)) {
            int lExp = fileManager.getMobInt(mobName + ".exp");

            oldExp.put(player, playerDataManager.exp.get(player));
            playerDataManager.exp.remove(player);
            playerDataManager.exp.put(player, lExp + oldExp.get(player));

            cE.checkExp(player);
        } else {
            return;
        }
    }
}
