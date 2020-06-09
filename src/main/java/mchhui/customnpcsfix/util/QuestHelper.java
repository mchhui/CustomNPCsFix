package mchhui.customnpcsfix.util;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.mamiyaotaru.voxelmap.VoxelMap;
import com.mamiyaotaru.voxelmap.WaypointManager;

import journeymap.client.model.Waypoint.Type;
import journeymap.client.waypoint.WaypointStore;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.controllers.data.Waypoint;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsGuiRendererTranformer;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsIngameRendererTranformer;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import noppes.npcs.controllers.data.Quest;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointWorldContainer;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.common.minimap.waypoints.render.WaypointsIngameRenderer;
import xaero.minimap.XaeroMinimap;

public class QuestHelper {
    public static Lock lock = new ReentrantLock();

    public static void addWaypoint(Waypoint point) {
        if (Config.QuestWaypointJMapMode) {
            JMap.addWaypoint(point);
            return;
        }
        if (Config.QuestWaypointVMapMode) {
            VMap.addWaypoint(point);
            return;
        }
        XMap.addWaypoint(point);
    }

    public static void removeWaypoint(Waypoint point) {
        if (Config.QuestWaypointJMapMode) {
            JMap.removeWaypoint(point);
            return;
        }
        if (Config.QuestWaypointVMapMode) {
            VMap.removeWaypoint(point);
            return;
        }
        XMap.removeWaypoint(point);
    }

    public static void clearAllWaypoint() {
        if (Config.QuestWaypointJMapMode) {
            JMap.clearAllWaypoint();
            return;
        }
        if (Config.QuestWaypointVMapMode) {
            VMap.clearAllWaypoint();
            return;
        }
        XMap.clearAllWaypoint();
    }

    public static class XMap {
        public static Map<Integer, xaero.common.minimap.waypoints.Waypoint> points = new HashMap<Integer, xaero.common.minimap.waypoints.Waypoint>();
        public static Map<xaero.common.minimap.waypoints.Waypoint, WaypointSet> setFromPoint = new HashMap<xaero.common.minimap.waypoints.Waypoint, WaypointSet>();

        public static void addWaypoint(Waypoint point) {
            if (!WaypointsGuiRendererTranformer.isSuccessful || !WaypointsIngameRendererTranformer.isSuccessful) {
                return;
            }
            WaypointsManager manager = XaeroMinimap.instance.getWaypointsManager();
            WaypointWorldContainer container = manager.getWorldContainer(manager.getAutoContainerID().split("/")[0]);
            container = container.addSubContainer("dim%" + point.worldDIM);
            WaypointSet set = container.getFirstWorld().getSets().get(point.setName);
            xaero.common.minimap.waypoints.Waypoint waypoint = new xaero.common.minimap.waypoints.Waypoint(point.x,
                    point.y, point.z, point.displayName, "", ((int) (Math.random() * 255) * 1000000
                            + (int) (Math.random() * 255) * 1000 + (int) (Math.random() * 255)),
                    1001, true);
            points.put(point.questID, waypoint);
            CustomNPCsFixScheduler.runTack(() -> {
                try {
                    QuestHelper.lock.lock();
                    setFromPoint.put(waypoint, set);
                    set.getList().add(waypoint);
                } catch (Exception err) {
                    err.printStackTrace();
                } finally {
                    QuestHelper.lock.unlock();
                }
            });
        }

        public static void removeWaypoint(Waypoint point) {
            if (!WaypointsGuiRendererTranformer.isSuccessful || !WaypointsIngameRendererTranformer.isSuccessful) {
                return;
            }
            xaero.common.minimap.waypoints.Waypoint waypoint = (xaero.common.minimap.waypoints.Waypoint) points
                    .remove(point.questID);
            if (waypoint == null) {
                return;
            }
            WaypointsManager manager = XaeroMinimap.instance.getWaypointsManager();
            WaypointWorldContainer container = manager.getWorldContainer(manager.getAutoContainerID().split("/")[0]);
            container = container.addSubContainer(new StringBuilder().append("dim%").append(point.worldDIM).toString());
            WaypointSet set = container.getFirstWorld().getSets().get(point.setName);
            CustomNPCsFixScheduler.runTack(() -> {
                try {
                    QuestHelper.lock.lock();
                    set.getList().remove(waypoint);
                    setFromPoint.remove(waypoint);
                } catch (Exception err) {
                    err.printStackTrace();
                } finally {
                    QuestHelper.lock.unlock();
                }
            });
        }

        public static void clearAllWaypoint() {
            if (!WaypointsGuiRendererTranformer.isSuccessful || !WaypointsIngameRendererTranformer.isSuccessful) {
                return;
            }
            points.clear();
            CustomNPCsFixScheduler.runTack(() -> {
                try {
                    QuestHelper.lock.lock();
                    for (Object point : setFromPoint.keySet().toArray()) {
                        setFromPoint.get((xaero.common.minimap.waypoints.Waypoint) point).getList()
                                .remove(((xaero.common.minimap.waypoints.Waypoint) point));
                    }
                    setFromPoint.clear();
                } catch (Exception err) {
                    err.printStackTrace();
                } finally {
                    QuestHelper.lock.unlock();
                }
            });
        }

    }

    public static class JMap {
        public static void addWaypoint(Waypoint point) {
            HueihueaJMapWaypoint waypoint = new HueihueaJMapWaypoint(point.displayName,
                    new BlockPos(point.x, point.y, point.z),
                    new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())),
                    Type.Normal, point.worldDIM, "customnpcsfix:textures/waypoints/waypoint_quest.png");
            WaypointStore.INSTANCE.add(waypoint);
        }

        public static void removeWaypoint(Waypoint point) {
            Collection<journeymap.client.model.Waypoint> Waypoints = WaypointStore.INSTANCE.getAll();
            for (journeymap.client.model.Waypoint waypoint : Waypoints) {
                if (waypoint.getOrigin().equals("NPCFIX") && waypoint.getName().equals(point.displayName)) {
                    WaypointStore.INSTANCE.remove(waypoint);
                }
            }
        }

        public static void clearAllWaypoint() {
            Collection<journeymap.client.model.Waypoint> Waypoints = WaypointStore.INSTANCE.getAll();
            for (journeymap.client.model.Waypoint waypoint : Waypoints) {
                if (waypoint.getOrigin().equals("NPCFIX")) {
                    WaypointStore.INSTANCE.remove(waypoint);
                }
            }
        }
    }

    public static class VMap {
        public static Map<Integer, com.mamiyaotaru.voxelmap.util.Waypoint> points = new HashMap<Integer, com.mamiyaotaru.voxelmap.util.Waypoint>();

        public static void addWaypoint(Waypoint point) {
            TreeSet<Integer> set = new TreeSet<Integer>();
            set.add(point.worldDIM);
            com.mamiyaotaru.voxelmap.util.Waypoint waypoint = new com.mamiyaotaru.voxelmap.util.Waypoint(
                    point.displayName, point.x, point.y, point.z, true, (float) Math.random(), (float) Math.random(),
                    (float) Math.random(), "quest", "", set);
            CustomNPCsFixScheduler.runTack(() -> {
                try {
                    QuestHelper.lock.lock();
                    VoxelMap.getInstance().getWaypointManager().addWaypoint(waypoint);
                    points.put(point.questID, waypoint);
                } catch (Exception err) {
                    err.printStackTrace();
                } finally {
                    QuestHelper.lock.unlock();
                }
            });
        }

        public static void removeWaypoint(Waypoint point) {
            CustomNPCsFixScheduler.runTack(() -> {
                try {
                    QuestHelper.lock.lock();
                    VoxelMap.getInstance().getWaypointManager().deleteWaypoint(points.get(point.questID));
                    points.remove(point.questID);
                } catch (Exception err) {
                    err.printStackTrace();
                } finally {
                    QuestHelper.lock.unlock();
                }
            });
        }

        public static void clearAllWaypoint() {
            Iterator<com.mamiyaotaru.voxelmap.util.Waypoint> iterator = VoxelMap.getInstance().getWaypointManager()
                    .getWaypoints().iterator();
            CustomNPCsFixScheduler.runTack(() -> {
                try {
                    QuestHelper.lock.lock();
                    while (iterator.hasNext()) {
                        com.mamiyaotaru.voxelmap.util.Waypoint point = iterator.next();
                        if (point.imageSuffix.equals("quest")) {
                            iterator.remove();
                        }
                    }
                    points.clear();
                } catch (Exception err) {
                    err.printStackTrace();
                } finally {
                    QuestHelper.lock.unlock();
                }
            });
        }
    }

    public static Waypoint getQuestWaypoint(Quest quest) {
        try {
            return ((Waypoint) Class.forName("noppes.npcs.controllers.data.Quest").getField("waypoint").get(quest))
                    .bind(quest);
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
