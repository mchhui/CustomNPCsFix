package mchhui.customnpcsfix.coremod.noppes.npcs.util;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import mchhui.customnpcsfix.coremod.HueihueaClassWriter;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class NBTJsonUtilTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("noppes.npcs.util.NBTJsonUtil")) {
            FMLLog.getLogger().warn("[Transforming:noppes.npcs.util.NBTJsonUtil]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            List<MethodNode> methods = classNode.methods;
            for (MethodNode method : methods) {
                if (method.name.equals("Convert")
                        && method.desc.equals("(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;")) {
                    LabelNode labelJumpOut = new LabelNode();
                    InsnList list = new InsnList();
                    list.add(new FieldInsnNode(Opcodes.GETSTATIC, "mchhui/customnpcsfix/Config", "FastJsonDeserialize",
                            "Z"));
                    list.add(new JumpInsnNode(Opcodes.IFEQ, labelJumpOut));
                    list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/util/NBTJsonUtil",
                            "Convert", "(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;", false));
                    list.add(new InsnNode(Opcodes.ARETURN));
                    list.add(labelJumpOut);
                    method.instructions.insert(method.instructions.getFirst(), list);
                }
                if (method.name.equals("ConvertList")) {
                    LabelNode labelJumpOut = new LabelNode();
                    InsnList list = new InsnList();
                    list.add(new FieldInsnNode(Opcodes.GETSTATIC, "mchhui/customnpcsfix/Config", "FastJsonDeserialize",
                            "Z"));
                    list.add(new JumpInsnNode(Opcodes.IFEQ, labelJumpOut));
                    list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/util/NBTJsonUtil",
                            "ConvertList", "(Ljava/util/List;)Ljava/lang/String;", false));
                    list.add(new InsnNode(Opcodes.ARETURN));
                    list.add(labelJumpOut);
                    method.instructions.insert(method.instructions.getFirst(), list);
                }
            }
            ClassWriter classWriter = new HueihueaClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:noppes.npcs.util.NBTJsonUtil]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

}
