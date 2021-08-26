package heaven.heavencore.inn;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class innCoolTime {

    public static HashMap<UUID, Double> cooldowns;

    public static void setupCooldown(){
        cooldowns = new HashMap<>();
    }

//    public static void setCooldown(Player player, int minutes){
//        double delay = System.currentTimeMillis() + (minutes*60000);
//        cooldowns.put(player.getUniqueId(), delay);
//    }

    public static void setCooldown(Player player, int minutes){
        double delay = System.currentTimeMillis() + (minutes*60000);
        cooldowns.put(player.getUniqueId(), delay);
    }

    public static int getCooldown(Player player){
        return Math.toIntExact(Math.round((cooldowns.get(player.getUniqueId()) - System.currentTimeMillis())/1000));
    }

    public static boolean checkCooldown(Player player){
        if(!cooldowns.containsKey(player.getUniqueId()) || cooldowns.get(player.getUniqueId()) <= System.currentTimeMillis()){
            return true;
        }
        return false;
    }

}
