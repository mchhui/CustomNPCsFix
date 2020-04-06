package net.minecraftforge.fml.common.network;

import java.lang.reflect.Field;

import mchhui.customnpcsfix.api.EventHook;
import mchhui.customnpcsfix.api.event.CustomNPCsSendPacketEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class FixEventChannel extends FMLEventChannel {
    public static FixEventChannel channel;

    public FixEventChannel(FMLEventChannel channel) {
        super("FixEventChannel");
        Class clazz = FMLEventChannel.class;
        Field fieldChannels = null;
        Field fieldEventBus = null;

        try {
            fieldChannels = clazz.getDeclaredField("channels");
            fieldEventBus = clazz.getDeclaredField("eventBus");
            fieldChannels.setAccessible(true);
            fieldEventBus.setAccessible(true);
            fieldChannels.set(this, fieldChannels.get(channel));
            fieldEventBus.set(this, fieldEventBus.get(channel));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.channel = this;
    }

    @Override
    public void sendTo(FMLProxyPacket pkt, EntityPlayerMP player) {
        FMLLog.log.info("test2");
        CustomNPCsSendPacketEvent event = new CustomNPCsSendPacketEvent(pkt, player);
        if (EventHook.onCustomNPCsSendPacket(event)) {
            return;
        }
        pkt = event.packet;
        player = event.player;
        super.sendTo(pkt, player);
    }
}
