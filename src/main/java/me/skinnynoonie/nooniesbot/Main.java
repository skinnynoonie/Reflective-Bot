package me.skinnynoonie.nooniesbot;

import me.skinnynoonie.nooniesbot.bot.MirrorBot;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class Main extends JavaPlugin implements Listener {

    private static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new GameruleManager(), this);
        GameruleManager.registerGamerules(Bukkit.getWorld("world"));
        for(Player player : Bukkit.getOnlinePlayers()) Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(player, ""));
        for(LivingEntity entity : Bukkit.getWorld("world").getLivingEntities()) if(!(entity instanceof Player)) entity.damage(99999999);
        System.out.println("Hello world");
    }

    @Override
    public void onDisable() {
        for(NPC npc : CitizensAPI.getNPCRegistry()) npc.destroy();
    }

    public static Plugin getInstance(){return instance;}

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(
                new Location(player.getWorld(), 10134.5, 106, 10183.5, 180, 0)
        );
        player.setHealth(player.getMaxHealth());
        MirrorBot.mirrorBot(player, new Location(player.getWorld(), 10134.5, 106, 10139.5));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        CooldownManager.unregisterCooldown(player);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getEntityType() != EntityType.PLAYER) {event.setCancelled(true);}
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (CooldownManager.getTimeSinceCooldown(player, "dmg") < 450) return;
            CooldownManager.setCooldown(player, "dmg");
            player.setNoDamageTicks(0);
            player.damage(0, event.getEntity());

            net.minecraft.server.v1_8_R3.Entity ep = ((CraftEntity)event.getEntity()).getHandle();
            PacketPlayOutAnimation packet = new PacketPlayOutAnimation(ep, 0);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

            player.setVelocity(player.getVelocity().multiply(new Vector(1.8, 1.5, 1.8)));
        }
        ((Damageable) event.getEntity()).damage(0);

    }


    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
    }

}
