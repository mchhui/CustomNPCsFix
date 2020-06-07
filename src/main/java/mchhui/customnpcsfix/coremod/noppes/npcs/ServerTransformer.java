package mchhui.customnpcsfix.coremod.noppes.npcs;

import java.util.List;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class ServerTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("noppes.npcs.Server")) {
            FMLLog.getLogger().warn("[Transforming:noppes.npcs.Server]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            List<MethodNode> methods = classNode.methods;
            for (MethodNode method : methods) {
                if (method.name.equals("lambda$sendDataDelayed$0")) {
                    putListen(method, 2, 0, 1);
                }
                if (method.name.equals("sendDataChecked")) {
                    putListen(method, 0, 1, 2,true);
                }
                if (method.name.equals("lambda$sendAssociatedData$1")) {
                    putListen(method, 5, 0, 1);
                }
                if (method.name.equals("lambda$sendRangedData$2")) {
                    putListen(method, 5, 0, 1);
                }
                if (method.name.equals("lambda$sendRangedData$3")) {
                    putListen(method, 5, 0, 1);
                }
                if (method.name.equals("lambda$sendToAll$4")) {
                    putListen(method, 5, 0, 1);
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:noppes.npcs.Server]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

    private static void putListen(MethodNode method, int player, int enm, int obs) {
        putListen(method, player, enm, obs, false);
    }

    private static void putListen(MethodNode method, int player, int enm, int obs, boolean isBoolean) {
        InsnList list = new InsnList();
        LabelNode label = new LabelNode();
        list.add(new VarInsnNode(Opcodes.ALOAD, player));
        list.add(new VarInsnNode(Opcodes.ALOAD, enm));
        list.add(new VarInsnNode(Opcodes.ALOAD, obs));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/api/EventHook",
                "onCustomNPCsSendPacket",
                "(Lnet/minecraft/entity/player/EntityPlayerMP;Lnoppes/npcs/constants/EnumPacketClient;[Ljava/lang/Object;)Z",
                false));
        list.add(new JumpInsnNode(Opcodes.IFEQ, label));
        if (isBoolean) {
            list.add(new InsnNode(Opcodes.ICONST_0));
            list.add(new InsnNode(Opcodes.IRETURN));
        }else {
            list.add(new InsnNode(Opcodes.RETURN));
        }
        list.add(label);
        for (AbstractInsnNode node : method.instructions.toArray()) {
            if (node.getOpcode() == Opcodes.GETSTATIC) {
                FieldInsnNode fnode = (FieldInsnNode) node;
                if (fnode.owner.equals("noppes/npcs/CustomNpcs")) {
                    method.instructions.insertBefore(node, list);
                }
            }
        }
        list = new InsnList();
        label = new LabelNode();
        list.add(new VarInsnNode(Opcodes.ALOAD, enm));
        list.add(new VarInsnNode(Opcodes.ALOAD, obs));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/api/EventHook",
                "onCustomNPCsPreSendPacket", "(Lnoppes/npcs/constants/EnumPacketClient;[Ljava/lang/Object;)Z", false));
        list.add(new JumpInsnNode(Opcodes.IFEQ, label));
        if (isBoolean) {
            list.add(new InsnNode(Opcodes.ICONST_0));
            list.add(new InsnNode(Opcodes.IRETURN));
        }else {
            list.add(new InsnNode(Opcodes.RETURN));
        }
        list.add(label);
        method.instructions.insert(method.instructions.getFirst(), list);
    }
}
