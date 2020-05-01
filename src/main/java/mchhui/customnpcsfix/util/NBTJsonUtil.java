package mchhui.customnpcsfix.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mchhui.org.json.JSONObject;
import mchhui.org.json.JSONTokener;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLLog;

public class NBTJsonUtil {
    public static NBTTagCompound Convert(String json) {
        return new JSONObject(new JSONTokener(json)).getNBT();
    }

    public static String ConvertList(List list) {
        StringBuilder json = new StringBuilder();
        int tab = 0;
        Class classJsonLine;
        try {
            classJsonLine = Class.forName("noppes.npcs.util.NBTJsonUtil$JsonLine");
            Method methodReduceTab = classJsonLine.getMethod("reduceTab");
            methodReduceTab.setAccessible(true);
            Method methodIncreaseTab = classJsonLine.getMethod("increaseTab");
            methodIncreaseTab.setAccessible(true);
            for (Object tag : list) {
                if ((boolean) methodReduceTab.invoke(tag)) {
                    tab--;
                }
                for (int i = 0; i < tab; i++) {
                    json = json.append("    ");
                }
                json = json.append(tag).append("\n");
                if ((boolean) methodIncreaseTab.invoke(tag)) {
                    tab++;
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return json.toString();
    }
}
