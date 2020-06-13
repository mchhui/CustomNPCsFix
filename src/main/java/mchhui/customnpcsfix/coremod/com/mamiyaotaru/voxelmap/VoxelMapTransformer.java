package mchhui.customnpcsfix.coremod.com.mamiyaotaru.voxelmap;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class VoxelMapTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("com.mamiyaotaru.voxelmap.VoxelMap")) {
            FMLLog.getLogger().warn("[Transforming:com.mamiyaotaru.voxelmap.VoxelMap]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            for (MethodNode method : classNode.methods) {
                if (method.name.equals("onResourceManagerReload") || method.name.equals("func_110549_a")) {
                    AbstractInsnNode node = method.instructions.getFirst();
                    InsnList list = new InsnList();
                    list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/api/EventHook",
                            "onPreVMapResourceReload", "(Lnet/minecraft/client/resources/IResourceManager;)V", false));
                    list.add(new LabelNode());
                    method.instructions.insert(node, list);
                    node = method.instructions.getLast();
                    while (node.getOpcode() != Opcodes.RETURN) {
                        node = node.getPrevious();
                    }
                    list.clear();
                    list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/api/EventHook",
                            "onPostVMapResourceReload", "(Lnet/minecraft/client/resources/IResourceManager;)V", false));
                    list.add(new LabelNode());
                    method.instructions.insertBefore(node, list);
                    break;
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:com.mamiyaotaru.voxelmap.VoxelMap]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

}
