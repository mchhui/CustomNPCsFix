package mchhui.customnpcsfix.proxy;

import mchhui.customnpcsfix.ChannelHandler;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.EnumHandler;
import mchhui.customnpcsfix.command.CommandHueihuea;
import mchhui.customnpcsfix.listener.PlayerListener;
import mchhui.customnpcsfix.listener.RoleListener;
import mchhui.customnpcsfix.network.HueihueaHandlerServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import noppes.npcs.CustomNpcs;
import noppes.npcs.api.NpcAPI;

public class CommonProxy {
    public void onPreInit(FMLPreInitializationEvent event) {
        new Config(event);
    }

    public void onInit(FMLInitializationEvent event) {
        NpcAPI.Instance().events().register(new RoleListener());
        CustomNpcs.Channel.register(new HueihueaHandlerServer.NPCHandler());
        CustomNpcs.Channel.register(new HueihueaHandlerServer.FixHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
    }

    public void onPostInit(FMLPostInitializationEvent event) {
        new EnumHandler(event);
        new ChannelHandler(event);
    }
    
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandHueihuea());
    }
    
}
