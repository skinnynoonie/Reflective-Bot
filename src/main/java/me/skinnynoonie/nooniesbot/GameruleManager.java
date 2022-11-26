package me.skinnynoonie.nooniesbot;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GameruleManager implements Listener {

    public static void registerGamerules(World world) {
        world.setGameRuleValue("commandBlockOutput", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doEntityDrops", "true");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("doMobLoot", "true");
        world.setGameRuleValue("doMobSpawning", "true");
        world.setGameRuleValue("doTileDrops", "true");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("logAdminCommands", "true");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("naturalRegeneration", "true");
        world.setGameRuleValue("randomTickSpeed", "0");
        world.setGameRuleValue("reducedDebugInfo", "true");
        world.setGameRuleValue("sendCommandFeedback", "true");
        world.setGameRuleValue("showDeathMessages", "false");
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) event.setCancelled(true);
    }

}
