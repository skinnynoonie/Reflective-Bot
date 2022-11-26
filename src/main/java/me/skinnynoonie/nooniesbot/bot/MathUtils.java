package me.skinnynoonie.nooniesbot.bot;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MathUtils {

    public static Vector vectorBetween(Location location1, Location location2) {
        double location1X = location1.getX();
        double location1Y = location1.getY();
        double location1Z = location1.getZ();

        double location2X = location2.getX();
        double location2Y = location2.getY();
        double location2Z = location2.getZ();

        return new Vector(
                location2X - location1X,
                location2Y - location1Y,
                location2Z - location1Z
        );
    }

    public static Vector getReflectedVector(Vector vector) {
        return vector.clone().multiply(-1);
    }

}
