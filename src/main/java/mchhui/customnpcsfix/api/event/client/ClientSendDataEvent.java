package mchhui.customnpcsfix.api.event.client;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import noppes.npcs.constants.EnumPacketServer;

@Cancelable
public class ClientSendDataEvent extends Event {
    public final EnumPacketServer type;
    public final Object[] obs;

    public ClientSendDataEvent(EnumPacketServer type, Object... obs) {
        this.type = type;
        this.obs = obs;
    }
}
