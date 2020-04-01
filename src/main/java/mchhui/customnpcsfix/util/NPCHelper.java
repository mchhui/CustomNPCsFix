package mchhui.customnpcsfix.util;

import mchhui.customnpcsfix.Config;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;

public class NPCHelper {
    public static boolean isEating(ItemStack item) {
        if(!Config.WIPNoFoodBUG) {
            return true;
        }
        if(item==null) {
            return false;
        }
        return !item.isEmpty();
    }
    
    public static String onSay(String line) {
        if(!Config.NoMsgPercentSymbolBug) {
            return line;
        }
        return line.replaceAll("%", "%%");
    }
}
