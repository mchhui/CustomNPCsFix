package mchhui.customnpcsfix.util;

import java.util.HashMap;
import java.util.Map;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.controllers.data.Waypoint;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import noppes.npcs.controllers.data.Quest;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointWorldContainer;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.minimap.XaeroMinimap;

public class QuestHelper {
    public static Map<Integer, xaero.common.minimap.waypoints.Waypoint> points = new HashMap<Integer, xaero.common.minimap.waypoints.Waypoint>();
    public static Map<xaero.common.minimap.waypoints.Waypoint, WaypointSet> setFromPoint = new HashMap<xaero.common.minimap.waypoints.Waypoint, WaypointSet>();

    public static void addWaypoint(Waypoint point) {
        WaypointsManager manager = XaeroMinimap.instance.getWaypointsManager();
        WaypointWorldContainer container = manager.getWorldContainer(manager.getAutoContainerID().split("/")[0]);
        container = container.addSubContainer(point.worldDIM);
        WaypointSet set = container.addWorld("waypoints").getSets().get(point.setName);
        xaero.common.minimap.waypoints.Waypoint waypoint = new xaero.common.minimap.waypoints.Waypoint(point.x, point.y,
                point.z, point.displayName, "", ((int) (Math.random() * 255) * 1000000
                        + (int) (Math.random() * 255) * 1000 + (int) (Math.random() * 255)),
                1001, true);
        points.put(point.questID, waypoint);
        setFromPoint.put(waypoint, set);
        set.getList().add(waypoint);
    }

    public static void removeWaypoint(Waypoint point) {
        WaypointsManager manager = XaeroMinimap.instance.getWaypointsManager();
        WaypointWorldContainer container = manager.getWorldContainer(manager.getAutoContainerID().split("/")[0]);
        container = container.addSubContainer(point.worldDIM);
        WaypointSet set = container.addWorld("waypoints").getSets().get(point.setName);
        xaero.common.minimap.waypoints.Waypoint waypoint = (xaero.common.minimap.waypoints.Waypoint) points
                .remove(point.questID);
        if(waypoint==null) {
            return;
        }
        set.getList().remove(waypoint);
        setFromPoint.remove(waypoint);
    }

    public static void clearAllWaypoint() {
        points.clear();
        for (Object point : setFromPoint.keySet().toArray()) {
            setFromPoint.get((xaero.common.minimap.waypoints.Waypoint) point).getList()
                    .remove(((xaero.common.minimap.waypoints.Waypoint) point));
        }
        setFromPoint.clear();
    }

    public static Waypoint getQuestWaypoint(Quest quest) {
        try {
            return ((Waypoint) Class.forName("noppes.npcs.controllers.data.Quest").getField("waypoint").get(quest)).bind(quest);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException
                | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Waypoint();
    }

    public static void onWriteNbt(Quest quest, NBTTagCompound nbt) {
        Waypoint point = getQuestWaypoint(quest);
        nbt.setTag("Waypoint", point.writeNBT());
    }

    public static void onReadNbt(Quest quest, NBTTagCompound nbt) {
        Waypoint point = getQuestWaypoint(quest);
        point.readNBT(nbt.getCompoundTag("Waypoint"));
    }
}
