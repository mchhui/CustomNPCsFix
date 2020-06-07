package mchhui.customnpcsfix.coremod.noppes.npcs.controllers.data;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class TransportLocationTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("noppes.npcs.controllers.data.TransportLocation")) {
            FMLLog.getLogger().warn("[Transforming:noppes.npcs.controllers.data.TransportLocation]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "worldName", "Ljava/lang/String;", null, null));
            List<MethodNode> methods = classNode.methods;
            for (MethodNode method : methods) {
                if (method.name.equals("readNBT")) {
                    byte count=0;
                    for (AbstractInsnNode node : method.instructions.toArray()) {
                        if (node.getOpcode() == Opcodes.RETURN) {
                            if(count!=1) {
                                count++;
                                continue;
                            }
                            InsnList list = new InsnList();
                            list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            list.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                    "mchhui/customnpcsfix/util/TransportLocationHelper", "onReadNbt",
                                    "(Lnoppes/npcs/controllers/data/TransportLocation;Lnet/minecraft/nbt/NBTTagCompound;)V",
                                    false));
                            list.add(new LabelNode());
                            method.instructions.insertBefore(node, list);
                            break;
                        }
                    }
                }
                if (method.name.equals("writeNBT")) {
                    for (AbstractInsnNode node : method.instructions.toArray()) {
                        if (node.getOpcode() == Opcodes.ARETURN) {
                            InsnList list = new InsnList();
                            list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            list.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                    "mchhui/customnpcsfix/util/TransportLocationHelper", "onWriteNbt",
                                    "(Lnoppes/npcs/controllers/data/TransportLocation;Lnet/minecraft/nbt/NBTTagCompound;)V",
                                    false));
                            list.add(new LabelNode());
                            method.instructions.insertBefore(node.getPrevious(), list);
                            break;
                        }
                    }
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:noppes.npcs.controllers.data.TransportLocation]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

}
