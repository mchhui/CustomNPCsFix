package mchhui.customnpcsfix.proxy;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.CustomNPCsFix;
import mchhui.customnpcsfix.EnumHandler;
import mchhui.customnpcsfix.command.CommandHueihuea;
import mchhui.customnpcsfix.listener.PlayerListener;
import mchhui.customnpcsfix.listener.QuestListener;
import mchhui.customnpcsfix.listener.RoleListener;
import mchhui.customnpcsfix.network.HueihueaHandlerServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import noppes.npcs.CustomNpcs;
import noppes.npcs.api.NpcAPI;

public class CommonProxy {
    public void onPreInit(FMLPreInitializationEvent event) {
        new Config(event);
        CustomNPCsFix.Channel=NetworkRegistry.INSTANCE.newEventDrivenChannel("CustomNPCsFix");
    }

    public void onInit(FMLInitializationEvent event) {
        NpcAPI.Instance().events().register(new RoleListener());
        CustomNpcs.Channel.register(new HueihueaHandlerServer.NPCHandler());
        CustomNpcs.Channel.register(new HueihueaHandlerServer.FixHandler());
        CustomNPCsFix.Channel.register(new HueihueaHandlerServer.WaypointHandler());
        MinecraftForge.EVENT_BUS.register(new QuestListener());
        NpcAPI.Instance().events().register(new QuestListener());
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
    }

    public void onPostInit(FMLPostInitializationEvent event) {
        new EnumHandler(event);
    }
    
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandHueihuea());
    }
    
}
