package mchhui.customnpcsfix.listener;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.util.BukkitHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noppes.npcs.api.event.RoleEvent.TransporterUseEvent;
import noppes.npcs.controllers.data.TransportLocation;

public class RoleListener {
    @SubscribeEvent
    public void onTransporterUse(TransporterUseEvent event) {
        String worldName = null;
        try {
            worldName = (String) TransportLocation.class.getField("worldName").get(event.location);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        if (worldName != null) {
            FMLLog.getLogger().info(worldName);
            World[] worlds = FMLCommonHandler.instance().getMinecraftServerInstance().worlds;
            for (World world : worlds) {
                if (world.getWorldInfo().getWorldName().equals(worldName)) {
                    ((TransportLocation) event.location).dimension = world.provider.getDimension();
                    break;
                }
            }
        }
        if (Config.TPUseBukkitAPI) {
            if (worldName == null) {
                FMLLog.getLogger().warn("启动了TPUseBukkitAPI 但是遇到了没有WorldName的TransportLocation:"+event.location.getName()+"#"+event.location.getId());
            } else {
                event.setCanceled(
                        BukkitHelper.teleport(event.player.getName(), worldName, (TransportLocation) event.location));
            }
        }
    }
}
