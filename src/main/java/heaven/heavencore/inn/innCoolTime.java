package heaven.heavencore.inn;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class innCoolTime {

    public static HashMap<UUID, Double> cooldowns;

    public static void setupCooldown(){
        cooldowns = new HashMap<>();
    }

    public static void setCooldown(Player player, int seconds){
        double delay = System.currentTimeMillis() + (seconds*60000);
        cooldowns.put(player.getUniqueId(), delay);
    }

    public static int getCooldown(Player player){
        return Math.toIntExact((long) Math.floor((cooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 60000 + 1));
    }

    public static boolean checkCooldown(Player player){
        if(!cooldowns.containsKey(player.getUniqueId()) || cooldowns.get(player.getUniqueId()) <= System.currentTimeMillis()){
            return true;
        }
        return false;
    }

    //    @EventHandler
//    public void onPlayerRightClick(PlayerInteractEvent e){
//        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)){
//            if(checkCooldown(e.getPlayer())){
//                e.getPlayer().sendMessage("Used");
//                setCooldown(e.getPlayer(), 1);
//            } else {
//                e.getPlayer().sendMessage("You cannot do this for another " + getCooldown(e.getPlayer()) + " seconds");
//            }
//        }
//    }

}
