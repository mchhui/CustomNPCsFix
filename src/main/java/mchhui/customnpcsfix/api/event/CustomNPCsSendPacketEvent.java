package mchhui.customnpcsfix.api.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

@Cancelable
public class CustomNPCsSendPacketEvent extends Event {
    public FMLProxyPacket packet;
    public EntityPlayerMP player;

    public CustomNPCsSendPacketEvent(FMLProxyPacket packet, EntityPlayerMP player) {
        this.packet = packet;
        this.player = player;
    }
}
