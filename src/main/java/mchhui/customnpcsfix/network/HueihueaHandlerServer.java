package mchhui.customnpcsfix.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.EnumHandler;
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
            ByteBuf buffer = event.getPacket().payload().copy();
            EnumPacketServer type = EnumPacketServer.values()[buffer.readInt()];

            if (type != EnumPacketServer.ScriptBlockDataSave && type != EnumPacketServer.ScriptDataSave
                    && type != EnumPacketServer.ScriptDoorDataSave && type != EnumPacketServer.ScriptForgeSave
                    && type != EnumPacketServer.ScriptItemDataSave && type != EnumPacketServer.ScriptPlayerSave) {
                return;
            }

            try {
                NBTTagCompound nbt = Server.readNBT(buffer);
                if (!nbt.getBoolean("ScriptEnabled")) {
                    if (nbt.getTagList("Scripts", 10).hasNoTags()) {
                        if (Config.DontAccpetDubiousScript) {
                            event.getPacket().payload().clear();
                            event.getPacket().payload().writeInt(EnumHandler.NOTHING.ordinal());
                        }
                    }
                }
            } catch (IOException e) {
                
            }

        }
    }
}
