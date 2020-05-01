package mchhui.customnpcsfix.listener.xaerominimap;

import mchhui.customnpcsfix.api.event.xaerominimap.DrawIconOnGUIEvent;
import mchhui.customnpcsfix.api.event.xaerominimap.RenderWaypointIngameEvent;
import mchhui.customnpcsfix.util.QuestHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noppes.npcs.controllers.data.Quest;
import xaero.common.interfaces.render.InterfaceRenderer;

public class RenderWaypointsListener {
    private static final ResourceLocation TEX = new ResourceLocation("customnpcsfix", "textures/waypoint_quest.png");

    @SubscribeEvent
    public void onDrawIconOnGUI(DrawIconOnGUIEvent event) {
        if (event.waypoint.getType() != 1001) {
            return;
        }
        GlStateManager.pushMatrix();
        int c = event.waypoint.getColor();
        GlStateManager.color(c % 1000 / 255.0f, c % 1000000 / 1000 / 255.0f, c % 1000000000 / 1000000 / 255.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEX);
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(event.drawX-8, event.drawY-8, 0, 0, 16, 16);
        GlStateManager.popMatrix();
    }

    @SubscribeEvent
    public void onRenderWaypointIngame(RenderWaypointIngameEvent event) {
        if (event.waypoint.getType() != 1001) {
            return;
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEX);
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        int c = event.waypoint.getColor();
        BufferBuilder vertexBuffer = event.bufferBuilder;
        GlStateManager.color(c % 1000 / 255.0f, c % 1000000 / 1000 / 255.0f, c % 1000000000 / 1000000 / 255.0f);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-5.0D, -9.0D, 0.0D).tex(0.0D, 0 * f1).endVertex();
        vertexBuffer.pos(-5.0D, 0.0D, 0.0D).tex(0.0D, 16 * f1).endVertex();
        vertexBuffer.pos(4.0D, 0.0D, 0.0D).tex(16 * f, 16 * f1).endVertex();
        vertexBuffer.pos(4.0D, -9.0D, 0.0D).tex(16 * f, 0 * f1).endVertex();
        event.tessellator.draw();
    }
}
