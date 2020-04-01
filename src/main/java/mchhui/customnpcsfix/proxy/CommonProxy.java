package mchhui.customnpcsfix.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.command.CommandHueihuea;

public class CommonProxy {
    public void onPreInit(FMLPreInitializationEvent event) {
        new Config(event);
    }
    
    public void onInit(FMLInitializationEvent event) {
        
    }
    
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandHueihuea());
    }
}
