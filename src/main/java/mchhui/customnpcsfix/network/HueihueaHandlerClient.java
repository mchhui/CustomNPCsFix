package mchhui.customnpcsfix.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.EnumHandler;
import mchhui.customnpcsfix.constants.EnumPacketServer;
import mchhui.customnpcsfix.controllers.data.Waypoint;
import mchhui.customnpcsfix.util.QuestHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import noppes.npcs.Server;

public class HueihueaHandlerClient {
    public static class WaypointHandler {
        @SubscribeEvent
        public void onServerPacket(ClientCustomPacketEvent event) {
            ByteBuf buffer = event.getPacket().payload().copy();
            EnumPacketServer type = EnumPacketServer.values()[buffer.readInt()];
            if (type == EnumPacketServer.ADD_WAYPOINT) {
                try {
                    NBTTagCompound nbt = Server.readNBT(buffer);
                    Waypoint waypoint = new Waypoint();
                    waypoint.readNBT(nbt);
                    QuestHelper.addWaypoint(waypoint);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (type == EnumPacketServer.REMOVE_WAYPOINT) {
                try {
                    NBTTagCompound nbt = Server.readNBT(buffer);
                    Waypoint waypoint = new Waypoint();
                    waypoint.readNBT(nbt);
                    QuestHelper.removeWaypoint(waypoint);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (type == EnumPacketServer.CLEAR_WAYPOINT) {
                QuestHelper.clearAllWaypoint();
            }
            buffer.release();
        }
    }
}
