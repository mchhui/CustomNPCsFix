package mchhui.customnpcsfix.proxy;

import mchhui.customnpcsfix.listener.client.ClientListener;
import mchhui.customnpcsfix.listener.client.NPCRenderListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
    }
    
    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);
        MinecraftForge.EVENT_BUS.register(new NPCRenderListener());
        MinecraftForge.EVENT_BUS.register(new ClientListener());
    }
}
