package mchhui.customnpcsfix;

import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumPacketServer;

public class EnumHandler {
    public static EnumPacketServer NOTHING;
    public static EnumPacketClient NOTHING_CLIENT;

    public EnumHandler(FMLPostInitializationEvent event) {
        load();
    }

    public void load() {
        this.NOTHING = EnumHelper.addEnum(EnumPacketServer.class, "NOTHING",new Class[0]);
        this.NOTHING_CLIENT = EnumHelper.addEnum(EnumPacketClient.class, "NOTHING",new Class[0]);
    }
}
