package mchhui.customnpcsfix.coremod.noppes.npcs.controllers.data;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class QuestTransfromer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("noppes.npcs.controllers.data.Quest")) {
            FMLLog.getLogger().warn("[Transforming:noppes.npcs.controllers.data.Quest]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "waypoint",
                    "Lmchhui/customnpcsfix/controllers/data/Waypoint;", null, null));
            List<MethodNode> methods = classNode.methods;
            for (MethodNode method : methods) {
                if(method.name.equals("<init>")) {
                    for(AbstractInsnNode node : method.instructions.toArray()) {
                        if(node.getOpcode() == Opcodes.RETURN) {
                            InsnList list = new InsnList();
                            list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            list.add(new TypeInsnNode(Opcodes.NEW, "mchhui/customnpcsfix/controllers/data/Waypoint"));
                            list.add(new InsnNode(Opcodes.DUP));
                            list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "mchhui/customnpcsfix/controllers/data/Waypoint",
                                    "<init>",
                                    "()V",
                                    false));
                            list.add(new FieldInsnNode(Opcodes.PUTFIELD, "noppes/npcs/controllers/data/Quest", "waypoint", "Lmchhui/customnpcsfix/controllers/data/Waypoint;"));
                            list.add(new LabelNode());
                            method.instructions.insertBefore(node, list);
                        }
                    }
                }
                if (method.name.equals("readNBTPartial")) {
                    for (AbstractInsnNode node : method.instructions.toArray()) {
                        if (node.getOpcode() == Opcodes.RETURN) {
                            InsnList list = new InsnList();
                            list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/util/QuestHelper",
                                    "onReadNbt",
                                    "(Lnoppes/npcs/controllers/data/Quest;Lnet/minecraft/nbt/NBTTagCompound;)V",
                                    false));
                            list.add(new LabelNode());
                            method.instructions.insertBefore(node, list);
                            break;
                        }
                    }
                }
                if (method.name.equals("writeToNBTPartial")) {
                    for (AbstractInsnNode node : method.instructions.toArray()) {
                        if (node.getOpcode() == Opcodes.ARETURN) {
                            InsnList list = new InsnList();
                            list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/util/QuestHelper",
                                    "onWriteNbt",
                                    "(Lnoppes/npcs/controllers/data/Quest;Lnet/minecraft/nbt/NBTTagCompound;)V",
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
            FMLLog.getLogger().warn("[Transformed:noppes.npcs.controllers.data.Quest]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

}
