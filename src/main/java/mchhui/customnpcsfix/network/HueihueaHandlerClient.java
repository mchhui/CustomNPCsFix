package mchhui.customnpcsfix.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.EnumHandler;
import mchhui.customnpcsfix.client.gui.HueihueaGuiQuestWaypoint;
import mchhui.customnpcsfix.constants.EnumFixPacketClient;
import mchhui.customnpcsfix.controllers.data.Waypoint;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsGuiRendererTranformer;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsIngameRendererTranformer;
import mchhui.customnpcsfix.util.QuestHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import noppes.npcs.Server;
import noppes.npcs.constants.EnumPacketClient;

public class HueihueaHandlerClient {
    public static class WaypointHandler {
        @SubscribeEvent
        public void onServerPacket(ClientCustomPacketEvent event) {
            ByteBuf buffer = event.getPacket().payload().copy();
            EnumFixPacketClient type = EnumFixPacketClient.values()[buffer.readInt()];
            if (type == EnumFixPacketClient.ADD_WAYPOINT) {
                try {
                    NBTTagCompound nbt = Server.readNBT(buffer);
                    Waypoint waypoint = new Waypoint();
                    waypoint.readNBT(nbt);
                    QuestHelper.addWaypoint(waypoint);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (type == EnumFixPacketClient.REMOVE_WAYPOINT) {
                try {
                    NBTTagCompound nbt = Server.readNBT(buffer);
                    Waypoint waypoint = new Waypoint();
                    waypoint.readNBT(nbt);
                    QuestHelper.removeWaypoint(waypoint);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (type == EnumFixPacketClient.CLEAR_WAYPOINT) {
                QuestHelper.clearAllWaypoint();
            }
            if (type == EnumFixPacketClient.SETTING_IS_WAYPOINT_FROM_DIM) {
                HueihueaGuiQuestWaypoint.isFromDIM = buffer.readBoolean();
            }
            buffer.release();
        }
    }

    public static class FixHandler {
        @SubscribeEvent(priority = EventPriority.HIGH)
        public void onHandle(ClientCustomPacketEvent event) {

        }
    }
}
