package mchhui.customnpcsfix.listener.client;

import mchhui.customnpcsfix.util.QuestHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldRenderListener {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void preRender(RenderWorldLastEvent event) {
        QuestHelper.lock.lock();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void postRender(RenderWorldLastEvent event) {
        QuestHelper.lock.unlock();
    }
}
