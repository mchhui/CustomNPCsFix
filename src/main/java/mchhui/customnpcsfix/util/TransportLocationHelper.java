package mchhui.customnpcsfix.util;

import mchhui.customnpcsfix.Config;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.server.FMLServerHandler;
import noppes.npcs.controllers.data.TransportLocation;

public class TransportLocationHelper {
    public static void onWriteNbt(TransportLocation loc, NBTTagCompound nbt) {
        if(!Config.TPLocUseWorldName) {
            return;
        }
        World world=FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(loc.dimension);
        nbt.setString("WorldName", world.getWorldInfo().getWorldName());
    }

    public static void onReadNbt(TransportLocation loc, NBTTagCompound nbt) {
        if(!Config.TPLocUseWorldName) {
            return;
        }
        if(nbt.hasKey("WorldName")) {
            try {
                loc.getClass().getField("worldName").set(loc, nbt.getString("WorldName"));
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
