package mchhui.customnpcsfix;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import mchhui.customnpcsfix.constants.EnumFixPacketClient;
import mchhui.customnpcsfix.controllers.data.Waypoint;
import mchhui.customnpcsfix.util.CustomNPCsFixScheduler;
import mchhui.customnpcsfix.util.QuestHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
            if (Config.QuestWaypointFromWorldName) {
                boolean isFound=false;
                for (World world : FMLCommonHandler.instance().getMinecraftServerInstance().worlds) {
                    if (world.getWorldInfo().getWorldName().equals(point.worldName)) {
                        point.worldDIM = world.provider.getDimension();
                        isFound=true;
                    }
                }
                if(!isFound) {
                    return;
                }
            }
            try {
                if (!noppes.npcs.Server.fillBuffer(buffer, EnumFixPacketClient.ADD_WAYPOINT,
                        new Object[] { point.writeNbtWithDIM() })) {
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
                Waypoint point = QuestHelper.getQuestWaypoint(quest);
                if (!noppes.npcs.Server.fillBuffer(buffer, EnumFixPacketClient.REMOVE_WAYPOINT,
                        new Object[] { point.writeNbtWithDIM() })) {
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
                if (!noppes.npcs.Server.fillBuffer(buffer, EnumFixPacketClient.CLEAR_WAYPOINT)) {
                    return;
                }
                CustomNPCsFix.Channel.sendTo(new FMLProxyPacket(buffer, "CustomNPCsFix"), player);
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        });
    }

    public static void sendIsWaypointFromDIM(EntityPlayerMP player, boolean isFromDIM) {
        CustomNPCsFixScheduler.runTack(() -> {
            PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            try {
                if (!noppes.npcs.Server.fillBuffer(buffer, EnumFixPacketClient.SETTING_IS_WAYPOINT_FROM_DIM,
                        new Object[] { isFromDIM })) {
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
