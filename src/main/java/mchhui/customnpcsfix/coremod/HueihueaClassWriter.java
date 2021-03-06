package mchhui.customnpcsfix.coremod;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import net.minecraftforge.fml.common.FMLLog;

public class HueihueaClassWriter extends ClassWriter {
    public static final Map<String, String> mapping = new HashMap<String, String>();

    static {
        mapping.put("net/minecraft/client/renderer/entity/Render,java/util/List", "java/lang/Object");
        mapping.put("java/lang/Exception,net/minecraft/client/resources/data/PackMetadataSection", "java/lang/Object");
        mapping.put("net/minecraft/client/resources/FolderResourcePack,net/minecraft/client/resources/FileResourcePack", "net/minecraft/client/resources/AbstractResourcePack");
        mapping.put("net/minecraft/client/resources/AbstractResourcePack,net/minecraft/client/resources/FileResourcePack", "net/minecraft/client/resources/AbstractResourcePack");
        mapping.put("net/minecraft/client/resources/data/PackMetadataSection,java/lang/Object", "java/lang/Object");
    }

    public HueihueaClassWriter(ClassReader classReader, int flags) {
        super(classReader, flags);
    }

    public HueihueaClassWriter(int computeFrames) {
        super(computeFrames);
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        //System.out.println(type1 + "," + type2+"="+super.getCommonSuperClass(type1, type2));
        if (mapping.containsKey(type1 + "," + type2)) {
            return mapping.get(type1 + "," + type2);
        }
        return super.getCommonSuperClass(type1, type2);
    }
}
