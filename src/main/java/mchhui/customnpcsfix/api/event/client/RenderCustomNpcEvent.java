package mchhui.customnpcsfix.api.event.client;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.entity.EntityCustomNpc;

public class RenderCustomNpcEvent extends LivingEvent {
    private final EntityCustomNpc npc;
    private final RenderCustomNpc render;

    public RenderCustomNpcEvent(RenderCustomNpc render, EntityCustomNpc entity) {
        super(entity);
        this.npc = entity;
        this.render = render;
    }

    @Cancelable
    public static class Pre extends RenderCustomNpcEvent {

        public Pre(RenderCustomNpc render, EntityCustomNpc entity) {
            super(render, entity);
            // TODO 自动生成的构造函数存根
        }

    }

    public static class Post extends RenderCustomNpcEvent {

        public Post(RenderCustomNpc render, EntityCustomNpc entity) {
            super(render, entity);
            // TODO 自动生成的构造函数存根
        }
    }
    
    public EntityCustomNpc getNPC() {
        return this.npc;
    }
}
