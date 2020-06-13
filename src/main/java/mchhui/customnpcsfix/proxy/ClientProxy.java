package mchhui.customnpcsfix.proxy;

import java.util.List;

import com.mamiyaotaru.voxelmap.VoxelMap;
import com.mamiyaotaru.voxelmap.textures.Sprite;
import com.mamiyaotaru.voxelmap.textures.TextureAtlas;
import com.mamiyaotaru.voxelmap.util.ReflectionUtils;

import mchhui.customnpcsfix.CustomNPCsFix;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsGuiRendererTranformer;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsIngameRendererTranformer;
import mchhui.customnpcsfix.listener.PlayerListener;
import mchhui.customnpcsfix.listener.client.ClientListener;
import mchhui.customnpcsfix.listener.client.NPCRenderListener;
import mchhui.customnpcsfix.listener.client.RenderGameOverlayListener;
import mchhui.customnpcsfix.listener.client.WorldRenderListener;
import mchhui.customnpcsfix.listener.journeymap.JMapWayPointLoadedListener;
import mchhui.customnpcsfix.listener.xaerominimap.RenderWaypointsListener;
import mchhui.customnpcsfix.network.HueihueaHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.asm.ModAnnotation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.libraries.ModList;
import noppes.npcs.CustomNpcs;

public class ClientProxy extends CommonProxy {
    private static ResourceLocation iconWaypointBig = new ResourceLocation("customnpcsfix",
            "textures/waypoint_quest_big.png");
    private static ResourceLocation iconWaypoint = new ResourceLocation("customnpcsfix", "textures/waypoint_quest.png");

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);
        MinecraftForge.EVENT_BUS.register(new NPCRenderListener());
        MinecraftForge.EVENT_BUS.register(new ClientListener());
        if (WaypointsGuiRendererTranformer.isSuccessful && WaypointsIngameRendererTranformer.isSuccessful) {
            MinecraftForge.EVENT_BUS.register(new RenderWaypointsListener());
        }
        MinecraftForge.EVENT_BUS.register(new WorldRenderListener());
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayListener());
        MinecraftForge.EVENT_BUS.register(new JMapWayPointLoadedListener());
        CustomNPCsFix.Channel.register(new HueihueaHandlerClient.WaypointHandler());
        CustomNpcs.Channel.register(new HueihueaHandlerClient.FixHandler());
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);      
    }
}
