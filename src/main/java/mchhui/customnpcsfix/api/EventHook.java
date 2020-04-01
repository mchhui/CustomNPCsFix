package mchhui.customnpcsfix.api;

import mchhui.customnpcsfix.api.event.client.ClientSendDataEvent;
import mchhui.customnpcsfix.api.event.client.RenderCustomNpcEvent;
import net.minecraftforge.common.MinecraftForge;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityCustomNpc;

public class EventHook {
    public static boolean onPreRenderCustomNpc(RenderCustomNpc render, EntityCustomNpc npc) {
        return MinecraftForge.EVENT_BUS.post(new RenderCustomNpcEvent.Pre(render, npc));
    }

    public static void onPostRenderCustomNpc(RenderCustomNpc render, EntityCustomNpc npc) {
        MinecraftForge.EVENT_BUS.post(new RenderCustomNpcEvent.Post(render, npc));
    }

    public static boolean onClientSendData(EnumPacketServer type, Object... obs) {
        return MinecraftForge.EVENT_BUS.post(new ClientSendDataEvent(type, obs));
    }
}
