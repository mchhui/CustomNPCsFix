package mchhui.customnpcsfix.coremod.net.minecraft.client.resources;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import mchhui.customnpcsfix.coremod.HueihueaClassWriter;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class ResourcePackRepositoryTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("net.minecraft.client.resources.ResourcePackRepository")) {
            FMLLog.getLogger().warn("[Transforming:net.minecraft.client.resources.ResourcePackRepository]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            for (MethodNode method : classNode.methods) {
                if (method.name.equals("getResourcePackFiles") || method.name.equals("func_110614_g")) {
                    for (AbstractInsnNode node : method.instructions.toArray()) {
                        if (node.getOpcode() == Opcodes.ARETURN) {
                            method.instructions.insertBefore(node,
                                    new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/api/EventHook",
                                            "onResourcePackRepositoryGetFiles",
                                            "(Ljava/util/List;)Ljava/util/List;", false));
                            break;
                        }
                    }
                    break;
                }
            }
            ClassWriter classWriter = new HueihueaClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:net.minecraft.client.resources.ResourcePackRepository]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

}
