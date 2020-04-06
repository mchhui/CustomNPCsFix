package mchhui.customnpcsfix;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FixEventChannel;
import noppes.npcs.CustomNpcs;

public class ChannelHandler {
    public ChannelHandler(FMLPostInitializationEvent event) {
        load();
    }

    public void load() {
        FMLLog.log.info("test1");
        CustomNpcs.Channel=new FixEventChannel(CustomNpcs.Channel);
    }
}
