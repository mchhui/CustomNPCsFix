package mchhui.customnpcsfix.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mchhui.customnpcsfix.listener.client.NPCRenderListener;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{
    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
    }
    
    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);
        MinecraftForge.EVENT_BUS.register(new NPCRenderListener());
    }
}
