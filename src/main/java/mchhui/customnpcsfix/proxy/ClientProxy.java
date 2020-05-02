package mchhui.customnpcsfix.proxy;

import mchhui.customnpcsfix.CustomNPCsFix;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsGuiRendererTranfromer;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsIngameRendererTranfromer;
import mchhui.customnpcsfix.listener.PlayerListener;
import mchhui.customnpcsfix.listener.client.ClientListener;
import mchhui.customnpcsfix.listener.client.NPCRenderListener;
import mchhui.customnpcsfix.listener.xaerominimap.RenderWaypointsListener;
import mchhui.customnpcsfix.network.HueihueaHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noppes.npcs.CustomNpcs;

public class ClientProxy extends CommonProxy {
    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);
        MinecraftForge.EVENT_BUS.register(new NPCRenderListener());
        MinecraftForge.EVENT_BUS.register(new ClientListener());
        if(WaypointsGuiRendererTranfromer.isSuccessful && WaypointsIngameRendererTranfromer.isSuccessful) {
            MinecraftForge.EVENT_BUS.register(new RenderWaypointsListener());
        }
        MinecraftForge.EVENT_BUS.register(new ClientListener());
        CustomNPCsFix.Channel.register(new HueihueaHandlerClient.WaypointHandler());
        CustomNpcs.Channel.register(new HueihueaHandlerClient.FixHandler());
    }
    
    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);
    }
}
