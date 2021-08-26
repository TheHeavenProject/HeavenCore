package heaven.heavencore.itemCreate.listener;

import heaven.heavencore.itemCreate.itemCreate;
import heaven.heavencore.prefix;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class itemCreateEvent implements Listener {

    itemCreate itemCreate = new itemCreate();

    public String getFoodName(String itemName) {
        return itemCreate.getItemString("food", itemName + ".itemName");
    }

    public int getFoodOption(String itemName, String option) {
        return itemCreate.getItemInt("food", itemName + "." + option);
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();

        String getName = event.getItem().getItemMeta().getDisplayName();

        String strip = ChatColor.stripColor(getName);

        if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(getFoodName(strip))) {

            double health = player.getHealth();
            int foodLevel = player.getFoodLevel();

            double getHealth = getFoodOption(strip, "heal");
            int getFoodLevel = getFoodOption(strip, "food-level");

            double healthMax = health + getHealth;

            int MaxFoodLevel = foodLevel + getFoodLevel;

            if (healthMax > player.getMaxHealth()) {

                double healthOver = healthMax - health;
                player.setHealth(healthOver);
                player.setFoodLevel(MaxFoodLevel);
            } else {

                player.setHealth(healthMax);
                player.setFoodLevel(MaxFoodLevel);

            }

            player.sendMessage(String.valueOf(healthMax));
            player.sendMessage(String.valueOf(MaxFoodLevel));
        }
        prefix.message(player, getName);
    }
}
