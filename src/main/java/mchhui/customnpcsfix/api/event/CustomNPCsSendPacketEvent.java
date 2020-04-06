package mchhui.customnpcsfix.api.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import noppes.npcs.constants.EnumPacketClient;

@Cancelable
public class CustomNPCsSendPacketEvent extends Event {
    public final EnumPacketClient enu;
    public final EntityPlayerMP player;
    public final Object[] obs;
    public CustomNPCsSendPacketEvent(EntityPlayerMP player,EnumPacketClient enu, Object... obs) {
        this.enu = enu;
        this.player = player;
        this.obs=obs;
    }
}
