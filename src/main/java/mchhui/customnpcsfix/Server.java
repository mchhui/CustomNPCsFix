package mchhui.customnpcsfix;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import mchhui.customnpcsfix.constants.EnumPacketServer;
import mchhui.customnpcsfix.controllers.data.Waypoint;
import mchhui.customnpcsfix.util.CustomNPCsFixScheduler;
import mchhui.customnpcsfix.util.QuestHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import noppes.npcs.controllers.data.Quest;

public class Server {
    public static void sendAddWaypoint(EntityPlayerMP player, Quest quest) {
        CustomNPCsFixScheduler.runTack(() -> {
            PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            Waypoint point = QuestHelper.getQuestWaypoint(quest);
            if (!point.isEnabled) {
                return;
            }
            try {
                if (!noppes.npcs.Server.fillBuffer(buffer, EnumPacketServer.ADD_WAYPOINT,
                        new Object[] { point.writeNBT() })) {
                    return;
                }
                CustomNPCsFix.Channel.sendTo(new FMLProxyPacket(buffer, "CustomNPCsFix"), player);
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        });
    }

    public static void sendRemoveWaypoint(EntityPlayerMP player, Quest quest) {
        CustomNPCsFixScheduler.runTack(() -> {
            PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            try {
                if (!noppes.npcs.Server.fillBuffer(buffer, EnumPacketServer.REMOVE_WAYPOINT,
                        new Object[] { QuestHelper.getQuestWaypoint(quest).writeNBT() })) {
                    return;
                }
                CustomNPCsFix.Channel.sendTo(new FMLProxyPacket(buffer, "CustomNPCsFix"), player);
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        });
    }

    public static void sendClearWaypoint(EntityPlayerMP player) {
        CustomNPCsFixScheduler.runTack(() -> {
            PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            try {
                if (!noppes.npcs.Server.fillBuffer(buffer, EnumPacketServer.CLEAR_WAYPOINT)) {
                    return;
                }
                CustomNPCsFix.Channel.sendTo(new FMLProxyPacket(buffer, "CustomNPCsFix"), player);
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        });
    }
}
