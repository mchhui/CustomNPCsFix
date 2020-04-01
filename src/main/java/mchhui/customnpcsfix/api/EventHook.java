package mchhui.customnpcsfix.api;

import mchhui.customnpcsfix.api.event.client.ClientSendDataEvent;
import mchhui.customnpcsfix.api.event.client.RenderNPCHumanMaleEvent;
import net.minecraftforge.common.MinecraftForge;
import noppes.npcs.client.renderer.RenderNPCHumanMale;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EventHook {
    public static boolean onPreRenderNPCHumanMale(RenderNPCHumanMale render, EntityNPCInterface npc) {
        return MinecraftForge.EVENT_BUS.post(new RenderNPCHumanMaleEvent.Pre(render, npc));
    }

    public static void onPostRenderNPCHumanMale(RenderNPCHumanMale render, EntityNPCInterface npc) {
        MinecraftForge.EVENT_BUS.post(new RenderNPCHumanMaleEvent.Post(render, npc));
    }

    public static boolean onClientSendData(EnumPacketServer type, Object... obs) {
        return MinecraftForge.EVENT_BUS.post(new ClientSendDataEvent(type, obs));
    }
}
