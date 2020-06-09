package mchhui.customnpcsfix.listener.client;

import mchhui.customnpcsfix.util.QuestHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderGameOverlayListener {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void preRender(RenderGameOverlayEvent.Pre event) {
        QuestHelper.lock.lock();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void postRender(RenderGameOverlayEvent.Post event) {
        QuestHelper.lock.unlock();
    }
}
