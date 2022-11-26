package me.skinnynoonie.nooniesbot;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    private static final HashMap<UUID, HashMap<String, Long>> cooldownTracker = new HashMap<>();

    public static void setCooldown(Player player, String cooldownKey, Long time) {
        if(cooldownTracker.get(player.getUniqueId()) == null) cooldownTracker.put(player.getUniqueId(), new HashMap<>());
        cooldownTracker.get(player.getUniqueId()).put(cooldownKey, time);
    }

    public static void setCooldown(Player player, String cooldownKey) {
        setCooldown(player, cooldownKey, System.currentTimeMillis());
    }

    public static Long getTimeSinceCooldown(Player player, String cooldownKey) {
        if(cooldownTracker.get(player.getUniqueId()) == null) return 10000000L;
        if(cooldownTracker.get(player.getUniqueId()).get(cooldownKey) == null) return 10000000L;
        return System.currentTimeMillis() - cooldownTracker.get(player.getUniqueId()).get(cooldownKey);
    }

    public static void unregisterCooldown(Player player) {
        cooldownTracker.remove(player.getUniqueId());
    }

}
