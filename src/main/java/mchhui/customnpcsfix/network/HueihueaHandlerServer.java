package mchhui.customnpcsfix.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.EnumHandler;
import mchhui.customnpcsfix.NetListener;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import noppes.npcs.Server;
import noppes.npcs.constants.EnumPacketServer;

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
}
