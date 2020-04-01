package mchhui.customnpcsfix.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mchhui.org.json.JSONObject;
import mchhui.org.json.JSONTokener;
import net.minecraft.nbt.NBTTagCompound;

public class NBTJsonUtil {
    public static NBTTagCompound Convert(String json) {
        return new JSONObject(new JSONTokener(json)).getNBT();
    }
}
