package mchhui.customnpcsfix.coremod.noppes.npcs.client;

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

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class PacketHandlerClientTransformer implements IClassTransformer{

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("noppes.npcs.client.PacketHandlerClient")) {
            FMLLog.getLogger().warn("[Transforming:noppes.npcs.client.PacketHandlerClient]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            List<MethodNode> methods = classNode.methods;
            for (MethodNode method : methods) {
                if (method.name.equals("client")) {
                    for(AbstractInsnNode node : method.instructions.toArray()) {
                        if(node.getOpcode()==Opcodes.INVOKEINTERFACE&&((MethodInsnNode)node).name.equals("addMessage")) {
                            InsnList list=new InsnList();
                            list.add(new LabelNode());
                            list.add(new VarInsnNode(Opcodes.ALOAD, 6));
                            list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/util/NPCHelper", "onSay","(Ljava/lang/String;)Ljava/lang/String;",false));
                            list.add(new VarInsnNode(Opcodes.ASTORE, 6));
                            method.instructions.insert(node,list);
                            break;
                        }
                    }
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:noppes.npcs.client.PacketHandlerClient]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

}
