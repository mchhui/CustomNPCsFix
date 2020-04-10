package mchhui.customnpcsfix.api;

import mchhui.customnpcsfix.NetListener;
import mchhui.customnpcsfix.api.event.CustomNPCsPreSendPacketEvent;
import mchhui.customnpcsfix.api.event.CustomNPCsSendPacketEvent;
import mchhui.customnpcsfix.api.event.client.ClientSendDataEvent;
import mchhui.customnpcsfix.api.event.client.RenderCustomNpcEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.constants.EnumPacketClient;
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

    public static boolean onCustomNPCsSendPacket(EntityPlayerMP player, EnumPacketClient enu, Object... obs) {
        CustomNPCsSendPacketEvent event = new CustomNPCsSendPacketEvent(player, enu, obs);
        NetListener.onPacketSend(event);
        return MinecraftForge.EVENT_BUS.post(event);
    }
    
    public static boolean onCustomNPCsPreSendPacket(EnumPacketClient enu, Object... obs) {
        CustomNPCsPreSendPacketEvent event = new CustomNPCsPreSendPacketEvent(enu, obs);
        NetListener.onPrePacketSend(event);
        return MinecraftForge.EVENT_BUS.post(event);
    }
}
