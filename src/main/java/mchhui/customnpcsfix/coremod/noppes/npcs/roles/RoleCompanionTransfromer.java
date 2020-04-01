package mchhui.customnpcsfix.coremod.noppes.npcs.roles;

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

import net.minecraft.init.Items;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class RoleCompanionTransfromer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("noppes.npcs.roles.RoleCompanion")) {
            FMLLog.getLogger().warn("[Transforming:noppes.npcs.roles.RoleCompanion]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            List<MethodNode> methods = classNode.methods;
            for (MethodNode method : methods) {
                if (method.name.equals("doEating")) {
                    for (AbstractInsnNode node : method.instructions.toArray()) {
                        if (node.getOpcode() == Opcodes.INVOKEVIRTUAL
                                && ((MethodInsnNode) node).owner.equals("noppes/npcs/NpcMiscInventory")) {
                            InsnList list = new InsnList();
                            list.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) node.getNext()).label));
                            list.add(new LabelNode());
                            list.add(new FrameNode(Opcodes.F_FULL, 2,
                                    new String[] { "noppes/npcs/roles/RoleCompanion", "net/minecraft/item/ItemStack" },
                                    0, new Object[0]));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/util/NPCHelper",
                                    "isEating", "(Lnet/minecraft/item/ItemStack;)Z", false));
                            method.instructions.insert(node, list);
                            break;
                        }
                    }
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:noppes.npcs.roles.RoleCompanion]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }
}
