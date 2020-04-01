package mchhui.customnpcsfix.api.event.client;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import noppes.npcs.client.renderer.RenderNPCHumanMale;
import noppes.npcs.entity.EntityNPCInterface;

public class RenderNPCHumanMaleEvent extends LivingEvent {
    private final EntityNPCInterface npc;
    private final RenderNPCHumanMale render;

    public RenderNPCHumanMaleEvent(RenderNPCHumanMale render, EntityNPCInterface entity) {
        super(entity);
        this.npc = entity;
        this.render = render;
    }

    @Cancelable
    public static class Pre extends RenderNPCHumanMaleEvent {

        public Pre(RenderNPCHumanMale render, EntityNPCInterface entity) {
            super(render, entity);
            // TODO 自动生成的构造函数存根
        }

    }

    public static class Post extends RenderNPCHumanMaleEvent {

        public Post(RenderNPCHumanMale render, EntityNPCInterface entity) {
            super(render, entity);
            // TODO 自动生成的构造函数存根
        }
    }
    
    public EntityNPCInterface getNPC() {
        return this.npc;
    }
}
