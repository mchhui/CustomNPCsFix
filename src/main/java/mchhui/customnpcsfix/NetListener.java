package mchhui.customnpcsfix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.ibm.icu.text.SimpleDateFormat;

import io.netty.buffer.ByteBuf;
import mchhui.customnpcsfix.api.event.CustomNPCsSendPacketEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumPacketServer;

public class NetListener {
    private static ArrayList<UUID> ListenedNetPlayer = new ArrayList<UUID>();
    public static void listen(EntityPlayer player) {
        ListenedNetPlayer.add(player.getUniqueID());
    }

    public static boolean unListen(EntityPlayer player) {
        return ListenedNetPlayer.remove(player.getUniqueID());
    }

    public static boolean unListen(UUID uuid) {
        return ListenedNetPlayer.remove(uuid);
    }

    public static boolean isListening(EntityPlayer player) {
        return ListenedNetPlayer.contains(player.getUniqueID());
    }

    public static boolean isListening(UUID uuid) {
        return ListenedNetPlayer.contains(uuid);
    }

    public static void onPacketHandle(ServerCustomPacketEvent event) {
        if (ListenedNetPlayer.isEmpty()) {
            return;
        }
        String time=DateFormatUtils.format(new Date(), "hh:mm:ss:SSSS");
        String sender = ((NetHandlerPlayServer) event.getHandler()).player.getName();
        ByteBuf buffer = event.getPacket().payload().copy();
        String type = null;
        try {
            type = EnumPacketServer.values()[buffer.readInt()].toString();
        } catch (Exception err) {
        }
        List<EntityPlayer> list = getListeningPlayers();
        for (EntityPlayer player : list) {
            player.sendMessage(new TextComponentString("[" + time + "] " + sender + " > Server " + type));
        }
    }

    public static void onPacketSend(CustomNPCsSendPacketEvent event) {
        if (ListenedNetPlayer.isEmpty()) {
            return;
        }
        String time=DateFormatUtils.format(new Date(), "hh:mm:ss:SSSS");
        String sender = event.player.getName();
        String type = event.enu.toString();
        List<EntityPlayer> list = getListeningPlayers();
        for (EntityPlayer player : list) {
            player.sendMessage(new TextComponentString("[" + time + "] Server > " + sender + " " + type));
        }
    }

    public static List<EntityPlayer> getListeningPlayers() {
        List list = new ArrayList<EntityPlayer>();
        PlayerList players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
        for (UUID uuid : ListenedNetPlayer) {
            EntityPlayer player = players.getPlayerByUUID(uuid);
            if (player != null) {
                list.add(player);
            }
        }
        return list;
    }
}
