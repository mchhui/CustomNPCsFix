package mchhui.customnpcsfix.api;

import mchhui.customnpcsfix.NetListener;
import mchhui.customnpcsfix.api.event.CustomNPCsPreSendPacketEvent;
import mchhui.customnpcsfix.api.event.CustomNPCsSendPacketEvent;
import mchhui.customnpcsfix.api.event.OnPlayerGetAllQuestWaypointEvent;
import mchhui.customnpcsfix.api.event.client.ClientSendDataEvent;
import mchhui.customnpcsfix.api.event.client.RenderCustomNpcEvent;
import mchhui.customnpcsfix.api.event.xaerominimap.DrawIconOnGUIEvent;
import mchhui.customnpcsfix.api.event.xaerominimap.RenderWaypointIngameEvent;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityCustomNpc;
import xaero.common.IXaeroMinimap;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;
import xaero.common.minimap.waypoints.render.WaypointsIngameRenderer;
import xaero.common.settings.ModSettings;

public class EventHook {
    public static boolean onPreRenderCustomNpc(RenderCustomNpc render, EntityCustomNpc npc) {
        return MinecraftForge.EVENT_BUS.post(new RenderCustomNpcEvent.Pre(render, npc));
    }

    public static void onPostRenderCustomNpc(RenderCustomNpc render, EntityCustomNpc npc) {
        MinecraftForge.EVENT_BUS.post(new RenderCustomNpcEvent.Post(render, npc));
    }

    public static boolean onClientSendData(EnumPacketServer type, Object... obs) {
        return MinecraftForge.EVENT_BUS.post(new ClientSendDataEvent(type, obs));
    }

    public static boolean onCustomNPCsSendPacket(EntityPlayerMP player, EnumPacketClient enu, Object... obs) {
        CustomNPCsSendPacketEvent event = new CustomNPCsSendPacketEvent(player, enu, obs);
        NetListener.onPacketSend(event);
        return MinecraftForge.EVENT_BUS.post(event);
    }

    public static boolean onCustomNPCsPreSendPacket(EnumPacketClient enu, Object... obs) {
        CustomNPCsPreSendPacketEvent event = new CustomNPCsPreSendPacketEvent(enu, obs);
        NetListener.onPrePacketSend(event);
        return MinecraftForge.EVENT_BUS.post(event);
    }

    public static boolean onDrawIconOnGUI(WaypointsGuiRenderer renderer, Waypoint waypoint, ModSettings settings,
            int drawX, int drawY) {
        return MinecraftForge.EVENT_BUS.post(new DrawIconOnGUIEvent(renderer, waypoint, settings, drawX, drawY));
    }

    public static boolean onRenderWaypointIngame(WaypointsIngameRenderer renderer, float cameraAngleYaw,
            Vec3d lookVector, int lookVectorMultiplier, double eyesX, double eyesY, double eyesZ, Waypoint waypoint,
            IXaeroMinimap modMain, double radius, double d3, double d4, double d5, Entity entity,
            BufferBuilder bufferBuilder, Tessellator tessellator, boolean divideBy8) {
        return MinecraftForge.EVENT_BUS.post(
                new RenderWaypointIngameEvent(renderer, cameraAngleYaw, lookVector, lookVectorMultiplier, eyesX, eyesY,
                        eyesZ, waypoint, modMain, radius, d3, d4, d5, entity, bufferBuilder, tessellator, divideBy8));
    }
    
    public static void OnPlayerGetAllQuestWaypoint(EntityPlayerMP player) {
        MinecraftForge.EVENT_BUS.post(new OnPlayerGetAllQuestWaypointEvent(player));
    }
}
