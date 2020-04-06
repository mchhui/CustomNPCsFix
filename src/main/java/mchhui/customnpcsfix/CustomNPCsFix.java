package mchhui.customnpcsfix;

import mchhui.customnpcsfix.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;

@Mod(modid = CustomNPCsFix.MODID, name = CustomNPCsFix.NAME, version = CustomNPCsFix.VERSION, useMetadata = true, acceptableRemoteVersions = "*")
public class CustomNPCsFix {
    public static final String MODID = "customnpcsfix";
    public static final String NAME = "CustomNPCs Fix";
    public static final String VERSION = "1.2.1";
    @SidedProxy(modId = CustomNPCsFix.MODID, clientSide = "mchhui.customnpcsfix.proxy.ClientProxy", serverSide = "mchhui.customnpcsfix.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        proxy.onPreInit(event);
    }
    
    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.onInit(event);
    }
    
    @EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        proxy.onServerStart(event);
    }
    
    @EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        proxy.onPostInit(event);
    }
}
