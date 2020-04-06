package mchhui.customnpcsfix;

import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import noppes.npcs.constants.EnumPacketServer;

public class EnumHandler {
    public static EnumPacketServer NOTHING;

    public EnumHandler(FMLPostInitializationEvent event) {
        load();
    }

    public void load() {
        this.NOTHING = EnumHelper.addEnum(EnumPacketServer.class, "NOTHING",new Class[0]);
    }
}
