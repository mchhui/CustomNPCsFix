package mchhui.customnpcsfix;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import mchhui.customnpcsfix.constants.EnumFixPacketClient;
import mchhui.customnpcsfix.constants.EnumFixPacketServer;
import mchhui.customnpcsfix.controllers.data.Waypoint;
import mchhui.customnpcsfix.util.CustomNPCsFixScheduler;
import mchhui.customnpcsfix.util.QuestHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import noppes.npcs.controllers.data.Quest;

public class Client {
    public static void getAllQuestWaypoint() {
        CustomNPCsFixScheduler.runTack(() -> {
            PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            try {
                if (!noppes.npcs.Server.fillBuffer(buffer, EnumFixPacketServer.GETALLQUESTWAYPOINT,
                        new Object[] {})) {
                    return;
                }
                CustomNPCsFix.Channel.sendToServer(new FMLProxyPacket(buffer, "CustomNPCsFix"));
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        });
    }
}
