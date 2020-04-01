package mchhui.customnpcsfix.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import dk.mrspring.nbtjson.NBTJsonCompile;
import dk.mrspring.nbtjson.NBTJsonDecompile;
import net.minecraft.nbt.NBTTagCompound;

public class NBTJsonHelper {
    public static String compile(NBTTagCompound nbt) {
        return "gson"+NBTJsonCompile.createJsonStringFromCompound(nbt);
    }

    public static NBTTagCompound decompile(String json) {
        json=json.substring(4);
        return NBTJsonDecompile.createFromJsonObject(new Gson().fromJson(json, LinkedTreeMap.class));
    }
}
