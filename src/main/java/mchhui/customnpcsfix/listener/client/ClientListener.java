package mchhui.customnpcsfix.listener.client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import mchhui.customnpcsfix.Client;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.api.event.client.ClientSendDataEvent;
import mchhui.customnpcsfix.client.gui.HueihueaGuiQuestEdit;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsGuiRendererTranfromer;
import mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsIngameRendererTranfromer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.asm.ModAnnotation;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import noppes.npcs.CustomItems;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.client.gui.global.GuiQuestEdit;
import noppes.npcs.client.gui.script.GuiScriptInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.constants.EnumPacketServer;
import xaero.common.api.spigot.ServerWaypoint;
import xaero.common.gui.GuiWaypoints;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointWorldContainer;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.minimap.XaeroMinimap;

public class ClientListener {
    private static String lastAutoContainerID = null;
    private static boolean initMessageSent=false;

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
        if (WaypointsGuiRendererTranfromer.isSuccessful || WaypointsIngameRendererTranfromer.isSuccessful) {
            WaypointsManager manager = XaeroMinimap.instance.getWaypointsManager();
            if (lastAutoContainerID != manager.getAutoContainerID()) {
                if (manager.getAutoContainerID() != null) {
                    Client.getAllQuestWaypoint();
                }
            }
            lastAutoContainerID = manager.getAutoContainerID();
            if (Minecraft.getMinecraft().world == null) {
                lastAutoContainerID = null;
            }
        }
        
        if(Minecraft.getMinecraft().world!=null) {
            if(!initMessageSent) {
                if (Loader.isModLoaded("Xaero's Minimap")&&(!WaypointsGuiRendererTranfromer.isSuccessful || !WaypointsIngameRendererTranfromer.isSuccessful)) {
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(I18n.format("mod.xmap.unsupportedversion")));
                }
                initMessageSent=true;
            }
        }
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        if ((WaypointsGuiRendererTranfromer.isSuccessful || WaypointsIngameRendererTranfromer.isSuccessful)
                && gui instanceof GuiWaypoints) {
            List<Waypoint> list;
            ConcurrentSkipListSet<Integer> selectedListSet;
            Class GuiWaypointsClass = GuiWaypoints.class;
            try {
                Field selectedListSetField = GuiWaypointsClass.getDeclaredField("selectedListSet");
                selectedListSetField.setAccessible(true);
                selectedListSet = (ConcurrentSkipListSet<Integer>) selectedListSetField.get(gui);
                Method getSelectedWaypointsListMethod = GuiWaypointsClass.getDeclaredMethod("getSelectedWaypointsList",
                        boolean.class);
                getSelectedWaypointsListMethod.setAccessible(true);
                list = (List<Waypoint>) getSelectedWaypointsListMethod.invoke(gui, true);
                int i = 0;
                List<Integer> removeList = new ArrayList<Integer>();
                for (Waypoint point : list) {
                    if (point.getType() == 1001) {
                        removeList.add(i);
                    }
                    i++;
                }
                i = 0;
                for (Integer in : selectedListSet) {
                    if (removeList.contains(i)) {
                        selectedListSet.remove(in);
                    }
                    i++;
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchFieldException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
        if (gui instanceof GuiScriptInterface) {
            GuiScriptInterface scriptgui = (GuiScriptInterface) gui;
            if (scriptgui.handler.getScripts().size() >= 11 && Config.LimitedScriptGuiAddButton) {
                scriptgui.getTopButton(12).enabled = false;
                scriptgui.getTopButton(12).visible = false;
            }
        }
        if (gui instanceof GuiNPCManageQuest) {
            GuiNPCManageQuest manageQuestGui = (GuiNPCManageQuest) gui;
            if (manageQuestGui.getSubGui() instanceof GuiQuestEdit) {
                GuiQuestEdit questgui = (GuiQuestEdit) manageQuestGui.getSubGui();
                if (!(questgui instanceof HueihueaGuiQuestEdit)) {
                    questgui = HueihueaGuiQuestEdit.create(questgui);
                    manageQuestGui.setSubGui(questgui);
                }
            }
        }
    }
}
