package mchhui.customnpcsfix;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.ibm.icu.text.SimpleDateFormat;

import io.netty.buffer.ByteBuf;
import mchhui.customnpcsfix.api.event.CustomNPCsPreSendPacketEvent;
import mchhui.customnpcsfix.api.event.CustomNPCsSendPacketEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumPacketServer;

public class NetListener {
    private static Map<UUID, ListenData> ListenedNetPlayer = new HashMap<UUID, ListenData>();
    private static long beginTime;

    public static void listen(EntityPlayer player, ListenData data) {
        ListenedNetPlayer.put(player.getUniqueID(), data);
    }

    public static boolean unListen(EntityPlayer player) {
        return unListen(player.getUniqueID());
    }

    public static boolean unListen(UUID uuid) {
        return ListenedNetPlayer.remove(uuid) != null;
    }

    public static boolean isListening(EntityPlayer player) {
        return isListening(player.getUniqueID());
    }

    public static boolean isListening(UUID uuid) {
        return ListenedNetPlayer.containsKey(uuid);
    }

    public static void onPacketHandle(ServerCustomPacketEvent event) {
        if (ListenedNetPlayer.isEmpty()) {
            return;
        }
        String time = DateFormatUtils.format(new Date(), "hh:mm:ss:SSSS");
        String sender = ((NetHandlerPlayServer) event.getHandler()).player.getName();
        ByteBuf buffer = event.getPacket().payload().copy();
        String type = null;
        try {
            type = EnumPacketServer.values()[buffer.readInt()].toString();
        } catch (Exception err) {
        }
        List<EntityPlayer> list = getListeningPlayers();
        for (EntityPlayer player : list) {
            ListenData data = ListenedNetPlayer.get(player.getUniqueID());
            if (!data.matchedFrom(sender)) {
                continue;
            }
            if (!data.matchedTo("Server")) {
                continue;
            }
            if (!data.matchedType(type)) {
                continue;
            }
            player.sendMessage(new TextComponentString("[" + time + "] " + sender + " > Server " + type));
        }
        buffer.release();
    }

    public static void onPacketSend(CustomNPCsSendPacketEvent event) {
        if (ListenedNetPlayer.isEmpty()) {
            return;
        }
        Date date = new Date();
        String time = DateFormatUtils.format(date, "hh:mm:ss:SSSS");
        String sender = event.player.getName();
        String type = event.enu.toString();
        int timeout = (int) (date.getTime() - NetListener.beginTime);
        List<EntityPlayer> list = getListeningPlayers();
        FMLLog.log.info(time+","+type);
        for (EntityPlayer player : list) {
            ListenData data = ListenedNetPlayer.get(player.getUniqueID());
            if (!data.matchedFrom("Server")) {
                continue;
            }
            if (!data.matchedTo(sender)) {
                continue;
            }
            if (!data.matchedType(type)) {
                continue;
            }
            if (!data.isOutTime(timeout)) {
                continue;
            }
            player.sendMessage(
                    new TextComponentString("[" + time + "] Server > " + sender + " " + type + " 耗时" + timeout + "ms"));
            if (data.printStack) {
                for (StackTraceElement stack : Thread.currentThread().getStackTrace()) {
                    if (!(stack.getClassName().startsWith("mchhui") || stack.getClassName().startsWith("noppes"))) {
                        continue;
                    }
                    player.sendMessage(new TextComponentString(stack.getClassName() + "." + stack.getMethodName()));
                }
            }
        }
    }

    public static void onPrePacketSend(CustomNPCsPreSendPacketEvent event) {
        if (ListenedNetPlayer.isEmpty()) {
            return;
        }
        NetListener.beginTime = new Date().getTime();
    }

    public static List<EntityPlayer> getListeningPlayers() {
        List list = new ArrayList<EntityPlayer>();
        PlayerList players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
        for (UUID uuid : ListenedNetPlayer.keySet()) {
            EntityPlayer player = players.getPlayerByUUID(uuid);
            if (player != null) {
                list.add(player);
            }
        }
        return list;
    }

    public static class ListenData {
        public String from = ".*";
        public String to = ".*";
        public String type = ".*";
        public int timeout = -1;
        public boolean printStack = false;

        public ListenData() {
            // TODO 自动生成的构造函数存根
        }

        public boolean matchedFrom(String str) {
            return Pattern.matches(from, str);
        }

        public boolean matchedTo(String str) {
            return Pattern.matches(to, str);
        }

        public boolean matchedType(String str) {
            return Pattern.matches(type, str);
        }

        public boolean isOutTime(int time) {
            return time > timeout;
        }

        public boolean isPrintStack() {
            return this.printStack;
        }
    }
}
