 package net.minecraft.client.renderer.entity;

import java.util.List;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;

public class NPCFixLayerHelper {
     public static List<LayerRenderer<EntityLivingBase>>  getLayers(RenderLivingBase<EntityLivingBase> renderLiving){
         return renderLiving.layerRenderers;
     }
}
