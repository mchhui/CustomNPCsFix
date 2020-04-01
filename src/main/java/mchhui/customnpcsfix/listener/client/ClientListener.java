package mchhui.customnpcsfix.listener.client;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.api.event.client.ClientSendDataEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noppes.npcs.constants.EnumPacketServer;

public class ClientListener {
    @SubscribeEvent
    public void onClientSendData(ClientSendDataEvent event) {
        if(!Config.DontSendDubiousScript) {
            return;
        }
        EnumPacketServer type = event.type;
        if (type != EnumPacketServer.ScriptBlockDataSave && type != EnumPacketServer.ScriptDataSave
                && type != EnumPacketServer.ScriptDoorDataSave && type != EnumPacketServer.ScriptForgeSave
                && type != EnumPacketServer.ScriptItemDataSave && type != EnumPacketServer.ScriptPlayerSave) {
            return;
        }
        NBTTagCompound nbt = (NBTTagCompound) event.obs[0];
        if (nbt.getTagList("Scripts", 10).hasNoTags()) {
            event.setCanceled(true);
        }
    }
}
