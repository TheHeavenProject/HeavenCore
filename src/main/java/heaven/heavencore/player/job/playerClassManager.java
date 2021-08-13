package heaven.heavencore.player.job;

import heaven.heavencore.player.playerData;
import heaven.heavencore.player.playerDataManager;
import heaven.heavencore.prefix;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class playerClassManager {

    playerClass playerClass = new playerClass();

    playerData playerData = new playerData();

    public void setPlayerClass(Player player, String className) {
        playerData.setPlayerString(player, "class", className);
        setAbility(player, className);
    }

    public double getAbility(String className, String abilityName) {
        return playerClass.getClassInt(className + ".attributes." + abilityName);
    }

    public double getLevelUpAbility(String className, String abilityName) {
        return playerClass.getClassInt(className + ".levelUp." + abilityName);
    }

    public void setAbility(Player player, String className) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getAbility(className, "MaxHealth"));
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(getAbility(className, "AttackSpeed"));
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(getAbility(className, "AttackDamage"));
        player.setHealthScale(20);
    }

    public void setStatusReset(Player player, String className) {
        double MaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
        double AttackDamage = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getDefaultValue();
        double AttackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getDefaultValue();

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MaxHealth);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(AttackDamage);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(AttackSpeed);
        player.setHealthScale(20);
    }

    public void levelUp(Player player, String className) {
        double MaxHealth = getAbility(className, "MaxHealth");
        double AttackDamage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
        double AttackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue();

        double MaxHealthNow = playerDataManager.level.get(player) * getLevelUpAbility(className, "MaxHealth");
        double AttackDamageNow = playerDataManager.level.get(player) * getLevelUpAbility(className, "AttackDamage");
        double AttackSpeedNow = playerDataManager.level.get(player) * getLevelUpAbility(className, "AttackSpeed");

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MaxHealthNow + MaxHealth);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(AttackDamageNow + AttackDamage);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(AttackSpeedNow + AttackSpeed);
        prefix.message(player, "でばっぐ");
        prefix.message(player, String.valueOf(player.getHealth()));
        player.setHealthScale(20);
    }

    public void onJoin(Player player, String className) {
        double MaxHealth = playerData.getPlayerInt(player, "level") * getLevelUpAbility(className, "MaxHealth");
        double AttackDamage = playerData.getPlayerInt(player, "level") * getLevelUpAbility(className, "AttackDamage");
        double AttackSpeed = playerData.getPlayerInt(player, "level") * getLevelUpAbility(className, "AttackSpeed");

        double MaxHealthClass = getAbility(className, "MaxHealth");
        double AttackDamageClass = getAbility(className, "AttackSpeed");
        double AttackSpeedClass = getAbility(className, "AttackDamage");

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MaxHealth + MaxHealthClass);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(AttackDamage + AttackDamageClass);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(AttackSpeed + AttackSpeedClass);
        player.setHealthScale(20);
    }
}
