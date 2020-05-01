package mchhui.customnpcsfix.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.EnumHandler;
import mchhui.customnpcsfix.NetListener;
import mchhui.customnpcsfix.api.EventHook;
import mchhui.customnpcsfix.constants.EnumFixPacketClient;
import mchhui.customnpcsfix.constants.EnumFixPacketServer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import noppes.npcs.Server;

public class HueihueaHandlerServer {
    public static class NPCHandler {
        @SubscribeEvent(priority = EventPriority.HIGH)
        public void onServerPacket(ServerCustomPacketEvent event) {

        }
    }

    public static class FixHandler {
        @SubscribeEvent(priority = EventPriority.HIGH)
        public void onHandle(ServerCustomPacketEvent event) {
            NetListener.onPacketHandle(event);
        }
    }

    public static class WaypointHandler {
        @SubscribeEvent(priority = EventPriority.HIGH)
        public void onHandle(ServerCustomPacketEvent event) {
            ByteBuf buffer = event.getPacket().payload().copy();
            EnumFixPacketServer type = EnumFixPacketServer.values()[buffer.readInt()];
            if (type == EnumFixPacketServer.GETALLQUESTWAYPOINT) {
                EventHook.OnPlayerGetAllQuestWaypoint(((NetHandlerPlayServer) event.getHandler()).player);
            }
            buffer.release();
        }
    }
}
