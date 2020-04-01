package mchhui.customnpcsfix.listener.client;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.api.event.client.RenderNPCHumanMaleEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class NPCRenderListener {
    @SubscribeEvent
    public void onRender(RenderNPCHumanMaleEvent.Pre event) {
        if (!Config.SafeRenderNoTexNPC) {
            return;
        }
        EntityNPCInterface npc = event.getNPC();
        if (npc.display.texture.isEmpty()) {
            npc.display.texture = "customnpcsfix:textures/null.png";
        }
    }

    @SubscribeEvent
    public void onRender(RenderNPCHumanMaleEvent.Post event) {
        if (!Config.SafeRenderNoTexNPC) {
            return;
        }
        EntityNPCInterface npc = event.getNPC();
        if (npc.display.texture.equals("customnpcsfix:textures/null.png")) {
            npc.display.texture = "";
        }
    }
}
