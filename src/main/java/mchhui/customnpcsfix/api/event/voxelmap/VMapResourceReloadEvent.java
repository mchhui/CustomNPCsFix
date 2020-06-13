package mchhui.customnpcsfix.api.event.voxelmap;

import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.fml.common.eventhandler.Event;

public class VMapResourceReloadEvent extends Event{
    public final IResourceManager resourceManager;

    protected VMapResourceReloadEvent(IResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public static class Pre extends VMapResourceReloadEvent {
        public Pre(IResourceManager resourceManager) {
            super(resourceManager);
        }
    }

    public static class Post extends VMapResourceReloadEvent {
        public Post(IResourceManager resourceManager) {
            super(resourceManager);
        }
    }
}
