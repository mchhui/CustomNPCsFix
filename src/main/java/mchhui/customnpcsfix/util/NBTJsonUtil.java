package mchhui.customnpcsfix.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mchhui.customnpcsfix.Config;
import mchhui.org.json.JSONObject;
import mchhui.org.json.JSONTokener;
import net.minecraft.nbt.NBTTagCompound;

public class NBTJsonUtil {
    public static String Convert(NBTTagCompound nbt) {
        if(!Config.FastJsonDeserialize) {
            throw new Error("FastJsonDeserialize is disable");
        }
        return NBTJsonHelper.compile(nbt);
    }
    
    public static NBTTagCompound Convert(String json) {
        if(json.startsWith("gson")) {
            return NBTJsonHelper.decompile(json);
        }
        return new JSONObject(new JSONTokener(json)).getNBT();
    }
}
