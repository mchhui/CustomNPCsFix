package mchhui.customnpcsfix.listener.client;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.api.event.client.RenderCustomNpcEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class NPCRenderListener {
    @SubscribeEvent
    public void onRender(RenderCustomNpcEvent.Pre event) {
        if (!Config.SafeRenderNoTexNPC) {
            return;
        }
        EntityCustomNpc npc = event.getNPC();
        if (npc.display.getSkinTexture().isEmpty()) {
            npc.display.setSkinTexture("customnpcsfix:textures/null.png");
            npc.modelData.eyes.type=-1;
        }
    }

    @SubscribeEvent
    public void onRender(RenderCustomNpcEvent.Post event) {
        if (!Config.SafeRenderNoTexNPC) {
            return;
        }
        EntityCustomNpc npc = event.getNPC();
        if (npc.display.getSkinTexture().equals("customnpcsfix:textures/null.png")) {
            npc.display.setSkinTexture("");
        }
    }
}
