package mchhui.customnpcsfix.api.event.client;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
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
