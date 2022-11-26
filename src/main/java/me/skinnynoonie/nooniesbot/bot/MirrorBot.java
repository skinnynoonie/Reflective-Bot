package me.skinnynoonie.nooniesbot.bot;

import me.skinnynoonie.nooniesbot.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class MirrorBot {

    public static final HashMap<UUID, Integer> mirrorBotHash = new HashMap<>();

    public static void mirrorBot(Player player, Location rotationPoint) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
        mirrorBotHash.put(player.getUniqueId(), npc.getId());

        Vector vectorBetweenPlayerAndRotationPoint = MathUtils.vectorBetween(rotationPoint, player.getLocation());
        Vector reflectedVectorBetweenPlayerAndRotationPoint = MathUtils.getReflectedVector(vectorBetweenPlayerAndRotationPoint);
        Location mirrorBotSpawnLocation = rotationPoint.clone().add(reflectedVectorBetweenPlayerAndRotationPoint);
        mirrorBotSpawnLocation.setY(player.getLocation().getY());

        npc.spawn(mirrorBotSpawnLocation);

        BukkitRunnable runnable =  new BukkitRunnable() {
            @Override
            public void run() {
                if(!player.isOnline()) cancel();
                Vector vectorBetweenPlayerAndRotationPoint = MathUtils.vectorBetween(rotationPoint, player.getLocation());
                Vector reflectedVectorBetweenPlayerAndRotationPoint = MathUtils.getReflectedVector(vectorBetweenPlayerAndRotationPoint);
                Location mirrorBotLocation = rotationPoint.clone().add(reflectedVectorBetweenPlayerAndRotationPoint);
                mirrorBotLocation.setPitch(player.getLocation().getPitch());
                mirrorBotLocation.setYaw(player.getLocation().getYaw() + 180);
                mirrorBotLocation.setY(player.getLocation().getY());
                npc.teleport(mirrorBotLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        };
        runnable.runTaskTimer(Main.getInstance(), 0, 1);

    }

}
