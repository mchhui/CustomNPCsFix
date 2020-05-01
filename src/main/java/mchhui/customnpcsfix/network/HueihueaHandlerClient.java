package mchhui.customnpcsfix.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.EnumHandler;
import mchhui.customnpcsfix.constants.EnumFixPacketClient;
import mchhui.customnpcsfix.controllers.data.Waypoint;
import mchhui.customnpcsfix.util.QuestHelper;
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
            buffer.release();
        }
    }

    public static class FixHandler {
        @SubscribeEvent(priority = EventPriority.HIGH)
        public void onHandle(ClientCustomPacketEvent event) {
            ByteBuf buffer = event.getPacket().payload().copy();
            EnumPacketClient type = EnumPacketClient.values()[buffer.readInt()];
            if (Config.DontUseScriptItemTextures) {
                if (type == EnumPacketClient.SYNC_ADD || type == EnumPacketClient.SYNC_END) {
                    if (buffer.readInt() == 9) {
                        event.getPacket().payload().clear();
                        try {
                            Server.fillBuffer(buffer, EnumHandler.NOTHING, new Object[] {});
                        } catch (IOException e) {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }
                    }
                }
            }
            buffer.release();
        }
    }
}
