package mchhui.customnpcsfix.listener.client;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.api.event.client.ClientSendDataEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import noppes.npcs.client.gui.script.GuiScriptInterface;
import noppes.npcs.constants.EnumPacketServer;

public class ClientListener {
    @SubscribeEvent
    public void onClientSendData(ClientSendDataEvent event) {
        if (!Config.DontSendDubiousScript) {
            return;
        }
        EnumPacketServer type = event.type;
        if (type != EnumPacketServer.ScriptBlockDataSave && type != EnumPacketServer.ScriptDataSave
                && type != EnumPacketServer.ScriptDoorDataSave && type != EnumPacketServer.ScriptForgeSave
                && type != EnumPacketServer.ScriptItemDataSave && type != EnumPacketServer.ScriptPlayerSave) {
            return;
        }
        NBTTagCompound nbt = null;
        if (type == EnumPacketServer.ScriptBlockDataSave) {
            nbt = (NBTTagCompound) event.obs[3];
        } else if (type == EnumPacketServer.ScriptDoorDataSave) {
            nbt = (NBTTagCompound) event.obs[3];
        } else {
            nbt = (NBTTagCompound) event.obs[0];
        }
        if (nbt.getTagList("Scripts", 10).hasNoTags()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onTick(ClientTickEvent event) {
        if (event.phase != Phase.END) {
            return;
        }
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        if (!(gui instanceof GuiScriptInterface)) {
            return;
        }
        GuiScriptInterface scriptgui = (GuiScriptInterface) gui;
        if (scriptgui.handler.getScripts().size() >= 11 && Config.LimitedScriptGuiAddButton) {
            scriptgui.getTopButton(12).enabled = false;
            scriptgui.getTopButton(12).visible = false;
        }
    }
}
