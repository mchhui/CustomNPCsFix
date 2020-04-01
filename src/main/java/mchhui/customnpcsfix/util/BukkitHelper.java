package mchhui.customnpcsfix.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import net.minecraft.world.World;
import noppes.npcs.controllers.data.TransportLocation;

public class BukkitHelper {
    public static boolean teleport(String playerName, String worldName, TransportLocation loc) {
        Location bukkitLoc = new Location(Bukkit.getWorld(worldName), loc.getX(), loc.getY(), loc.getZ());
        return Bukkit.getPlayer(playerName).teleport(bukkitLoc);
    }

    public static boolean isBukkitAlive() {
        try {
            Class.forName("org.bukkit.Bukkit");
        } catch (ClassNotFoundException err) {
            return false;
        }
        return true;
    }
}
